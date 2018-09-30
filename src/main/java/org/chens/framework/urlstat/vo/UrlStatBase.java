package org.chens.framework.urlstat.vo;

import org.chens.core.util.ConcurrentUtil;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * url基础
 */
public class UrlStatBase {
    /**
     * 调用次数
     */
    public final AtomicInteger invokeCount = new AtomicInteger(0);

    /**
     * 当前正在跑的线程数
     */
    public final AtomicInteger runningCount = new AtomicInteger(0);

    /**
     * 调用总时间
     */
    public final AtomicLong millsTotal = new AtomicLong(0);

    /**
     * 错误次数
     */
    public final AtomicInteger errorCount = new AtomicInteger(0);

    /**
     * 最慢调用
     */
    public final AtomicLong maxTime = new AtomicLong(0);

    /**
     * 错误堆栈
     */
    public final AtomicReference<String> lastError = new AtomicReference<String>();

    /**
     * 最大并发
     */
    public final AtomicInteger concurrentMax = new AtomicInteger(0);

    /**
     * 方法开始前调用
     */
    public void onStart() {
        int ic = runningCount.incrementAndGet();
        ConcurrentUtil.setMaxValue(concurrentMax, ic);
    }

    /**
     * 抛出异常调用
     * @param t
     */
    public void onThrowable(Throwable t){
        errorCount.incrementAndGet();
    }

    /**
     * 结束时调用
     * @param start
     * @return
     */
    public long onFinally(long start){
        long end=System.currentTimeMillis();
        invokeCount.incrementAndGet();
        runningCount.decrementAndGet();
        long used=end-start;
        millsTotal.addAndGet(used);
        ConcurrentUtil.setMaxValue(maxTime, used);
        return end;

    }

    /**
     * 汇总线程调用，如果返回null，那么代表方法没有调用
     * @return
     */
    public UrlStatBaseTotal getValue(){
        int ic=invokeCount.getAndSet(0);
        if(ic>0){
            UrlStatBaseTotal v=new UrlStatBaseTotal();
            //running count 不清0
            v.setRunningCount(runningCount.get());
            v.setInvokeCount(ic);
            v.setTotalTime(millsTotal.getAndSet(0));
            v.setErrorCount(errorCount.getAndSet(0));
            v.setConcurrentMax(concurrentMax.getAndSet(0));
            v.setMaxTime((int)(maxTime.getAndSet(0)));
            v.setLastError(lastError.getAndSet(null));
            return v;
        }
        return null;


    }

}
