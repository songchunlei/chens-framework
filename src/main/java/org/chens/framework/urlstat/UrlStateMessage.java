package org.chens.framework.urlstat;

import lombok.Data;
import org.chens.framework.urlstat.vo.UrlStatBaseTotal;

import java.util.List;

/**
 * Url监控消息
 *
 * @author songchunlei
 * @since 2018/9/17
 */
@Data
public class UrlStateMessage {

    private List<UrlStatBaseTotal> statList;

    private String host;

    private String appName;

    private long timeStamp;

    public UrlStateMessage(List<UrlStatBaseTotal> statList, String host, String appName, long timeStamp) {
        this.statList = statList;
        this.host = host;
        this.appName = appName;
        this.timeStamp = timeStamp;
    }

    public UrlStateMessage() {
    }
}
