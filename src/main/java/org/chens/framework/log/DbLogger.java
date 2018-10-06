package org.chens.framework.log;


import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * 保存系统日志工具
 * 参考自 https://gitee.com/geek_qi/ace-security
 */
@Slf4j
public class DbLogger extends Thread {

    private static DbLogger dblogger = null;
    private static BlockingQueue<DbLog> logInfoQueue = new LinkedBlockingQueue<DbLog>(1024);
    private IDbLogService dbLogService;

    public IDbLogService getDbLogService() {
        return dbLogService;
    }

    public DbLogger setDbLogService(IDbLogService dbLogService) {
        if(this.dbLogService==null){
            this.dbLogService = dbLogService;
        }
        return this;
    }

    public static synchronized DbLogger getInstance() {
        if (dblogger == null) {
            dblogger = new DbLogger();
        }
        return dblogger;
    }

    public static synchronized DbLogger getInstance(IDbLogService dbLogService) {
        if (dblogger == null) {
            dblogger = new DbLogger(dbLogService);
        }
        return dblogger;
    }

    private DbLogger() {
        super("CLogWriterThread");
    }

    private DbLogger(IDbLogService dbLogService) {
        super("CLogWriterThread");
        this.dbLogService = dbLogService;
    }

    public void offerQueue(DbLog dbLog) {
        try {
            logInfoQueue.offer(dbLog);
        } catch (Exception e) {
            log.error("日志写入失败", e);
        }
    }

    @Override
    public void run() {
        // 缓冲队列
        List<DbLog> bufferedLogList = Lists.newArrayList();
        while (true) {
            try {
                bufferedLogList.add(logInfoQueue.take());
                logInfoQueue.drainTo(bufferedLogList);
                if (bufferedLogList != null && bufferedLogList.size() > 0) {
                    // 写入日志
                    for(DbLog log:bufferedLogList){
                        dbLogService.create(log);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                // 防止缓冲队列填充数据出现异常时不断刷屏
                try {
                    Thread.sleep(1000);
                } catch (Exception eee) {
                }
            } finally {
                if (bufferedLogList != null && bufferedLogList.size() > 0) {
                    try {
                        bufferedLogList.clear();
                    } catch (Exception e) {
                    }
                }
            }
        }
    }
}