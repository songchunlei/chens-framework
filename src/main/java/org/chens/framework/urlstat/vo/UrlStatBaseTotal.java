package org.chens.framework.urlstat.vo;

import lombok.Data;

/**
 * 汇总
 *
 * @author songchunlei
 * @since 2018/9/17
 */
@Data
public class UrlStatBaseTotal {
    private String url;

    private String method;

    private int runningCount;

    private int invokeCount;
    private long totalTime;
    private int errorCount;
    private int concurrentMax;
    private int maxTime;
    private String lastError;
}
