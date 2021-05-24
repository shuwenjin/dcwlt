package com.dcits.dcwlt.pay.batch.controller;

import com.dcits.dcwlt.common.core.web.controller.BaseController;
import com.dcits.dcwlt.common.core.web.page.TableDataInfo;
import com.dcits.dcwlt.common.security.annotation.PreAuthorize;
import com.dcits.dcwlt.pay.api.model.SignInfoDO;
import com.dcits.dcwlt.pay.api.model.SignJrnDO;

import com.dcits.dcwlt.pay.batch.service.ISignInfoService;
import com.dcits.dcwlt.pay.batch.service.ISignJrnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
    供管理控制台获取数据
    pay_sign_signinfo;签约协议表
    pay_sign_signinfo_jrn;签约流水表
 */
@RestController
@RequestMapping("/sign")
public class SignInfoAndJrnController  extends BaseController {

    @Autowired
    private ISignInfoService signInfoService;

    @Autowired
    private ISignJrnService signJrnService;

    @PreAuthorize(hasPermi = "system:signinfo:query")
    @PostMapping(value = "/signinfo/list")
    public TableDataInfo selectPartSignInfo(@RequestBody SignInfoDO signInfoDO){
        startPage();
        List<SignInfoDO> list = signInfoService.selectPartSignInfo(signInfoDO);
        return getDataTable(list);
    }

    @PreAuthorize(hasPermi = "system:signjrn:query")
    @PostMapping(value = "/signjrn/list")
    public TableDataInfo selectPartSignInfo(@RequestBody SignJrnDO signJrnDO){
        startPage();
        List<SignJrnDO> list = signJrnService.selectPartSignJrn(signJrnDO);
        return getDataTable(list);
    }

}
