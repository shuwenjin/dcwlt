package com.dcits.dcwlt.common.pay.channel.bankcore.dto;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
//import com.rtp.dubbo.common.Head;

/**
 * @author zhanguohai
 * @Time 2020年3月30日下午3:38:43
 * @Version
 *          <p>
 *          Description: 核心通讯请求报文类
 *          </p>
 * @param <T>
 */
public class BankCoreReqMessage<T, A> {
	//private Head head; // 服务化报文头
	private BankCoreReqHeader bankCoreHeader; // 核心报文头
	private T body; // 通用报文体
	private List<A> coreBodyArrayInfo; // 核心报文体数组

	/**

	 * @param bankCoreHeader
	 *            核心报文头
	 * @param body
	 *            报文体
	 */
	public static <T, A> BankCoreReqMessage<T, A> newInstance(
			 BankCoreReqHeader bankCoreHeader, T body, List<A> coreBodyArrayInfo) {
		BankCoreReqMessage<T, A> msg = new BankCoreReqMessage<>();
		msg.setBody(body);
	//	msg.setHead(head);
		msg.setBankCoreHeader(bankCoreHeader);
		msg.setCoreBodyArrayInfo(coreBodyArrayInfo);
		return msg;
	}

	/**

	 * @param body
	 *            报文体
	 * @return
	 */
	public static <T, A> BankCoreReqMessage<T, A> newInstance(
			 T body, BankCoreReqHeader bankCoreHeader) {
		BankCoreReqMessage<T, A> msg = new BankCoreReqMessage<>();
		msg.setBody(body);
		msg.setBankCoreHeader(bankCoreHeader);
		//msg.setHead(head);
		return msg;
	}

	@JSONField(name = "coreBodyArrayInfo")
	public List<A> getCoreBodyArrayInfo() {
		return coreBodyArrayInfo;
	}

	public void setCoreBodyArrayInfo(List<A> coreBodyArrayInfo) {
		this.coreBodyArrayInfo = coreBodyArrayInfo;
	}

//	@JSONField(name = "head")
//	public Head getHead() {
//		return head;
//	}
//
//	public void setHead(Head head) {
//		this.head = head;
//	}

	@JSONField(name = "coreHead")
	public BankCoreReqHeader getBankCoreHeader() {
		return bankCoreHeader;
	}

	public void setBankCoreHeader(BankCoreReqHeader bankCoreHeader) {
		this.bankCoreHeader = bankCoreHeader;
	}

	@JSONField(name = "coreBodyInfo")
	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "BankCoreMessage [head=" + "" + ", bankCoreHeader=" + bankCoreHeader + ", body=" + body
				+ ", coreBodyArrayInfo=" + coreBodyArrayInfo + "]";
	}

}
