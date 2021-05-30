package com.dcits.dcwlt.pay.batch.controller;

import com.dcits.dcwlt.common.core.utils.poi.ExcelUtil;
import com.dcits.dcwlt.common.core.web.controller.BaseController;
import com.dcits.dcwlt.common.core.web.domain.AjaxResult;
import com.dcits.dcwlt.common.core.web.page.TableDataInfo;
import com.dcits.dcwlt.common.log.annotation.Log;
import com.dcits.dcwlt.common.log.enums.BusinessType;
import com.dcits.dcwlt.common.security.annotation.PreAuthorize;
import com.dcits.dcwlt.pay.batch.domain.PaySignSigninfo;
import com.dcits.dcwlt.pay.batch.fegin.PayBatchCheckStatisticsFegin;
import com.dcits.dcwlt.pay.batch.service.IPaySignSigninfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 协议Controller
 *
 * @author dcwlt
 * @date 2021-05-08
 */
@RestController
@RequestMapping("/signinfo")
public class PaySignSigninfoController extends BaseController {
    @Autowired
    private IPaySignSigninfoService paySignSigninfoService;

    @Autowired
    private PayBatchCheckStatisticsFegin payBatchCheckStatisticsFegin;

    /**
     * 查询协议列表
     */
    @PreAuthorize(hasPermi = "system:signinfo:list")
    @GetMapping("/list")
    public TableDataInfo list(PaySignSigninfo paySignSigninfo) {
        startPage();
        List<PaySignSigninfo> list = paySignSigninfoService.selectPaySignSigninfoList(paySignSigninfo);
        return getDataTable(list);
    }

    /**
     * 导出协议列表
     */
    @PreAuthorize(hasPermi = "system:signinfo:export")
    @Log(title = "协议", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PaySignSigninfo paySignSigninfo) throws IOException {
        List<PaySignSigninfo> list = paySignSigninfoService.selectPaySignSigninfoList(paySignSigninfo);
        List<PaySignSigninfo> excels = new ArrayList<>();
        for (int i = 0; list != null && i < list.size(); i++) {
            PaySignSigninfo pay = list.get(i);
            //pay.setSignstatus(DictUtils.getDictCache("sign_status", pay.getSignstatus()));
            excels.add(pay);
        }
        ExcelUtil<PaySignSigninfo> util = new ExcelUtil<PaySignSigninfo>(PaySignSigninfo.class);
        util.exportExcel(response, excels, "signinfo");
    }

    /**
     * 获取协议详细信息
     */
    @PreAuthorize(hasPermi = "system:signinfo:query")
    @GetMapping(value = "/{signno}")
    public AjaxResult getInfo(@PathVariable("signno") String signno) {
        return AjaxResult.success(paySignSigninfoService.selectPaySignSigninfoById(signno));
    }

    /**
     * 新增协议
     */
    @PreAuthorize(hasPermi = "system:signinfo:add")
    @Log(title = "协议", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PaySignSigninfo paySignSigninfo) {
        return toAjax(paySignSigninfoService.insertPaySignSigninfo(paySignSigninfo));
    }

    /**
     * 查询客户信息
     *
     * @param acctid 账号 个人客户信息查询接口请求实体
     * @return 客户信息
     */
    @PreAuthorize(hasPermi = "system:signinfo:queryCustInfo")
    @GetMapping(value = "queryCustInfo/{acctid}")
    public AjaxResult queryCustInfo(@PathVariable("acctid") String acctid) {
//        BankCore20130001Req bankCore20130001Req = new BankCore20130001Req();
//        bankCore20130001Req.setAcctNo(acctid);
//        bankCore20130001Req.setQryTp("1");      //查询类型：1、按账号查
//        bankCore20130001Req.setUsrId("90087");  //柜员号
//        bankCore20130001Req.setTxnVrty("NB1001");     //业务类别
        //return AjaxResult.success(payBatchCheckStatisticsFegin.queryCustInfo(bankCore20130001Req));
        return AjaxResult.success();
    }

    /**
     * 修改协议
     */
    @PreAuthorize(hasPermi = "system:signinfo:edit")
    @Log(title = "协议", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PaySignSigninfo paySignSigninfo) {
        return toAjax(paySignSigninfoService.updatePaySignSigninfo(paySignSigninfo));
    }

    /**
     * 删除协议
     */
    @PreAuthorize(hasPermi = "system:signinfo:remove")
    @Log(title = "协议", businessType = BusinessType.DELETE)
    @DeleteMapping("/{signnos}")
    public AjaxResult remove(@PathVariable String[] signnos) {
        return toAjax(paySignSigninfoService.deletePaySignSigninfoByIds(signnos));
    }
}
