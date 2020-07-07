
package com.itbooking.core;

import java.sql.Connection;

/**
 * @Author 徐柯
 * @Tel/微信：15074816437
 * @Description  创建Connection代理接口
 * @Date 13:02 2019/3/20
 * @Param 
 * @return 
 **/
public interface ConnectionProxy extends Connection {

    /**
     * 根据传入的读写分离需要的key路由到正确的connection
     * @param key 数据源标识
     * @return
     */
    Connection  getTargetConnection(String key);
}