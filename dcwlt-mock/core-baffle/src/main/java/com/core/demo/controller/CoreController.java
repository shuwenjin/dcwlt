package com.core.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.core.demo.service.CoreBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 *
 * 
 * @author yxk
 */
@RequestMapping("/core")
@RestController
public class CoreController{

    @Autowired
    private CoreBackService coreBackService;

    /**
     * 模拟核心接口返回不同的json
     * @param
     * @return
     * @throws IOException
     */
    @GetMapping("/sendBack/{trId}")
    public JSONObject result(@PathVariable("trId") String trId)
    {
       return coreBackService.replaceAuthInfo(trId);
    }

}
