package com.dcits.dcwlt.gateway.utils;

import org.dom4j.DocumentException;

public class TEXT {
	public static void main(String[] args) throws DocumentException {
		String a = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!--兑回是指用户将钱包中的DC/EP兑换为银行存款。-->\n" +
				"<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:head=\"http://www.dcep.com/dcep/header/\">\n" +
				"    <soap:Header>\n" +
				"        <head:Ver>01</head:Ver> <!--版本号,目前固定填写01-->\n" +
				"        <head:SndDtTm>2021-01-13T16:49:07</head:SndDtTm><!--系统时间6分钟以内-->\n" +
				"        <head:MsgTp>dcep.221.001.01</head:MsgTp>  <!--报文编号-->\n" +
				"        <head:MsgSN>202101130001221845953462465000000001</head:MsgSN>\n" +
				"        <!--业务的通信层标识，同一笔业务可以发起多次通信请求（如超时重试等），MsgId一样，MsgSN不一样,前32位取MsgId，后四位从0001开始递增-->\n" +
				"        <head:Sender>C1010411000013</head:Sender> <!--发起方运营机构-->\n" +
				"        <head:Receiver>C1030644021075</head:Receiver> <!--接收方运营机构 农业银行-->\n" +
				"        <head:SignSN>4</head:SignSN><!--签名证书序列号-->\n" +
				"    </soap:Header>\n" +
				"    <soap:Body>\n" +
				"        <wstxns1:ReconvertReq xmlns:wstxns1=\"http://www.dcep.com/dcep/22100101/\">\n" +
				"            <GrpHdr>\n" +
				"                <!--报文标识号-->\n" +
				"                <MsgId>20210113000122184595346246598765</MsgId>\n" +
				"                <!--报文发送时间-->\n" +
				"                <CreDtTm>2021-01-13T16:49:07</CreDtTm>\n" +
				"                <InstgPty>\n" +
				"                    <!--发起运营机构-->\n" +
				"                    <InstgDrctPty>C1010411000013</InstgDrctPty>\n" +
				"                </InstgPty>\n" +
				"                <InstdPty>\n" +
				"                    <!--接收运营机构-->\n" +
				"                    <InstdDrctPty>C1030644021075</InstdDrctPty>\n" +
				"                </InstdPty>\n" +
				"                <!--备注-->\n" +
				"\n" +
				"            </GrpHdr>\n" +
				"            <TrxInf>\n" +
				"                <TrxBizTp>C201</TrxBizTp>  <!--业务类型编码-->\n" +
				"                <TrxCtgyPurpCd>03011</TrxCtgyPurpCd>  <!--业务种类编码-->\n" +
				"                <TrxAmt Ccy=\"CNY\">11.00</TrxAmt>    <!--交易金额-->\n" +
				"                <BatchId>B202101131600</BatchId>    <!--交易批次号-->\n" +
				"            </TrxInf>\n" +
				"            <DbtrInf><!--兑回钱包信息--><!--兑回是指用户将钱包中的DC/EP兑换为银行存款。-->\n" +
				"                <DbtrPtyId>C1010411000013</DbtrPtyId>   <!--付款人钱包所属运营机构-->\n" +
				"                <DbtrNm>测试1</DbtrNm>   <!--付款人名称-->\n" +
				"                <DbtrWltId>101112345678916</DbtrWltId>   <!--付款人钱包ID-->\n" +
				"                <DbtrWltLvl>WL01</DbtrWltLvl>   <!--付款人钱包等级-->\n" +
				"                <DbtrWltTp>WT01</DbtrWltTp>   <!--付款人钱包类型-->\n" +
				"                <DbtrWltNm>测试钱包</DbtrWltNm>   <!--付款人钱包名称-->\n" +
				"            </DbtrInf>\n" +
				"            <CdtrInf><!--收款人信息-->\n" +
				"                <CdtrPtyId>C1030644021075</CdtrPtyId>   <!--收款人账户所属运营机构-->\n" +
				"                <CdtrAcctTp>AT00</CdtrAcctTp>   <!--收款人账户类型-->\n" +
				"                <CdtrNm>造数泰伯</CdtrNm>   <!--收款人名称-->\n" +
				"                <CdtrAcct>6214622121002964305</CdtrAcct>   <!--收款人账户账号-->\n" +
				"            </CdtrInf>\n" +
				"        </wstxns1:ReconvertReq>\n" +
				"    </soap:Body>\n" +
				"</soap:Envelope>\n";

		System.out.println(JsonXmlUtil.soapToJson(a));
	}
}
