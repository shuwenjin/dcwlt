package com.dcits.dcwlt.dcepgw.filter;

import com.dcits.dcwlt.dcepgw.utils.JsonXmlUtil;
import org.dom4j.DocumentException;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

@Component
public class XmlToJsonFilter extends
        AbstractGatewayFilterFactory<AbstractGatewayFilterFactory.NameConfig> {

    private final List<HttpMessageReader<?>> messageReaders;

    public XmlToJsonFilter() {
        super(NameConfig.class);
        this.messageReaders = HandlerStrategies.withDefaults().messageReaders();
    }

    public XmlToJsonFilter(
            List<HttpMessageReader<?>> messageReaders) {
        super(NameConfig.class);
        this.messageReaders = messageReaders;
    }

    @Deprecated
    public XmlToJsonFilter(ServerCodecConfigurer codecConfigurer) {
        this(codecConfigurer.getReaders());
    }

    @Override
    @SuppressWarnings("unchecked")
    public GatewayFilter apply(NameConfig config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange,
                                     GatewayFilterChain chain) {
                ServerRequest serverRequest = ServerRequest.create(exchange,
                        messageReaders);

                // read & modify body
                Mono<String> modifiedBody = serverRequest.bodyToMono(String.class)
                        .flatMap(body -> {
                            //xml2json
                            String json = null;
                            try {
                                json = JsonXmlUtil.soapToJson(body).toJSONString();
                            } catch (DocumentException e) {
                                e.printStackTrace();
                            }
                            return Mono.just(json);
                        });


                BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody,
                        String.class);
                HttpHeaders headers = new HttpHeaders();
                headers.putAll(exchange.getRequest().getHeaders());

                // the new content type will be computed by bodyInserter
                // and then set in the request decorator
                headers.remove(HttpHeaders.CONTENT_LENGTH);

                // if the body is changing content types, set it here, to the bodyInserter
                // will know about it
                headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(
                        exchange, headers);
                return bodyInserter.insert(outputMessage, new BodyInserterContext())
                        // .log("modify_request", Level.INFO)
                        .then(Mono.defer(() -> {
                            ServerHttpRequest decorator = decorate(exchange, headers,
                                    outputMessage);
                            return chain
                                    .filter(exchange.mutate().request(decorator).build());
                        })).onErrorResume(
                                (Function<Throwable, Mono<Void>>) throwable -> release(
                                        exchange, outputMessage, throwable));
            }
        };
    }

    protected Mono<Void> release(ServerWebExchange exchange,
                                 CachedBodyOutputMessage outputMessage, Throwable throwable) {
        //TODO
//        if (outputMessage.isCached()) {
        return outputMessage.getBody().map(DataBufferUtils::release)
                .then(Mono.error(throwable));
//        }
//        return Mono.error(throwable);
    }

    ServerHttpRequestDecorator decorate(ServerWebExchange exchange, HttpHeaders headers,
                                        CachedBodyOutputMessage outputMessage) {
        return new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public HttpHeaders getHeaders() {
                long contentLength = headers.getContentLength();
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(headers);
                if (contentLength > 0) {
                    httpHeaders.setContentLength(contentLength);
                } else {
                    // TODO: this causes a 'HTTP/1.1 411 Length Required' // on
                    // httpbin.org
                    httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                }
                return httpHeaders;
            }

            @Override
            public Flux<DataBuffer> getBody() {
                return outputMessage.getBody();
            }
        };
    }


}

