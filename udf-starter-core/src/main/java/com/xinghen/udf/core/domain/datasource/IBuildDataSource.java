package com.xinghen.udf.core.domain.datasource;

import javax.sql.DataSource;

/**
 * 构建数据源接口
 */
public interface IBuildDataSource {

    /**
     * 构建数据源
     *
     * @param dataSourceMeta
     * @return
     */
    DataSource build(DataSourceMeta dataSourceMeta);

}
