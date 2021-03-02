package com.dcits.dcwlt.pay.api.domain.dcep.chngctrl;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.common.pay.enums.ChangeCdEnum;
import com.dcits.dcwlt.common.pay.enums.EffectiveCdEnum;
import com.dcits.dcwlt.common.pay.validator.annotation.EnumValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.validation.constraints.NotBlank;
import java.time.format.DateTimeFormatter;

/**
 *
 * @desc 数据变更组件
 */
public class ChngCtrl {
    private static final Logger logger = LoggerFactory.getLogger(ChngCtrl.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
    private static final String isoDateTimePattern = "yyyy-mm-ddTHH:MM:SS";
    /*
     * 变更类型
     * */
    @NotBlank(message = "变更类型不能为空")
    @EnumValue(value = ChangeCdEnum.class, message = "变更类型格式有误")
    private String chngTp;
    /*
     *生效类型
     * */
    @NotBlank(message = "生效类型不能为空")
    @EnumValue(value = EffectiveCdEnum.class, message = "生效类型格式有误")
    private String fctvTp;
    /*
     * 生效日期
     * */
    private String fctvDt;
    /*
     * 失效日期
     * */
    private String ifctvDt;

//    /*
//     * 校验数据是否合法
//     * 不合法会抛出相应异常
//     * */
//    public void validate() {
//        //立即生效 则生效日期和失效日期 不填写
//        /*if (EffectiveCdEnum.EF00.getCode().equals(fctvTp)) {
//            if (null != fctvDt && !"".equals(fctvDt) || null != ifctvDt && !"".equals(ifctvDt)) {
//                logger.info("当前为立即生效,生效日期:{}和失效日期:{}不填写", fctvDt, ifctvDt);
//                throw new EcnyTransException(EcnyTransError.PARAMS_INVALID);
//            }
//        } else */
//        if (EffectiveCdEnum.EF01.getCode().equals(fctvTp)) { //指定日期生效
//            //变更类型为增加或更新
//            if (ChangeCdEnum.CC00.getCode().equals(chngTp) || ChangeCdEnum.CC01.getCode().equals(chngTp)) {
//                //生效日期必填,失效日期选填
//                if (null == fctvDt || "".equals(fctvDt)) {
//                    logger.info("当前为指定日期生效的新增或更新场景,生效日期:{}为必填", fctvDt);
//                    throw new EcnyTransException(EcnyTransError.PARAMS_INVALID);
//                } else {
//                    try {
//                        //校验生效日期格式
//                        dateTimeFormatter.parse(fctvDt);
//                        if (null != ifctvDt && !"".equals(ifctvDt)) {
//                            //校验失效日期
//                            dateTimeFormatter.parse(ifctvDt);
//                        }
//                    } catch (Exception e) {
//                        logger.error("请求报文中的日期字段：生效日期{}或失效日期{}不符合ISODateTime：{}格式", fctvDt, ifctvDt, isoDateTimePattern);
//                        throw new EcnyTransException(EcnyTransError.INPARAMS_INVALID);
//                    }
//                }
//            } else if (ChangeCdEnum.CC02.getCode().equals(chngTp)) { //变更类型删除
//                //失效日期必填,生效日期必不填
//                if (null == ifctvDt || "".equals(ifctvDt) || null != fctvDt && !"".equals(fctvDt)) {
//                    logger.info("当前为指定日期生效的删除场景,失效日期:{}为必填,生效日期:{}必不填", ifctvDt, fctvDt);
//                    throw new EcnyTransException(EcnyTransError.PARAMS_INVALID);
//                }
//                //校验失效日期
//                try {
//                    dateTimeFormatter.parse(ifctvDt);
//                } catch (Exception e) {
//                    logger.error("请求报文中的日期字段：{}不符合ISODateTime：{}格式", ifctvDt, isoDateTimePattern);
//                    throw new EcnyTransException(EcnyTransError.INPARAMS_INVALID);
//                }
//            }
//        }
//    }

    @JSONField(name = "ChngTp")
    public String getChngTp() {
        return chngTp;
    }

    public void setChngTp(String chngTp) {
        this.chngTp = chngTp;
    }

    @JSONField(name = "FctvTp")
    public String getFctvTp() {
        return fctvTp;
    }

    public void setFctvTp(String fctvTp) {
        this.fctvTp = fctvTp;
    }

    @JSONField(name = "FctvDt")
    public String getFctvDt() {
        return fctvDt;
    }

    public void setFctvDt(String fctvDt) {
        this.fctvDt = fctvDt;
    }

    @JSONField(name = "IfctvDt")
    public String getIfctvDt() {
        return ifctvDt;
    }

    public void setIfctvDt(String ifctvDt) {
        this.ifctvDt = ifctvDt;
    }

    @Override
    public String toString() {
        return "ChngCtrl{" +
                "chngTp=" + chngTp +
                ", fctvTp=" + fctvTp +
                ", fctvDt='" + fctvDt + '\'' +
                ", ifctvDt='" + ifctvDt + '\'' +
                '}';
    }
}
