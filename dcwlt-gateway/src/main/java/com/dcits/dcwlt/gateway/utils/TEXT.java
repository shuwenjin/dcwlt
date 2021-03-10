package com.dcits.dcwlt.gateway.utils;

import org.dom4j.DocumentException;

public class TEXT {
	public static void main(String[] args) throws DocumentException {
		String a = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
				"<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:head=\"http://www.dcep.com/dcep/header/\">\n" +
				"    <soap:Header>\n" +
				"        <head:Ver>01</head:Ver>\n" +
				"        <head:SndDtTm>2021-01-13T16:02:17</head:SndDtTm>\n" +
				"        <head:MsgTp>dcep.711.001.01</head:MsgTp>\n" +
				"        <head:MsgSN>202101130001711255750847067000000001</head:MsgSN>\n" +
				"        <head:Sender>G4001011000013</head:Sender>\n" +
				"        <head:Receiver>C1030644021075</head:Receiver>\n" +
				"        <head:SignSN>4</head:SignSN>\n" +
				"    </soap:Header>\n" +
				"    <soap:Body>\n" +
				"        <wstxns1:ReconSummaryChk xmlns:wstxns1=\"http://www.dcep.com/dcep/71100101/\">\n" +
				"            <GrpHdr>\n" +
				"                <MsgId>20210113000171125575084706700000</MsgId>\n" +
				"                <CreDtTm>2021-01-13T16:02:17</CreDtTm>\n" +
				"                <InstgPty>\n" +
				"                    <InstgDrctPty>G4001011000013</InstgDrctPty>\n" +
				"                </InstgPty>\n" +
				"                <InstdPty>\n" +
				"                    <InstdDrctPty>C1030644021075</InstdDrctPty>\n" +
				"                </InstdPty>\n" +
				"                <Rmk>机构对账汇总核对报文</Rmk>\n" +
				"            </GrpHdr>\n" +
				"            <SummaryChkInf>\n" +
				"                <SummaryHdr>\n" +
				"                    <BatchId>B202101131600</BatchId>\n" +
				"                    <CntNb>15</CntNb>\n" +
				"                    <CntAmt Ccy=\"CNY\">10161.34</CntAmt>\n" +
				"                    <DbtCntNb>4</DbtCntNb>\n" +
				"                    <DbtCntAmt Ccy=\"CNY\">103.96</DbtCntAmt>\n" +
				"                    <CdtCntNb>11</CdtCntNb>\n" +
				"                    <CdtCntAmt Ccy=\"CNY\">10057.38</CdtCntAmt>\n" +
				"                </SummaryHdr>\n" +
				"                <SummaryBody>\n" +
				"                    <SummaryGrp>\n" +
				"                        <Nb>00</Nb>\n" +
				"                        <CntNb>2</CntNb>\n" +
				"                        <CntAmt Ccy=\"CNY\">10001.00</CntAmt>\n" +
				"                        <DbtCntNb>0</DbtCntNb>\n" +
				"                        <DbtCntAmt Ccy=\"CNY\">0.00</DbtCntAmt>\n" +
				"                        <CdtCntNb>2</CdtCntNb>\n" +
				"                        <CdtCntAmt Ccy=\"CNY\">10001.00</CdtCntAmt>\n" +
				"                        <ChkPayList>\n" +
				"                            <ChkPayInf>\n" +
				"                                <MsgTp>dcep.221.001.01</MsgTp>\n" +
				"                                <BizSts>PR00</BizSts>\n" +
				"                                <CntNb>1</CntNb>\n" +
				"                                <CntAmt Ccy=\"CNY\">1.00</CntAmt>\n" +
				"                                <DbtCntNb>0</DbtCntNb>\n" +
				"                                <DbtCntAmt Ccy=\"CNY\">0.00</DbtCntAmt>\n" +
				"                                <CdtCntNb>1</CdtCntNb>\n" +
				"                                <CdtCntAmt Ccy=\"CNY\">1.00</CdtCntAmt>\n" +
				"                            </ChkPayInf>\n" +
				"                            <ChkPayInf>\n" +
				"                                <MsgTp>dcep.227.001.01</MsgTp>\n" +
				"                                <BizSts>PR01</BizSts>\n" +
				"                                <CntNb>1</CntNb>\n" +
				"                                <CntAmt Ccy=\"CNY\">10000.00</CntAmt>\n" +
				"                                <DbtCntNb>0</DbtCntNb>\n" +
				"                                <DbtCntAmt Ccy=\"CNY\">0.00</DbtCntAmt>\n" +
				"                                <CdtCntNb>1</CdtCntNb>\n" +
				"                                <CdtCntAmt Ccy=\"CNY\">10000.00</CdtCntAmt>\n" +
				"                            </ChkPayInf>\n" +
				"                        </ChkPayList>\n" +
				"                    </SummaryGrp>\n" +
				"                    <SummaryGrp>\n" +
				"                        <Nb>68</Nb>\n" +
				"                        <CntNb>2</CntNb>\n" +
				"                        <CntAmt Ccy=\"CNY\">0.07</CntAmt>\n" +
				"                        <DbtCntNb>0</DbtCntNb>\n" +
				"                        <DbtCntAmt Ccy=\"CNY\">0.00</DbtCntAmt>\n" +
				"                        <CdtCntNb>2</CdtCntNb>\n" +
				"                        <CdtCntAmt Ccy=\"CNY\">0.07</CdtCntAmt>\n" +
				"                        <ChkPayList>\n" +
				"                            <ChkPayInf>\n" +
				"                                <MsgTp>dcep.227.001.01</MsgTp>\n" +
				"                                <BizSts>PR00</BizSts>\n" +
				"                                <CntNb>1</CntNb>\n" +
				"                                <CntAmt Ccy=\"CNY\">0.01</CntAmt>\n" +
				"                                <DbtCntNb>0</DbtCntNb>\n" +
				"                                <DbtCntAmt Ccy=\"CNY\">0.00</DbtCntAmt>\n" +
				"                                <CdtCntNb>1</CdtCntNb>\n" +
				"                                <CdtCntAmt Ccy=\"CNY\">0.01</CdtCntAmt>\n" +
				"                            </ChkPayInf>\n" +
				"                            <ChkPayInf>\n" +
				"                                <MsgTp>dcep.227.001.01</MsgTp>\n" +
				"                                <BizSts>PR03</BizSts>\n" +
				"                                <CntNb>1</CntNb>\n" +
				"                                <CntAmt Ccy=\"CNY\">0.06</CntAmt>\n" +
				"                                <DbtCntNb>0</DbtCntNb>\n" +
				"                                <DbtCntAmt Ccy=\"CNY\">0.00</DbtCntAmt>\n" +
				"                                <CdtCntNb>1</CdtCntNb>\n" +
				"                                <CdtCntAmt Ccy=\"CNY\">0.06</CdtCntAmt>\n" +
				"                            </ChkPayInf>\n" +
				"                        </ChkPayList>\n" +
				"                    </SummaryGrp>\n" +
				"                    <SummaryGrp>\n" +
				"                        <Nb>76</Nb>\n" +
				"                        <CntNb>2</CntNb>\n" +
				"                        <CntAmt Ccy=\"CNY\">0.20</CntAmt>\n" +
				"                        <DbtCntNb>0</DbtCntNb>\n" +
				"                        <DbtCntAmt Ccy=\"CNY\">0.00</DbtCntAmt>\n" +
				"                        <CdtCntNb>2</CdtCntNb>\n" +
				"                        <CdtCntAmt Ccy=\"CNY\">0.20</CdtCntAmt>\n" +
				"                        <ChkPayList>\n" +
				"                            <ChkPayInf>\n" +
				"                                <MsgTp>dcep.227.001.01</MsgTp>\n" +
				"                                <BizSts>PR00</BizSts>\n" +
				"                                <CntNb>1</CntNb>\n" +
				"                                <CntAmt Ccy=\"CNY\">0.10</CntAmt>\n" +
				"                                <DbtCntNb>0</DbtCntNb>\n" +
				"                                <DbtCntAmt Ccy=\"CNY\">0.00</DbtCntAmt>\n" +
				"                                <CdtCntNb>1</CdtCntNb>\n" +
				"                                <CdtCntAmt Ccy=\"CNY\">0.10</CdtCntAmt>\n" +
				"                            </ChkPayInf>\n" +
				"                            <ChkPayInf>\n" +
				"                                <MsgTp>dcep.227.001.01</MsgTp>\n" +
				"                                <BizSts>PR03</BizSts>\n" +
				"                                <CntNb>1</CntNb>\n" +
				"                                <CntAmt Ccy=\"CNY\">0.10</CntAmt>\n" +
				"                                <DbtCntNb>0</DbtCntNb>\n" +
				"                                <DbtCntAmt Ccy=\"CNY\">0.00</DbtCntAmt>\n" +
				"                                <CdtCntNb>1</CdtCntNb>\n" +
				"                                <CdtCntAmt Ccy=\"CNY\">0.10</CdtCntAmt>\n" +
				"                            </ChkPayInf>\n" +
				"                        </ChkPayList>\n" +
				"                    </SummaryGrp>\n" +
				"                </SummaryBody>\n" +
				"                <DtlFileInf>\n" +
				"                    <FileInfNb>9</FileInfNb>\n" +
				"                    <FileInfList>\n" +
				"                        <FileInf>\n" +
				"                            <FilePath>127.0.0.1:22/dcrecon/20200525/C1010511003703/trade/00/</FilePath>\n" +
				"                            <FileNameList>\n" +
				"                                <FileName>B202005251400_00_01.zip.sec</FileName>\n" +
				"                            </FileNameList>\n" +
				"                        </FileInf>\n" +
				"                        <FileInf>\n" +
				"                            <FilePath>127.0.0.1:22/dcrecon/20200525/C1010511003703/trade/01/</FilePath>\n" +
				"                            <FileNameList>\n" +
				"                                <FileName>B202005251400_01_01.zip.sec</FileName>\n" +
				"                            </FileNameList>\n" +
				"                        </FileInf>\n" +
				"                        <FileInf>\n" +
				"                            <FilePath>127.0.0.1:22/dcrecon/20200525/C1010511003703/trade/02/</FilePath>\n" +
				"                            <FileNameList>\n" +
				"                                <FileName>B202005251400_02_01.zip.sec</FileName>\n" +
				"                            </FileNameList>\n" +
				"                        </FileInf>\n" +
				"                        <FileInf>\n" +
				"                            <FilePath>127.0.0.1:22/dcrecon/20200525/C1010511003703/trade/03/</FilePath>\n" +
				"                            <FileNameList>\n" +
				"                                <FileName>B202005251400_03_01.zip.sec</FileName>\n" +
				"                            </FileNameList>\n" +
				"                        </FileInf>\n" +
				"                        <FileInf>\n" +
				"                            <FilePath>127.0.0.1:22/dcrecon/20200525/C1010511003703/trade/33/</FilePath>\n" +
				"                            <FileNameList>\n" +
				"                                <FileName>B202005251400_33_01.zip.sec</FileName>\n" +
				"                            </FileNameList>\n" +
				"                        </FileInf>\n" +
				"                        <FileInf>\n" +
				"                            <FilePath>127.0.0.1:22/dcrecon/20200525/C1010511003703/trade/46/</FilePath>\n" +
				"                            <FileNameList>\n" +
				"                                <FileName>B202005251400_46_01.zip.sec</FileName>\n" +
				"                            </FileNameList>\n" +
				"                        </FileInf>\n" +
				"                        <FileInf>\n" +
				"                            <FilePath>127.0.0.1:22/dcrecon/20200525/C1010511003703/trade/68/</FilePath>\n" +
				"                            <FileNameList>\n" +
				"                                <FileName>B202005251400_68_01.zip.sec</FileName>\n" +
				"                            </FileNameList>\n" +
				"                        </FileInf>\n" +
				"                        <FileInf>\n" +
				"                            <FilePath>127.0.0.1:22/dcrecon/20200525/C1010511003703/trade/73/</FilePath>\n" +
				"                            <FileNameList>\n" +
				"                                <FileName>B202005251400_73_01.zip.sec</FileName>\n" +
				"                            </FileNameList>\n" +
				"                        </FileInf>\n" +
				"                        <FileInf>\n" +
				"                            <FilePath>127.0.0.1:22/dcrecon/20200525/C1010511003703/trade/76/</FilePath>\n" +
				"                            <FileNameList>\n" +
				"                                <FileName>B202005251400_76_01.zip.sec</FileName>\n" +
				"                            </FileNameList>\n" +
				"                        </FileInf>\n" +
				"                    </FileInfList>\n" +
				"                </DtlFileInf>\n" +
				"                <ReconIndex>0x7b75633e709800a1e4ddc97a1fb54d56a65593b432ea79eeab57cec41dc229ba</ReconIndex>\n" +
				"            </SummaryChkInf>\n" +
				"        </wstxns1:ReconSummaryChk>\n" +
				"    </soap:Body>\n" +
				"</soap:Envelope>";

		System.out.println(JsonXmlUtil.soapToJson(a));
	}
}
