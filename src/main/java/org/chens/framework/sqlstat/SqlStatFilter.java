package org.chens.framework.sqlstat;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.proxy.jdbc.DataSourceProxy;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import com.alibaba.druid.stat.JdbcSqlStat;

/**
 * 过滤器?
 */
public class SqlStatFilter extends StatFilter{
    @Override
    public JdbcSqlStat createSqlStat(StatementProxy statement, String sql) {
        JdbcSqlStat jdbcSqlStat = super.createSqlStat(statement, sql);
        DataSourceProxy dataSourceProxy = statement.getConnectionProxy().getDirectDataSource();
        return super.createSqlStat(statement, sql);
    }
}
