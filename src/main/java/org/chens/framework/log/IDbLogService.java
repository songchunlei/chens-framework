package org.chens.framework.log;

/**
 * 日志服务
 *
 * @author songchunlei
 * @since 2018/10/6
 */
public interface IDbLogService {

    /**
     * 创建日志
     * 
     * @param dbLog
     * @return
     */
    Boolean create(DbLog dbLog);
}
