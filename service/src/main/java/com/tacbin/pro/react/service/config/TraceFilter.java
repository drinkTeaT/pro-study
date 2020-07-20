package com.tacbin.pro.react.service.config;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * @author EDZ
 * @description
 * @date 2020/7/16 14:40
 */
@Activate(group = CommonConstants.PROVIDER)
public class TraceFilter implements Filter {
    private static final String UNIQUE_ID = "traceId";

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String traceId = invocation.getAttachment(UNIQUE_ID);
        if (!StringUtils.isBlank(traceId)) {
            MDC.put(UNIQUE_ID, traceId);
        } else {
            UUID uuid = UUID.randomUUID();
            String uniqueId = uuid.toString().replace("-", "");
            MDC.put(UNIQUE_ID, uniqueId);
        }
        Result result = invoker.invoke(invocation);
        MDC.remove(UNIQUE_ID);
        return result;
    }

}