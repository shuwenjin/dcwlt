package com.core.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.core.demo.entity.TryIdCode;
import com.core.demo.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 *  服务层实现
 * 
 * @author yxk
 */
@Service
public class CoreBackServiceImpl implements CoreBackService{
    private static final Logger logger = LoggerFactory.getLogger(CoreBackServiceImpl.class);
    /**
     * 模拟核心接口返回不同的响应json数据
     * @return
     */
    @Override
    public JSONObject replaceAuthInfo(String trId) {
        JSONObject resultJson = null;
        if(trId.equals(TryIdCode.E001.getCode())){
            resultJson=return351100();
        }else if(trId.equals(TryIdCode.E002.getCode())){
            resultJson=return358040();
        }else if(trId.equals(TryIdCode.E003.getCode())){
            resultJson=return996666();
        }else if(trId.equals(TryIdCode.E004.getCode())){
            resultJson=return998889();
        }else if(trId.equals(TryIdCode.E005.getCode())){
            resultJson=returnSMS();
        }else{
           return null;
        }
        return resultJson;
    }

    /**
     * 核心记账
     * @return
     */
     public JSONObject return351100(){
        JSONObject resultJson = null;
        try {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("coreStatus", "1");                        // 核心状态
            resultMap.put("errorCode", "000000");                    // 处理码
            resultMap.put("errorMsg", "成功");              // 处理信息
            resultMap.put("coreReqDate", DateUtil.getDefaultDate());// 核心请求日期
            resultMap.put("coreReqSerno", "1");                     // 核心请求流水
            resultMap.put("coreRspDate",DateUtil.getDefaultDate()); // 核心应答日期（核心会计日期）
            resultMap.put("coreRspSerno", "1");                     // 核心应答流水（核心会计流水）
            resultMap.put("ac2RvsNo", "1");                         // 贷方挂账编号
            resultMap.put("hostDate", DateUtil.getDefaultDate());   // 核心处理日期（核心生成的流水文件，以该日期取数）
            resultJson = JSONObject.parseObject(JSONObject.toJSONString(resultMap));
            logger.info("核心记账:"+resultJson.toJSONString());
        }catch (Exception e) {
            logger.error("核心返回json异常：{}-{}", e.getMessage(), e);
        }
         return resultJson;
     }
    /**
     * 核心账户信息查询
     * @return
     */
    public JSONObject return358040(){
        JSONObject resultJson = null;
        try {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("ac","6214622121003509638");							   //卡号账号
            resultMap.put("acNo","6214622121003509638");                           //账号
            resultMap.put("acSeq","01");                          //序号
            resultMap.put("ccy","AMT");                            //币别
            resultMap.put("ciNo","12345678");                       //客户号
            resultMap.put("ciSts","0");                          //客户状态
            resultMap.put("type","1");                           //客户类型
            resultMap.put("idType","IT01");                         //证件类型
            resultMap.put("idNo","612427199300000");                           //证件号码
            resultMap.put("idAvalDt","0413");                       //开户证件有效日期
            resultMap.put("ciName","袁小花");                         //客户名称
            resultMap.put("ciLvl","1");                          //客户级别
            resultMap.put("sex","01");                            //性别
            resultMap.put("telNo","18710919222");                          //手机号码
            resultMap.put("addrType","01");                       //地址类型
            resultMap.put("addrFlg","01");                        //默认地址标识
            resultMap.put("addrNm","陕西省xx市xx县");                         //地址详细信息
            resultMap.put("addrCntyCd","000000");                     //国家代码
            resultMap.put("addrPostCd","750000");                     //邮政编码
            resultMap.put("acPrdCode","01");                      //账户产品码
            resultMap.put("acPrdName","账户产品名称");              //账户产品名称
            resultMap.put("acAttr","26");                         //账户类型
            resultMap.put("acType","01");                         //账户性质
            resultMap.put("acSts","N");                          //账户状态
            resultMap.put("acStsw","1");                         //账户状态字
            resultMap.put("acBlockSts","N");                     //账户冻结状态
            resultMap.put("amtHoldSts","00");                     //金额冻结状态
            resultMap.put("acBr","xx网点");                           //开户网点
            resultMap.put("acFh","XX支行");                           //开户分行
            resultMap.put("openDate","20210414");                       //开户日期
            resultMap.put("lastDate","20210414");                       //最后交易日期
            resultMap.put("bookBr","");                         //账务行
            resultMap.put("domBr","");                          //管理行
            resultMap.put("term","");                           //存期
            resultMap.put("instrMth","");                       //到期指示
            resultMap.put("ccyTyp","");                         //钞汇标志
            resultMap.put("intRat","");                         //利率值
            resultMap.put("acEname","");                        //账户英文名称
            resultMap.put("acCname","");                        //账户中文名称
            resultMap.put("ledgerBal","");                      //账面余额
            resultMap.put("availBal","");                       //可用余额
            resultMap.put("holdBal","");                        //冻结金额
            resultMap.put("agrDepFlg","");                      //协定存款标志
            resultMap.put("agrRat","");                         //协定利率
            resultMap.put("agrAmt","");                         //协定金额
            resultMap.put("drawMth","");                        //支取方式
            resultMap.put("drcrFlg","");                        //借贷记标志
            resultMap.put("crosDrFlg","");                      //通兑标志
            resultMap.put("crosCrFlg","");                      //通存标志
            resultMap.put("pbSts","");                          //存折挂失状态
            resultMap.put("cardMedi ","");                      //卡介质类型
            resultMap.put("cardSts","");                        //卡介质状态
            resultMap.put("cardStsw","");                       //卡使用状态
            resultMap.put("cardProd","");                       //卡产品
            resultMap.put("cardProdNm","");                     //卡产品名称
            resultMap.put("cardJointTyp","N");                   //卡联名标志
            resultMap.put("cardPcTyp","");                      //卡归属类别
            resultMap.put("cardPvTyp","");                      //卡物理属性
            resultMap.put("cardMobiTyp","");                    //支持移动支付标识
            resultMap.put("cardLinkTyp","");                    //主副卡标志
            resultMap.put("cardCatlg","");                      //卡类
            resultMap.put("issueBr","");                        //开卡网点
            resultMap.put("brNm","");                           //开卡网点名称
            resultMap.put("issueDt","");                        //开卡日期
            resultMap.put("expDt","");                          //失效日期
            resultMap.put("item","");                           //所属科目
            resultMap.put("stdAcFlg","");                       //账号标志
            resultMap.put("nonStdActyp","");                    //非标准账号类型
            resultMap.put("mainAc","6214622121003509638");                         //主账号
            resultMap.put("stdAc","6214622121003509638");                          //标准账号
            resultMap.put("spcKind","");                        //专用户资金性质
            resultMap.put("frgInd","");                         //外汇属性标注
            resultMap.put("yyzz","");                           //企业营业执照
            resultMap.put("ciStsw","1");                         //客户状态字
            resultMap.put("socialFlg","");                      //社保金融卡标识
            resultMap.put("acPurp","");                         //账户用途
            resultMap.put("relType","");                        //对公关联人类型
            resultMap.put("relName","");                        //对公关联人名称
            resultMap.put("flg1","");                           //定期账户凭证类型
            resultMap.put("flg2","");                           //定期存单质押状态
            resultMap.put("flg3","");                           //卡定向账户交易标志
            resultMap.put("flg4","");                           //货币标识
            resultMap.put("flg5","");                           //预留标识位5
            resultMap.put("reserved","");                       //预留栏位
            resultMap.put("frgType","");                        //对公外汇账户类型
            resultMap.put("frgCode","");                        //对公外汇账户性质代码
            resultMap.put("acClass","1");                        //账户分类
            resultMap.put("ftfFlg","");                         //面签标识
            resultMap.put("rmk","");                            //预留栏位
            resultMap.put("nonStdAcCnt","");                    //非标账号个数
            resultJson = JSONObject.parseObject(JSONObject.toJSONString(resultMap));
            logger.info("核心账户信息查询:"+resultJson.toJSONString());
        }catch (Exception e) {
            logger.error("核心返回json异常：{}-{}", e.getMessage(), e);
        }
        return resultJson;
    }
    /**
     * 核心交易状态查询
     * @return
     */
    public JSONObject return996666(){
        JSONObject resultJson = null;
        try {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("txnSts","1");                                // 交易状态
            resultMap.put("hostJrnno","123");                           // 主机交易流水
            resultMap.put("hostAcdate",DateUtil.getDefaultDate());      // 主机日期
            resultMap.put("coreRetCode","000000");                     // 原正交易的核心处理码
            resultMap.put("coreRetMsg","核心处理成功");                 // 原正交易的核心处理信息
            resultJson = JSONObject.parseObject(JSONObject.toJSONString(resultMap));
            logger.info("核心交易状态查询:"+resultJson.toJSONString());
        }catch (Exception e) {
            logger.error("核心返回json异常：{}-{}", e.getMessage(), e);
        }
        return resultJson;
    }

    /**
     *核心冲正
     * @return
     */
    public JSONObject return998889(){
        JSONObject resultJson = null;
        try {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("errorCode","000000");//核心返回码M
            resultMap.put("errorMsg","冲正成功");//核心返回信息M
            resultMap.put("reqCoreDate",DateUtil.getDefaultDate());//主机请求日期
            resultMap.put("reqCoreSerno","1234");//主机请求流水M
            resultMap.put("rspCoreDate",DateUtil.getDefaultDate());//主机返回日期
            resultMap.put("rspCoreSerno","12345");//主机返回流水M
            resultMap.put("coreStatus","1");//核心返回状态
            resultJson = JSONObject.parseObject(JSONObject.toJSONString(resultMap));
            logger.info("核心冲正:"+resultJson.toJSONString());
        }catch (Exception e) {
            logger.error("核心返回json异常：{}-{}", e.getMessage(), e);
        }
        return resultJson;
    }

    /**
     * 发送短信
     * @return
     */
    public JSONObject returnSMS(){
        JSONObject resultJson = null;
        try {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("errorCode","000000");//核心返回码M
            resultMap.put("errorMsg","发送成功");//核心返回信息M
            resultMap.put("reqCoreDate",DateUtil.getDefaultDate());//主机请求日期
            resultJson = JSONObject.parseObject(JSONObject.toJSONString(resultMap));
            logger.info("发送短信:"+resultJson.toJSONString());
        }catch (Exception e) {
            logger.error("核心返回json异常：{}-{}", e.getMessage(), e);
        }
        return resultJson;
    }
}
