package org.chens.framework.sqlstat;

import com.alibaba.druid.pool.DruidDataSourceStatLogger;
import com.alibaba.druid.pool.DruidDataSourceStatValue;
import com.alibaba.druid.stat.JdbcSqlStatValue;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Properties;

/**
 * 借Druid对sql日志记录
 */
@Slf4j
public class SqlStatLogger implements DruidDataSourceStatLogger,InitializingBean{
    /**
     * host
     */
    private static final String HOST;
    private static final int SQLSTAT_PAGE_SIZE = 50;

    /**
     * app名称
     */
    private String appName;

    /**
     * 环境
     */
    private String env;

    /**
     * 是否记录
     */
    private boolean isLog = false;
    /**
     * 记录日志的时间戳
     */
    private Long logTimeStamp;

    static {
        HOST = getHostName();
    }

    private static String getHostName() {
        InetAddress address;

        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException var4) {
            var4.printStackTrace();
            return null;
        }
        return address.getHostName();
    }

    @Override
    public void log(DruidDataSourceStatValue statValue) {
        //判断是否记录
        if(!isLog){
            return;
        }
        //开始时间
        Long startLogTime = System.currentTimeMillis();
        //sql脚本
        List<JdbcSqlStatValue> statValueList = statValue.getSqlList();
        if (CollectionUtils.isEmpty(statValueList)) {
            return;
        }
        //性能考虑，分页记录
        int index = 0;
        int pageNum = (statValueList.size() - 1) / SQLSTAT_PAGE_SIZE + 1;
        while (index < pageNum) {
            int startPos = SQLSTAT_PAGE_SIZE * index;
            int endPos = Math.min(startPos + SQLSTAT_PAGE_SIZE, statValueList.size());
            //TODO 这里可以接入专门的日志收集工具，比如Kafka
            log.info(JSON.toJSONString(new SqlStatMessage(statValue.getSqlList().subList(startPos, endPos), appName, HOST, logTimeStamp)));
            log.info("statLogger log finished, time elapsed {} ms", System.currentTimeMillis() - startLogTime);
            index++;
        }

    }

    @Override
    public void configFromProperties(Properties properties) {

    }

    @Override
    public void setLogger(Log logger) {

    }

    @Override
    public void setLoggerName(String loggerName) {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //TODO 这里可以初始化一些参数
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public void setLog(boolean log) {
        isLog = log;
    }
}
