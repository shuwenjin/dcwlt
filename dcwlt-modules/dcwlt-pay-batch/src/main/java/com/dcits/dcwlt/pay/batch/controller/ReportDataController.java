package com.dcits.dcwlt.pay.batch.controller;

import com.dcits.dcwlt.pay.batch.service.IReportDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public void statistics(@RequestParam String reportDate)
    {
        iReportDataService.statistics(reportDate);
    }

}
