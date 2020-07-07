package com.itbooking.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by pure on 2018-05-06.
 */
public class DynamicDataManySource extends AbstractRoutingDataSource {
	
	
	private Object writeDataSource; //写数据源
    private List<Object> readDataSources; //读数据源

    private int readDataSourceSize; //读数据源个数
    private int readDataSourcePollPattern = 0; //获取读数据源方式，0：随机，1：轮询


    private AtomicLong counter = new AtomicLong(0); // 计数器
    private static final Long MAX_POOL = Long.MAX_VALUE; // 累加的最大边界
    private final Lock lock = new ReentrantLock(); // 锁，用来切换当计数器超过MAX_POOL的时候，重置使用。

	@Override
    public void afterPropertiesSet() {
        if (this.writeDataSource == null) {
            throw new IllegalArgumentException("Property 'writeDataSource' is required");
        }
        // 设置默认数据源
        setDefaultTargetDataSource(writeDataSource);
        // 定义数据源注册的容器Map
        Map<Object, Object> targetDataSources = new HashMap<>();
        // 注册写库数据源
        targetDataSources.put(DynamicDataSourceGlobal.WRITE.name(), writeDataSource);
        // 判断如果读库如果没有
        if (this.readDataSources == null) {
            readDataSourceSize = 0;
        } else {
            // 如果注册了读库，开始进行读库注册，根据索引进行轮询或者随机切换
            for(int i=0; i<readDataSources.size(); i++) {
                targetDataSources.put(DynamicDataSourceGlobal.READ.name() + i, readDataSources.get(i));
            }
            // 获取到节点的数量
            readDataSourceSize = readDataSources.size();
        }

        // 注册所有的目标数据源
        setTargetDataSources(targetDataSources);
        // 调用父类方法，统一注册管理
        super.afterPropertiesSet();
    }

	
    @Override
    protected Object determineCurrentLookupKey() {
    	DynamicDataSourceGlobal dynamicDataSourceGlobal = DynamicDataSourceHolder.getDataSource();
        // 如果没有选择数据源，或者你选择的writer或者，没有写库都用write数据源返回
    	if(dynamicDataSourceGlobal == null || dynamicDataSourceGlobal == DynamicDataSourceGlobal.WRITE
        || readDataSources==null || readDataSources.size() <=0) {
        	System.out.println("数据源为"+dynamicDataSourceGlobal.WRITE.name());
            return DynamicDataSourceGlobal.WRITE.name();
        }

        // 否则开始切换读库，采用轮询或者随机的策略
        int index = 0;
    	if(readDataSourcePollPattern==0){
            //轮询方式
            long currValue = counter.incrementAndGet();
            // 如果达到了最大的值，开始进行归0 重新开始计算
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
            index = (int)(currValue % readDataSourceSize);
        }else{
    	    // 随机给予长度范围内的数字，切换服务器
            index = ThreadLocalRandom.current().nextInt(0,readDataSourceSize);
        }

        System.out.println("数据源为"+dynamicDataSourceGlobal.READ.name()+index);
        return DynamicDataSourceGlobal.READ.name() + index;
    }


    public Object getWriteDataSource() {
        return writeDataSource;
    }

    public void setWriteDataSource(Object writeDataSource) {
        this.writeDataSource = writeDataSource;
    }

    public List<Object> getReadDataSources() {
        return readDataSources;
    }

    public void setReadDataSources(List<Object> readDataSources) {
        this.readDataSources = readDataSources;
    }

    public int getReadDataSourcePollPattern() {
        return readDataSourcePollPattern;
    }

    public void setReadDataSourcePollPattern(int readDataSourcePollPattern) {
        this.readDataSourcePollPattern = readDataSourcePollPattern;
    }
}