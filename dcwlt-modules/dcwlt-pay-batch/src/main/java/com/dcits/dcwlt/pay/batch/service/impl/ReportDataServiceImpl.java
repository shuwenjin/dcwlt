package com.dcits.dcwlt.pay.batch.service.impl;

import com.dcits.dcwlt.common.pay.enums.ManagementTpCdEnum;
import com.dcits.dcwlt.common.pay.enums.MsgTpEnum;
import com.dcits.dcwlt.common.pay.enums.TrxStatusEnum;
import com.dcits.dcwlt.pay.api.model.FinanceReportDO;
import com.dcits.dcwlt.pay.api.model.NonfinanceReportDO;
import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.api.model.PayTransDtlNonfDO;
import com.dcits.dcwlt.pay.batch.mapper.FinanceReportDOMapper;
import com.dcits.dcwlt.pay.batch.mapper.NonfinanceReportDOMapper;
import com.dcits.dcwlt.pay.batch.mapper.PayPayTransdtlMapper;
import com.dcits.dcwlt.pay.batch.mapper.PayPayTransdtlNonfMapper;
import com.dcits.dcwlt.pay.batch.service.IReportDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by yangjld on 2021/3/12 0012.
 */
@Service
public class ReportDataServiceImpl implements IReportDataService {

    @Autowired
    private FinanceReportDOMapper financeReportDOMapper;

    @Autowired
    private NonfinanceReportDOMapper nonfinanceReportDOMapper;

    @Autowired
    public PayPayTransdtlMapper payPayTransdtlMapper;

    @Autowired
    private PayPayTransdtlNonfMapper payPayTransdtlNonfMapper;

    @Override
    public void statistics(String reportDate)
    {
        financeReportStatistics(reportDate);
        nonfinanceReportStatistics(reportDate);
    }

    // 金融报表统计
    private void financeReportStatistics(String reportDate)
    {
        try {
            FinanceReportDO financeReportDO = new FinanceReportDO();

            PayTransDtlInfoDO payTransDtlInfoDO = new PayTransDtlInfoDO();
            payTransDtlInfoDO.setPayDate(reportDate);

            // 兑出请求的统计
            payTransDtlInfoDO.setMsgType(MsgTpEnum.DCEP225.getCode());
            List<PayTransDtlInfoDO> payCashoutInfoList = payPayTransdtlMapper.selectPayPayTransdtlList(payTransDtlInfoDO);

            if(payCashoutInfoList != null && payCashoutInfoList.size() >0)
            {
                BigDecimal price = new BigDecimal("0");
                Integer number = 0;
                for(PayTransDtlInfoDO obj : payCashoutInfoList)
                {
                    if(TrxStatusEnum.SUCCESS.getCode().equals(obj.getTrxStatus()))
                    {
                        price = price.add(new BigDecimal(obj.getAmount()));
                        number = number + 1;
                    }
                }

                financeReportDO.setCashoutAmount(price.toString());
                financeReportDO.setCashoutSuccessNumber(number.toString());
                financeReportDO.setCashoutTotalNumber(String.valueOf(payCashoutInfoList.size()));
            }
            else
            {
                financeReportDO.setCashoutAmount("0");
                financeReportDO.setCashoutSuccessNumber("0");
                financeReportDO.setCashoutTotalNumber("0");
            }

            // 兑回请求的统计
            payTransDtlInfoDO.setMsgType(MsgTpEnum.DCEP221.getCode());
            List<PayTransDtlInfoDO> payCashinInfoList = payPayTransdtlMapper.selectPayPayTransdtlList(payTransDtlInfoDO);

            if(payCashinInfoList != null && payCashinInfoList.size() >0)
            {
                BigDecimal price = new BigDecimal("0");
                Integer number = 0;
                for(PayTransDtlInfoDO obj : payCashinInfoList)
                {
                    if(TrxStatusEnum.SUCCESS.getCode().equals(obj.getTrxStatus()))
                    {
                        price = price.add(new BigDecimal(obj.getAmount()));
                        number = number + 1;
                    }
                }

                financeReportDO.setCashinAmount(price.toString());
                financeReportDO.setCashinSuccessNumber(number.toString());
                financeReportDO.setCashinTotalNumber(String.valueOf(payCashinInfoList.size()));
            }
            else
            {
                financeReportDO.setCashinAmount("0");
                financeReportDO.setCashinSuccessNumber("0");
                financeReportDO.setCashinTotalNumber("0");
            }

            // 汇款兑出请求的统计
            payTransDtlInfoDO.setMsgType(MsgTpEnum.DCEP227.getCode());
            List<PayTransDtlInfoDO> payRemitoutInfoList = payPayTransdtlMapper.selectPayPayTransdtlList(payTransDtlInfoDO);

            if(payRemitoutInfoList != null && payRemitoutInfoList.size() >0)
            {
                BigDecimal price = new BigDecimal("0");
                Integer number = 0;
                for(PayTransDtlInfoDO obj : payRemitoutInfoList)
                {
                    if(TrxStatusEnum.SUCCESS.getCode().equals(obj.getTrxStatus()))
                    {
                        price = price.add(new BigDecimal(obj.getAmount()));
                        number = number + 1;
                    }
                }

                financeReportDO.setRemitoutAmount(price.toString());
                financeReportDO.setRemitoutSuccessNumber(number.toString());
                financeReportDO.setRemitoutTotalNumber(String.valueOf(payRemitoutInfoList.size()));
            }
            else
            {
                financeReportDO.setRemitoutAmount("0");
                financeReportDO.setRemitoutSuccessNumber("0");
                financeReportDO.setRemitoutTotalNumber("0");
            }

            financeReportDO.setReportDate(reportDate);
            financeReportDOMapper.insertFinanceReportDO(financeReportDO);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    // 非金融报表统计
    private void nonfinanceReportStatistics(String reportDate)
    {
        try {
            NonfinanceReportDO nonfinanceReportDO = new NonfinanceReportDO();

            PayTransDtlNonfDO payTransDtlNonfDO = new PayTransDtlNonfDO();
            payTransDtlNonfDO.setPayDate(reportDate);

            // 银行账户挂接管理请求的统计
            payTransDtlNonfDO.setPkgNo(MsgTpEnum.DCEP433.getCode());
            List<PayTransDtlNonfDO> payTransDtlNonfList = payPayTransdtlNonfMapper.selectPayPayTransdtlNonfList(payTransDtlNonfDO);

            if(payTransDtlNonfList != null && payTransDtlNonfList.size() >0)
            {
                Integer tiedCardNumber = 0;
                Integer untieNumber = 0;
                for(PayTransDtlNonfDO obj : payTransDtlNonfList)
                {
                    // 绑卡数量
                    if(ManagementTpCdEnum.MT06.getCode().equals(obj.getOpterationType()))
                    {
                        tiedCardNumber = tiedCardNumber + 1;
                    }

                    // 解绑数量
                    if(ManagementTpCdEnum.MT03.getCode().equals(obj.getOpterationType()))
                    {
                        untieNumber = untieNumber + 1;
                    }
                }

                nonfinanceReportDO.setTiedCardNumber(tiedCardNumber.toString());
                nonfinanceReportDO.setUntieNumber(untieNumber.toString());
            }
            else
            {
                nonfinanceReportDO.setTiedCardNumber("0");
                nonfinanceReportDO.setUntieNumber("0");
            }

            nonfinanceReportDO.setReportDate(reportDate);
            nonfinanceReportDOMapper.insertNonfinanceReportDO(nonfinanceReportDO);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
