package com.dcits.dcwlt.pay.batch.service.impl;



import com.dcits.dcwlt.common.pay.constant.Constant;
import com.dcits.dcwlt.common.pay.enums.SettleTaskErrorEnum;
import com.dcits.dcwlt.pay.api.domain.dcep.page.PageResult;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspHead;

import java.util.List;

public class BasicEncyService {
	
	public <T> ECNYRspDTO<PageResult<T>> createQueryResponseError(ECNYReqDTO req, String code, String msg) {
		//取入参
        ECNYRspHead ecnyRspHead = new ECNYRspHead();
        //查数据
        return ECNYRspDTO.newInstance(req, ecnyRspHead, null, code, msg);
	}
    
	public <T> ECNYRspDTO<PageResult<T>> createQueryResponse(ECNYReqDTO req, List<T> list, String pageCnt, String queryPageFlag) {
		//取入参
        ECNYRspDTO<PageResult<T>> ecnyRspDTO = new ECNYRspDTO<PageResult<T>>();
        ECNYRspHead ecnyRspHead = new ECNYRspHead();
        //查数据
        if (list != null && !list.isEmpty()) {
            //封装响应
            //组装body数据
            PageResult<T> rspBody = createPageResult(list, pageCnt, queryPageFlag);
            ecnyRspDTO = ECNYRspDTO.newInstance(req, ecnyRspHead, rspBody, SettleTaskErrorEnum.SUCCESS.getCode(), SettleTaskErrorEnum.SUCCESS.getDesc());
        } else {
            ecnyRspDTO = ECNYRspDTO.newInstance(req, ecnyRspHead, null, 
            		SettleTaskErrorEnum.NODATA.getCode(), SettleTaskErrorEnum.NODATA.getDesc());
        }
        return ecnyRspDTO;
	}
	
	public <T> PageResult<T> createPageResult(List<T> list, String count, String queryPageFlag) {
    	PageResult<T> res = new PageResult<T>();
    	int returnCount = 0;//返回给外围渠道的条数
    	int pageCnt = Integer.parseInt(count);
        String firstPgInd = Constant.JUDGE_YES;//是否第一页
        String lastPgInd = Constant.JUDGE_YES;//是否最后一页
    	if (Constant.QUERYPAGEFLAG_FIRST.equals(queryPageFlag)) {//首页
            if (list.size() > pageCnt) {//查询条数>每页显示条数，代表非首页
                returnCount = list.size() - 1;
                firstPgInd = Constant.JUDGE_YES;
                lastPgInd = Constant.JUDGE_NO;
                list.remove(list.size() - 1);
            } else {
                returnCount = list.size();
                firstPgInd = Constant.JUDGE_YES;
                lastPgInd = Constant.JUDGE_YES;
            }
        } else if (Constant.QUERYPAGEFLAG_DOWN.equals(queryPageFlag)) {//下页
            if (list.size() > pageCnt) {//查询条数>每页显示条数，代表非首页
                returnCount = list.size() - 1;
                firstPgInd = Constant.JUDGE_NO;
                lastPgInd = Constant.JUDGE_NO;
                list.remove(list.size() - 1);
            } else {
                returnCount = list.size();
                firstPgInd = Constant.JUDGE_NO;
                lastPgInd = Constant.JUDGE_YES;
            }
        } else if (Constant.QUERYPAGEFLAG_UP.equals(queryPageFlag)) {//上页
            if (list.size() > pageCnt) {//查询条数>每页显示条数，代表非首页
                returnCount = list.size() - 1;
                firstPgInd = Constant.JUDGE_NO;
                lastPgInd = Constant.JUDGE_NO;
                list.remove(0);
            } else {
                returnCount = list.size();
                firstPgInd = Constant.JUDGE_YES;
                lastPgInd = Constant.JUDGE_NO;
            }
        }
    	res.setFirstPageFlag(firstPgInd);
    	res.setLastPageFlag(lastPgInd);
    	res.setReturnCount(Integer.toString(returnCount));
    	res.setList(list);
    	return res;
    }
}
