package org.chens.framework.urlstat;

import org.chens.framework.urlstat.vo.UrlStatBase;
import org.chens.framework.urlstat.vo.UrlStatBaseTotal;
import org.chens.framework.urlstat.vo.UrlStatKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 收集汇总
 */
public class UrlStatAggregator {

    private volatile ConcurrentHashMap<UrlStatKey, UrlStatBase> statMap = new ConcurrentHashMap<>();

    /**
     * 请求时间
     */
    private ThreadLocal<Long> timeLocal = new ThreadLocal<>();

    /**
     * key集合
     */
    private ThreadLocal<UrlStatKey> keyLocal = new ThreadLocal<>();

    /**
     * 状态集合
     */
    private ThreadLocal<UrlStatBase> statLocal = new ThreadLocal<>();

    /**
     * 最大集合5000
     */
    private static final int MAX_ENTRY_SIZE = 5000;

    public List<UrlStatBaseTotal> collect() {
        List<UrlStatBaseTotal> statValueList = new ArrayList<>();

        for(Map.Entry<UrlStatKey, UrlStatBase> entry : statMap.entrySet()) {
            UrlStatBaseTotal statValue = entry.getValue().getValue();
            // invokeCount等于0时(未执行完)忽略
            if(statValue == null) {
                continue;
            }

            UrlStatKey key = entry.getKey();
            statValue.setUrl(key.getUrl());
            statValue.setMethod(key.getMethod());
            statValueList.add(statValue);
        }
        return statValueList;
    }

    public void onStart(String url, String method) {
        UrlStatKey urlStatKey = new UrlStatKey(url, method);
        UrlStatBase stat = statMap.get(urlStatKey);
        if(null == stat) {
            stat = new UrlStatBase();

            if(statMap.size() < MAX_ENTRY_SIZE) {
                // 避免并发覆盖
                UrlStatBase oldStat = statMap.putIfAbsent(urlStatKey, stat);
                if (oldStat != null) {
                    stat = oldStat;
                }
            }
        }

        timeLocal.set(System.currentTimeMillis());
        keyLocal.set(urlStatKey);
        statLocal.set(stat);

        stat.onStart();
    }

    public void onFinally() {
        UrlStatBase stat = statLocal.get();
        if(stat == null) {
            return;
        }
        stat.onFinally(timeLocal.get());

        clearThreadLocal();
    }

    public void onThrowable(Throwable t) {
        UrlStatBase stat = statLocal.get();
        if(stat == null) {
            return;
        }
        stat.onThrowable(t);

        clearThreadLocal();
    }

    private void clearThreadLocal() {
        keyLocal.remove();
        timeLocal.remove();
        statLocal.remove();
    }

}
