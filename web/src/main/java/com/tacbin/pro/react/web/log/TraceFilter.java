package com.tacbin.pro.react.web.log;

import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.rpc.*;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author EDZ
 * @description
 * @date 2020/7/16 14:40
 */
@Service
public class TraceFilter implements Filter {
    private static final String UNIQUE_ID = "traceId";

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String traceId = RpcContext.getContext().getAttachment("traceId");
        if (!StringUtils.isBlank(traceId)) {
            MDC.put(UNIQUE_ID, traceId);
        } else {
            UUID uuid = UUID.randomUUID();
            String uniqueId = uuid.toString().replace("-", "");
            MDC.put(UNIQUE_ID, uniqueId);
            RpcContext.getContext().setAttachment("traceId", traceId);
        }
        Result result = invoker.invoke(invocation);
        MDC.remove(UNIQUE_ID);
        return result;
    }

}