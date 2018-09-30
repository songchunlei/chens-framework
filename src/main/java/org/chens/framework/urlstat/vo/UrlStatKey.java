package org.chens.framework.urlstat.vo;

import lombok.Data;

/**
 * url-key 记录
 */
@Data
public class UrlStatKey {
    private String url;

    private String method;

    public UrlStatKey(String url, String method) {
        this.url = url;
        this.method = method;
    }

    public UrlStatKey() {
    }
}
