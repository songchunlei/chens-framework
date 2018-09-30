package org.chens.framework.urlstat;

import lombok.extern.slf4j.Slf4j;
import org.chens.framework.urlstat.vo.UrlStatBaseTotal;
import org.chens.framework.util.FastJsonUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * 收集器
 */
@Slf4j
public class UrlStatCollector implements InitializingBean {


    private String appName;
    private static String host;

    /**
     * 是否记录
     */
    private boolean isLog = false;




    private static UrlStatAggregator aggregator;

    /**
     * 定时任务-执行周期 目前1分钟执行一次
     */
    private static Long COLLECT_PERIOD = 60 * 1000L;


    static {
        aggregator = new UrlStatAggregator();
        host = getHostName();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        CollectStatsThread collectStatsThread = new CollectStatsThread("urlStatsCollectThread");
        collectStatsThread.start();
    }

    public void onStart(String url, String method) {
        try {
            aggregator.onStart(url, method);
        } catch(Exception ex) {
        }
    }

    public void onFinally() {
        try {
            aggregator.onFinally();
        } catch(Exception ex) {
        }
    }

    public void onThrowable(Throwable t) {
        try {
            aggregator.onThrowable(t);
        } catch (Exception ex) {
        }
    }

    /**
     * 收集线程
     */
    private class CollectStatsThread extends Thread {

        public CollectStatsThread(String name){
            super(name);
            //守护线程
            this.setDaemon(true);
        }

        @Override
        public void run() {
            try {
                for (;;) {
                    try {
                        collect();
                    } catch (Exception e) {
                        log.error("UrlStatCollector:collect url stat error", e);
                    }

                    Thread.sleep(COLLECT_PERIOD);
                }
            } catch (InterruptedException e) {
                // skip
            }
        }
    }


    private void collect() {
        List<UrlStatBaseTotal> statValueList = aggregator.collect();
        if(CollectionUtils.isEmpty(statValueList)) {
            return;
        }
        log.info(FastJsonUtil.toJSONString(new UrlStateMessage(statValueList, appName, host, System.currentTimeMillis())));
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

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
