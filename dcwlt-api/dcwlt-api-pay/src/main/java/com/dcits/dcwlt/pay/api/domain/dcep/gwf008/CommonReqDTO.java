package com.dcits.dcwlt.pay.api.domain.dcep.gwf008;


import com.dcits.dcwlt.pay.api.domain.BaseReqDto;

/**
 * 开放平台初始实体
 *
 * @author
 * @date 2021/1/7
 */
public class CommonReqDTO<T extends CommonReqBody> extends BaseReqDto {

    private T body;

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    /**
     * 生成请求开放平台请求报文实例
     *
     * @param reqInstanceId 请求实例id
     * @param body          报文体
     */
    public static <T extends CommonReqBody> CommonReqDTO<T> newInstance(String reqInstanceId, T body) {
        //设置请求服务化报文头
//        Head reqHead = HeadUtil.newHead(reqInstanceId, ApiConstant.COP_SYS_ID, SequenceUtil.getSeq());
//
//        //返回互联互通请求报文
       return newInstance(//reqHead,
               body);
    }

    /**
     * 生成请求开放平台请求报文实例
     *
     * @param <T>
     * @param body
     * @return
     */
    public static <T extends CommonReqBody> CommonReqDTO<T> newInstance(//Head head,
                                                                        T body) {
        CommonReqDTO<T> commonReqDTO = new CommonReqDTO<>();
//        commonReqDTO.setHead(head);
        commonReqDTO.setBody(body);

        return commonReqDTO;
    }

    @Override
    public String toString() {
        return "CommonReqDTO{" +
                "body=" + body +
                //", head=" + head +
                //", protocolType=" + protocolType +
                '}';
    }
}
