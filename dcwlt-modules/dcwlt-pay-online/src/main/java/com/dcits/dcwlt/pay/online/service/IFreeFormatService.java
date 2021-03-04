package com.dcits.dcwlt.pay.online.service;


import com.dcits.dcwlt.pay.api.domain.dcep.freefrmt.FreeFrmt;

public interface IFreeFormatService {
    /*
     * 保存自由格式
     * @param freeFrmt 自由格式报文
     * @Param direct 往来方向
     * @Param tlrNO 柜员号(非必输)
     * */
    int insertOrUpdateFreeFormat(FreeFrmt freeFrmt, String direct, String tlrNO, String flag);

}
