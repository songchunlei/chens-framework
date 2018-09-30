package org.chens.framework.sqlstat;

import com.alibaba.druid.stat.JdbcSqlStatValue;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * sql监控日志
 */
@Data
public class SqlStatMessage implements Serializable {
    private static final long serialVersionUID = -90909613641688569L;

    /**
     * sql日志
     */
    private List<JdbcSqlStatValue> statList;

    /**
     * host
     */
    private String host;

    /**
     * app名称
     */
    private String appName;

    /**
     * 记录日期
     */
    private long timeStamp;

    public SqlStatMessage() {
    }

    public SqlStatMessage(List<JdbcSqlStatValue> statList, String host, String appName, long timeStamp) {
        this.statList = statList;
        this.host = host;
        this.appName = appName;
        this.timeStamp = timeStamp;
    }
}
