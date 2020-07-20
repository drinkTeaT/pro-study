package com.tacbin.pro.react.web.log;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.*;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * @author EDZ
 * @description
 * @date 2020/7/16 14:40
 */
@Activate(group = CommonConstants.CONSUMER)
public class TraceFilter implements Filter {
    private static final String UNIQUE_ID = "traceId";

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String traceId = MDC.get(UNIQUE_ID);
        if (!StringUtils.isBlank(traceId)) {
            // do nothing
        } else {
            UUID uuid = UUID.randomUUID();
            String uniqueId = uuid.toString().replace("-", "");
            traceId = uniqueId;
            MDC.put(UNIQUE_ID, uniqueId);
        }
        invocation.setAttachment(UNIQUE_ID, traceId);
        Result result = invoker.invoke(invocation);
        MDC.remove(UNIQUE_ID);
        return result;
    }

}