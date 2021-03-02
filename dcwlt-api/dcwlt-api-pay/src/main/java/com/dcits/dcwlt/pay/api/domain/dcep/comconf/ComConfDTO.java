package com.dcits.dcwlt.pay.api.domain.dcep.comconf;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.common.pay.enums.ProcessStatusCodeEnum;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPHeader;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspBody;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;


import javax.validation.Valid;

/**
 *
 * @desc 通信级响应体
 */
public class ComConfDTO extends DCEPRspBody {
    @Valid
    private ComConf comConf;

    /*
     * 初始化通信级确认报文
     * @Param: GrpHdr
     * @Param: DCEPHeader
     * */
    public static ComConfDTO newInstance(GrpHdr grpHdr, DCEPHeader dcepHeader, String remark ) {
        ComConfDTO comConfDTO = new ComConfDTO();
        ComConf comConf = new ComConf();
        ConfInf confInf = new ConfInf();
        confInf.setPrcSts(ProcessStatusCodeEnum.PR00.getValue());
        confInf.setOrigSndDtTm(dcepHeader.getSndDtTm());
        confInf.setOrgnlInstgPty(grpHdr.getInstgPty().getInstgDrctPty());
        confInf.setOrgnlMsgId(grpHdr.getMsgId());
        confInf.setOrgnlMT(dcepHeader.getMsgTp());
        confInf.setRemark(remark);

        comConf.setConfInf(confInf);
        comConfDTO.setComConf(comConf);
        return comConfDTO;
    }

    /*
     * 初始化通信级确认报文
     * @Param: GrpHdr
     * @Param: DCEPHeader
     * */
    public static ComConfDTO newInstance(GrpHdr grpHdr, DCEPHeader dcepHeader ) {
        ComConfDTO comConfDTO = new ComConfDTO();
        ComConf comConf = new ComConf();
        ConfInf confInf = new ConfInf();
        confInf.setPrcSts(ProcessStatusCodeEnum.PR00.getValue());
        confInf.setOrigSndDtTm(dcepHeader.getSndDtTm());
        confInf.setOrgnlInstgPty(grpHdr.getInstgPty().getInstgDrctPty());
        confInf.setOrgnlMsgId(grpHdr.getMsgId());
        confInf.setOrgnlMT(dcepHeader.getMsgTp());

        comConf.setConfInf(confInf);
        comConfDTO.setComConf(comConf);
        return comConfDTO;
    }

    @JSONField(name = "ComConf")
    public ComConf getComConf() {
        return comConf;
    }

    public void setComConf(ComConf comConf) {
        this.comConf = comConf;
    }

    @Override
    public String toString() {
        return "ComConfDTO{" +
                "ComConf=" + comConf +
                '}';
    }
}
