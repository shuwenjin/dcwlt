package com.dcits.dcwlt.pay.api.domain.dcep.party.chng;

import com.alibaba.fastjson.annotation.JSONField;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.common.NbInf;
import com.dcits.dcwlt.pay.api.domain.dcep.common.Prttn;
import com.dcits.dcwlt.pay.api.domain.dcep.party.Party;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 机构变更报文
 *
 * @date 2021/1/2
 */
public class FinCdChngNtfctn {
    @Valid
    @NotNull
    @JSONField(name = "GrpHdr")
    private GrpHdr grpHdr;              //业务头组信息

    @Valid
    @NotNull
    @JSONField(name = "Prttn")
    private Prttn prttn;                //报文分片组件

    @Valid
    @NotNull
    @JSONField(name = "NbInf")
    private NbInf nbInf;                //期数信息

    @Valid
    @NotNull
    @JSONField(name = "ChngInf")
    private List<Party> chngInf;        //运营机构变更信息

    public GrpHdr getGrpHdr() {
        return grpHdr;
    }

    public void setGrpHdr(GrpHdr grpHdr) {
        this.grpHdr = grpHdr;
    }

    public Prttn getPrttn() {
        return prttn;
    }

    public void setPrttn(Prttn prttn) {
        this.prttn = prttn;
    }

    public NbInf getNbInf() {
        return nbInf;
    }

    public void setNbInf(NbInf nbInf) {
        this.nbInf = nbInf;
    }

    public List<Party> getChngInf() {
        return chngInf;
    }

    public void setChngInf(List<Party> chngInf) {
        this.chngInf = chngInf;
    }

    @Override
    public String toString() {
        return "FinCdChngNtfctn{" +
                "grpHdr=" + grpHdr +
                ", prttn=" + prttn +
                ", nbInf=" + nbInf +
                ", chngInf=" + chngInf +
                '}';
    }
}
