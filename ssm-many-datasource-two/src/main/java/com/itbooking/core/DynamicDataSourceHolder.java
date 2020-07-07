
package com.itbooking.core;

/**
 * Created by IDEA
 * 本地线程设置和获取数据源信息
 * User: 徐柯老师
 * Date: 2018-07-07 13:35
 * Desc:
 */
public class DynamicDataSourceHolder {

    private static final ThreadLocal<DynamicDataSourceGlobal> holder = new ThreadLocal<DynamicDataSourceGlobal>();

    public static void putDataSource(DynamicDataSourceGlobal dataSource){
        holder.set(dataSource);
    }

    public static DynamicDataSourceGlobal getDataSource(){
        return holder.get();
    }

    public static void clearDataSource() {
        holder.remove();
    }

}