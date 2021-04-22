package com.core.demo.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;


/**
 * 服务层接口
 * 
 * @author yxk
 */
public interface CoreBackService {
    JSONObject replaceAuthInfo(String trId);
}

