package com.dcits.dcwlt.pay.batch.controller;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.pay.batch.service.IReportDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by yangjld on 2021/3/12 0012.
 */
@RestController
@RequestMapping("/reportdata")
public class ReportDataController {
    
    @Autowired
    private IReportDataService iReportDataService;

    /**
     * 统计报表数据
     */
    @PostMapping("/statistics")
    public void statistics(@RequestBody String requestJson)
    {
        JSONObject obj = JSONObject.parseObject(requestJson);
        iReportDataService.statistics(obj.getString("reportDate"));
    }

}
