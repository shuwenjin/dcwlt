package com.dcits.dcwlt.gateway.filter;

import com.dcits.dcwlt.gateway.utils.JsonXmlUtil;
import org.dom4j.DocumentException;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.filter.factory.rewrite.MessageBodyDecoder;
import org.springframework.cloud.gateway.filter.factory.rewrite.MessageBodyEncoder;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.client.reactive.ClientHttpResponse;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Function;

import static org.springframework.cloud.gateway.support.GatewayToStringStyler.filterToStringCreator;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR;

@Component
public class JsonToXmlFilter extends
        AbstractGatewayFilterFactory<AbstractGatewayFilterFactory.NameConfig> {

    private final List<HttpMessageReader<?>> messageReaders;

    public JsonToXmlFilter() {
        super(NameConfig.class);
        this.messageReaders = HandlerStrategies.withDefaults().messageReaders();
    }

    public JsonToXmlFilter(
            List<HttpMessageReader<?>> messageReaders) {
        super(NameConfig.class);
        this.messageReaders = messageReaders;
    }

    @Deprecated
    public JsonToXmlFilter(ServerCodecConfigurer codecConfigurer) {
        this(codecConfigurer.getReaders());
    }

    @Override
    public GatewayFilter apply(AbstractGatewayFilterFactory.NameConfig config) {
        ModifyResponseGatewayFilter gatewayFilter = new ModifyResponseGatewayFilter(
                config);
        gatewayFilter.setFactory(this);
        return gatewayFilter;
    }
    public class ModifyResponseGatewayFilter implements GatewayFilter, Ordered {

        private final NameConfig config;

        private GatewayFilterFactory<NameConfig> gatewayFilterFactory;

        public ModifyResponseGatewayFilter(NameConfig config) {
            this(config, null);
        }

        @Deprecated
        public ModifyResponseGatewayFilter(NameConfig config,
                                           @Nullable ServerCodecConfigurer codecConfigurer) {
            this.config = config;
        }

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            return chain.filter(exchange.mutate()
                    .response(new ModifiedServerHttpResponse(exchange, config)).build());
        }

        @SuppressWarnings("unchecked")
        @Deprecated
        ServerHttpResponse decorate(ServerWebExchange exchange) {
            return new ModifiedServerHttpResponse(exchange, config);
        }

        @Override
        public int getOrder() {
            return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 1;
        }



        public void setFactory(GatewayFilterFactory<NameConfig> gatewayFilterFactory) {
            this.gatewayFilterFactory = gatewayFilterFactory;
        }

    }

    protected class ModifiedServerHttpResponse extends ServerHttpResponseDecorator {

        private final ServerWebExchange exchange;

        private final NameConfig config;

        public ModifiedServerHttpResponse(ServerWebExchange exchange, NameConfig config) {
            super(exchange.getResponse());
            this.exchange = exchange;
            this.config = config;
        }

        @SuppressWarnings("unchecked")
        @Override
        public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
            String originalResponseContentType = exchange
                    .getAttribute(ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR);
            HttpHeaders httpHeaders = new HttpHeaders();
            // explicitly add it in this way instead of
            // 'httpHeaders.setContentType(originalResponseContentType)'
            // this will prevent exception in case of using non-standard media
            // types like "Content-Type: image"
            httpHeaders.add(HttpHeaders.CONTENT_TYPE, originalResponseContentType);

            ClientResponse clientResponse = prepareClientResponse(body, httpHeaders);

            // TODO: flux or mono
            Mono modifiedBody = extractBody(exchange, clientResponse, String.class)
                    .flatMap(originalBody -> {
                        String xml = null;
                        xml = JsonXmlUtil.jsonToSoap(originalBody);
                        return Mono.just(xml);
                    });

            BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody,
                    String.class);
            CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange,
                    exchange.getResponse().getHeaders());
            return bodyInserter.insert(outputMessage, new BodyInserterContext())
                    .then(Mono.defer(() -> {
                        Mono<DataBuffer> messageBody = writeBody(getDelegate(),
                                outputMessage, String.class);
                        HttpHeaders headers = getDelegate().getHeaders();
                        if (!headers.containsKey(HttpHeaders.TRANSFER_ENCODING)
                                || headers.containsKey(HttpHeaders.CONTENT_LENGTH)) {
                            messageBody = messageBody.doOnNext(data -> headers
                                    .setContentLength(data.readableByteCount())
                            );
                        }
                        if (headers.containsKey(HttpHeaders.CONTENT_TYPE)) {
                            messageBody = messageBody.doOnNext(data -> headers
                                    .setContentType(new MediaType("application", "xml", StandardCharsets.UTF_8))
                            );
                        }
                        // TODO: fail if isStreamingMediaType?
                        return getDelegate().writeWith(messageBody);
                    }));
        }

        @Override
        public Mono<Void> writeAndFlushWith(
                Publisher<? extends Publisher<? extends DataBuffer>> body) {
            return writeWith(Flux.from(body).flatMapSequential(p -> p));
        }

        private ClientResponse prepareClientResponse(Publisher<? extends DataBuffer> body,
                                                     HttpHeaders httpHeaders) {
            ClientResponse.Builder builder;
            builder = ClientResponse.create(exchange.getResponse().getStatusCode(),
                    messageReaders);
            return builder.headers(headers -> headers.putAll(httpHeaders))
                    .body(Flux.from(body)).build();
        }

        private <T> Mono<T> extractBody(ServerWebExchange exchange,
                                        ClientResponse clientResponse, Class<T> inClass) {
            // if inClass is byte[] then just return body, otherwise check if
            // decoding required
            if (byte[].class.isAssignableFrom(inClass)) {
                return clientResponse.bodyToMono(inClass);
            }

            List<String> encodingHeaders = exchange.getResponse().getHeaders()
                    .getOrEmpty(HttpHeaders.CONTENT_ENCODING);
            for (String encoding : encodingHeaders) {
                MessageBodyDecoder decoder = null;
                if (decoder != null) {
                    return clientResponse.bodyToMono(byte[].class)
                            .publishOn(Schedulers.parallel()).map(decoder::decode)
                            .map(bytes -> exchange.getResponse().bufferFactory()
                                    .wrap(bytes))
                            .map(buffer -> prepareClientResponse(Mono.just(buffer),
                                    exchange.getResponse().getHeaders()))
                            .flatMap(response -> response.bodyToMono(inClass));
                }
            }

            return clientResponse.bodyToMono(inClass);
        }

        private Mono<DataBuffer> writeBody(ServerHttpResponse httpResponse,
                                           CachedBodyOutputMessage message, Class<?> outClass) {
            Mono<DataBuffer> response = DataBufferUtils.join(message.getBody());
            if (byte[].class.isAssignableFrom(outClass)) {
                return response;
            }

            List<String> encodingHeaders = httpResponse.getHeaders()
                    .getOrEmpty(HttpHeaders.CONTENT_ENCODING);
            for (String encoding : encodingHeaders) {
                MessageBodyEncoder encoder = null;
                if (encoder != null) {
                    DataBufferFactory dataBufferFactory = httpResponse.bufferFactory();
                    response = response.publishOn(Schedulers.parallel()).map(buffer -> {
                        byte[] encodedResponse = encoder.encode(buffer);
                        DataBufferUtils.release(buffer);
                        return encodedResponse;
                    }).map(dataBufferFactory::wrap);
                    break;
                }
            }

            return response;
        }

    }

    @Deprecated
    @SuppressWarnings("unchecked")
    public class ResponseAdapter implements ClientHttpResponse {

        private final Flux<DataBuffer> flux;

        private final HttpHeaders headers;

        public ResponseAdapter(Publisher<? extends DataBuffer> body,
                               HttpHeaders headers) {
            this.headers = headers;
            if (body instanceof Flux) {
                flux = (Flux) body;
            }
            else {
                flux = ((Mono) body).flux();
            }
        }

        @Override
        public Flux<DataBuffer> getBody() {
            return flux;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }

        @Override
        public HttpStatus getStatusCode() {
            return null;
        }

        @Override
        public int getRawStatusCode() {
            return 0;
        }

        @Override
        public MultiValueMap<String, ResponseCookie> getCookies() {
            return null;
        }

    }

}

