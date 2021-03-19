package com.dcits.dcwlt.pay.batch.controller;

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
    @ExceptionHandler(value = Exception.class)
    @PostMapping("/statistics")
    public void statistics(@RequestParam("reportDate") String reportDate) throws Exception
    {
        try {
            iReportDataService.statistics(reportDate);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
