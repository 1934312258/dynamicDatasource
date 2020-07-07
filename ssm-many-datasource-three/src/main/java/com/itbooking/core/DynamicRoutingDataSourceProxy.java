package com.itbooking.core;

import javax.sql.DataSource;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by IDEA
 * User: 徐柯老师
 * Date: 2018-07-19 16:04
 * Desc:
 */
public class DynamicRoutingDataSourceProxy extends AbstractDynamicDataSourceProxy {

    private AtomicLong counter = new AtomicLong(0);

    private static final Long MAX_POOL = Long.MAX_VALUE;

    private final Lock lock = new ReentrantLock();

    @Override
    protected DataSource loadReadDataSource() {
        int index = 1;

        if(getReadDataSourcePollPattern() == 1) {
            //轮询方式 读库的轮询操作 采用hash取余的算法来解决
            long currValue = counter.incrementAndGet();
            // 如果当前读库的值大于最大的调用次数
            if((currValue + 1) >= MAX_POOL) {
                try {
                    // 开始进行加锁
                    lock.lock();
                    if((currValue + 1) >= MAX_POOL) {
                        // 计算器归0，重新开始获取读的数据源对象
                        counter.set(0);
                    }
                } finally {
                    // 释放锁
                    lock.unlock();
                }
            }
            // 获取读取的长度和当前的计数器，准备去进入什么样子的读数据源
            index = (int) (currValue % getReadDsSize());
        } else {
            //随机方式
            index = ThreadLocalRandom.current().nextInt(0, getReadDsSize());
        }

        return getResolvedReadDataSources().get(index);
    }
}