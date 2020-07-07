package com.itbooking.core;//package com.itbooking.core;
// 
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//
//import com.itbooking.config.DynamicDataSourceHolder;
// 
///**
// * Created by pure on 2018-05-06.
// */
//public class DynamicDataSource extends AbstractRoutingDataSource {
//    @Override
//    protected Object determineCurrentLookupKey() {
//        System.out.println("数据源为"+DynamicDataSourceHolder.getDataSource());
//        return DynamicDataSourceHolder.getDataSource();
//    }
//}