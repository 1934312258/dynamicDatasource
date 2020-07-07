package com.itbooking.core;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by IDEA
 * User: 徐柯老师
 * Date: 2018-07-14 10:56
 * Desc: 动态数据源实现读写分离
 */

public class DynamicDataSource extends AbstractRoutingDataSource {

    private Object writeDataSource; //写数据源 --1主多从
    private List<Object> readDataSources; //多个读数据源，多个

    private int readDataSourceSize; //读数据源个数
    private int readDataSourcePollPattern =1; //获取读数据源方式，0：随机，1：轮询
    private AtomicLong counter = new AtomicLong(0);
    private static final Long MAX_POOL = Long.MAX_VALUE;
    private final Lock lock = new ReentrantLock();

    @Override
    public void afterPropertiesSet() {
        // 1 :如果写数据源，直接返回
        if (this.writeDataSource == null) {
            throw new IllegalArgumentException("Property 'writeDataSource' is required");
        }
        // 2: 设置写库的数据源默认数据源
        setDefaultTargetDataSource(writeDataSource);

        // 定义map,收集所有的数据源（readDataSource,readDataSource2,readDataSource3,wirterDataSource）---
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DynamicDataSourceGlobal.WRITE.name(), writeDataSource);
        if (this.readDataSources == null) {
            readDataSourceSize = 0;
        } else {
            // 如果你配置读数据源
            for(int i=0; i<readDataSources.size(); i++) {
                //  放入到map中
                targetDataSources.put(DynamicDataSourceGlobal.READ.name() + i, readDataSources.get(i));
            }
            // 返回读库的数量
            readDataSourceSize = readDataSources.size();
        }

        // 所有的数据源进行了注册
        setTargetDataSources(targetDataSources);

        // 调用父类的处理数据源选择的方法
        super.afterPropertiesSet();
    }

    // determineCurrentLookupKey 在所有前置通知执行之后，才执行后置通知
    @Override
    protected Object determineCurrentLookupKey() {

        DynamicDataSourceGlobal dynamicDataSourceGlobal = DynamicDataSourceHolder.getDataSource();

        if(dynamicDataSourceGlobal == null
                || dynamicDataSourceGlobal == DynamicDataSourceGlobal.WRITE
                || readDataSourceSize <= 0) {
            return DynamicDataSourceGlobal.WRITE.name();
        }

        int index = 1;

        if(readDataSourcePollPattern == 1) {
            //轮询方式 ---currValue--Long
            long currValue = counter.incrementAndGet();
            //  因为你的计数器会达到一个最大值，如果大于最大值，那么久溢出，重新归0 重新计算
            if((currValue + 1) >= MAX_POOL) {
                try {
                    lock.lock();
                    if((currValue + 1) >= MAX_POOL) {
                        counter.set(0);
                    }
                } finally {
                    lock.unlock();
                }
            }
            index = (int) (currValue % readDataSourceSize); // 轮询
        } else if(readDataSourcePollPattern==2){
            // 写权重的算法 weight
        }else {
            //随机方式
            index = ThreadLocalRandom.current().nextInt(0, readDataSourceSize);
        }
        return dynamicDataSourceGlobal.name() + index;
    }

    public void setWriteDataSource(Object writeDataSource) {
        this.writeDataSource = writeDataSource;
    }

    public void setReadDataSources(List<Object> readDataSources) {
        this.readDataSources = readDataSources;
    }

    public void setReadDataSourcePollPattern(int readDataSourcePollPattern) {
        this.readDataSourcePollPattern = readDataSourcePollPattern;
    }
}