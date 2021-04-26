package com.dcits.dcwlt.pay.online.controller.front;

import com.dcits.dcwlt.common.core.web.controller.BaseController;
import com.dcits.dcwlt.common.core.web.page.TableDataInfo;
import com.dcits.dcwlt.common.security.annotation.PreAuthorize;
import com.dcits.dcwlt.pay.api.model.MonitorDO;
import com.dcits.dcwlt.pay.online.service.IMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 异常监控
 */
@RestController
@RequestMapping("/monitor")
public class ExMonitorController extends BaseController {

    @Autowired
    private IMonitorService monitorService;

    @PreAuthorize(hasPermi = "monitor:exmonitor:list")
    @PostMapping(value = "/exmonitor/list")
    public TableDataInfo queryExMonitorList(@RequestBody MonitorDO monitorDO) {
        startPage();
        List<MonitorDO> list = monitorService.queryMonitorList(monitorDO);

        return getDataTable(list);
    }


}
