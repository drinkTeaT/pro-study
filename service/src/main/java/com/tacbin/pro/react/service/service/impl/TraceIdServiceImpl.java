package com.tacbin.pro.react.service.service.impl;

import com.tacbin.pro.react.api.service.TraceIdService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author EDZ
 * @description
 * @date 2020/7/16 14:27
 */
@Service
@Slf4j
public class TraceIdServiceImpl implements TraceIdService {
    @Override
    public void transferTraceId() {
        log.info("被调用了");
    }
}
