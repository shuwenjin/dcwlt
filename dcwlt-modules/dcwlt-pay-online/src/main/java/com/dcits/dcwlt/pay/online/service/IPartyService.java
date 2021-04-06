package com.dcits.dcwlt.pay.online.service;

import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspDTO;
import com.dcits.dcwlt.pay.api.model.PartyInfoDO;

/**
 * 机构服务
 *
 * @author
 * @date 2021/1/3
 */
public interface IPartyService {

    /**
     * 移动临时表中的数据到表中
     */
    ECNYRspDTO effectiveParty(ECNYReqDTO reqDTO);

    /**
     * 判断机构是否可以发送或接收报文
     * @param partyId
     * @return
     */
    public boolean sendReceiveAble(String partyId);

    /**
     * 判断机构是否可以发送或接收报文
     * @param partyInfoDO
     * @return
     */
    public boolean sendReceiveAble(PartyInfoDO partyInfoDO);

    /**
     * 机构是否可用
     * 一般正常情况下，机构可以接受和发送报， 否则机构会拒绝报文
     *
     * @param partyInfoDO
     * @return
     */
    public boolean isAvailable(PartyInfoDO partyInfoDO);
    /**
     * 机构是否可用
     * 一般正常情况下，机构可以接受和发送报， 否则机构会拒绝报文
     *
     * @param partyId
     * @return
     */
     boolean isAvailable(String partyId);
}
