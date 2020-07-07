package com.itbooking.config;
 
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
 
/**
 * Created by pure on 2018-05-06.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
	
	
	@Autowired
	@Qualifier("writeDataSource")
	private Object writeDataSource; //写数据源
	@Qualifier("readDataSource")
	private Object readDataSource; //读数据源
	
	@Override
    public void afterPropertiesSet() {
        if (this.writeDataSource == null) {
            throw new IllegalArgumentException("Property 'writeDataSource' is required");
        }
        setDefaultTargetDataSource(writeDataSource);
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DynamicDataSourceGlobal.WRITE.name(), writeDataSource);
        if(readDataSource != null) {
            targetDataSources.put(DynamicDataSourceGlobal.READ.name(), readDataSource);
        }
        setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

	
    @Override
    protected Object determineCurrentLookupKey() {
    	DynamicDataSourceGlobal dynamicDataSourceGlobal = DynamicDataSourceHolder.getDataSource();
        if(dynamicDataSourceGlobal == null || dynamicDataSourceGlobal == DynamicDataSourceGlobal.WRITE) {
        	System.out.println("数据源为"+dynamicDataSourceGlobal.WRITE.name());
            return DynamicDataSourceGlobal.WRITE.name();
        }

        System.out.println("数据源为"+dynamicDataSourceGlobal.READ.name());
        return DynamicDataSourceGlobal.READ.name();
    }
}