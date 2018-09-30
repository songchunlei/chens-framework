package org.chens.framework.trace.vo;

import org.slf4j.MDC;
import org.springframework.util.StringUtils;

/**
 * 日志内容
 *
 * @author songchunlei
 * @since 2018/9/17
 */
public class LogContext {
    public static final String MDC_TRACE_ID = "traceId";
    public static final String MDC_LOG_TRACE_ID = "logTraceId";

    private LogContext() {
    }

    public static String generateLogTraceId() {
        return ObjectId.get().toString();
    }

    public static String getLogTraceId() {
        return MDC.get(MDC_LOG_TRACE_ID);
    }

    public static void set(String traceId, String logTraceId) {
        if (StringUtils.isEmpty(traceId)) {
            MDC.put(MDC_TRACE_ID, traceId);
        }
        if (StringUtils.isEmpty(logTraceId)) {
            MDC.put(MDC_LOG_TRACE_ID, logTraceId);
        }
    }

    public static void remove() {
        //TraceClient.removeLogTraceId();
        MDC.remove(MDC_TRACE_ID);
        MDC.remove(MDC_LOG_TRACE_ID);
    }
}
