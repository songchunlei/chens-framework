package org.chens.framework.log;

import lombok.Data;

import java.io.Serializable;

/**
 * 日志
 *
 * @author songchunlei
 * @since 2018/10/6
 */
@Data
public class DbLog implements Serializable{
    /**
     * 操作类型
     */
    private String opt;
    /**
     * 请求人地址
     */
    private String host;
    /**
     * 请求串
     */
    private String url;
}
