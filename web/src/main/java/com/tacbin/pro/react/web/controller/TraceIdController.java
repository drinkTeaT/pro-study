package com.tacbin.pro.react.web.controller;

import com.tacbin.pro.react.api.service.TraceIdService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author EDZ
 * @description
 * @date 2020/7/16 14:33
 */
@RestController
@RequestMapping("/traceId")
@Slf4j
public class TraceIdController {
    @Reference
    private TraceIdService traceIdService;

    @RequestMapping(path = "/demo1", method = RequestMethod.GET)
    public String demo1() {
        log.info("开始调用");
        traceIdService.transferTraceId();
        return "success";
    }
}
