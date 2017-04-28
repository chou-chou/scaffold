package com.hrp.utils.dataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * DynamicDataSource
 *
 * @author KVLT
 * @date 2017-03-14.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DatabaseContextHolder.getCustomerType() + "DataSource";
    }
}
