package com.itbooking.core;
//package com.itbooking.config;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//
//import javax.sql.DataSource;
//
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.env.Environment;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import com.alibaba.druid.pool.DruidDataSourceFactory;
//
///**
// * 多数据源配置类 Created by pure on 2018-05-06.
// */
//@Configuration
//@MapperScan("com.itbooking.mapper")
//public class DataSourceConfig {
//	
//	@Autowired
//	Environment  env;
//	
//	
//	// 数据源1
//	@Bean(name = "datasource1")
//	@ConfigurationProperties(prefix = "spring.datasource.db1") // application.properteis中对应属性的前缀
//	public DataSource dataSource1() throws Exception {
//		Properties props = new Properties();
//		props.put("driverClassName", "spring.datasource.db1.driverClassName");
//		props.put("url", env.getProperty("spring.datasource.db1.url"));
//		props.put("username", env.getProperty("spring.datasource.db1.username"));
//		props.put("password", env.getProperty("spring.datasource.db1.password"));
//		return DruidDataSourceFactory.createDataSource(props);
//	}
//
//	// 数据源2
//	@Bean(name = "datasource2")
//	@ConfigurationProperties(prefix = "spring.datasource.db2") // application.properteis中对应属性的前缀
//	public DataSource dataSource2() throws Exception  {
//		Properties props = new Properties();
//		props.put("driverClassName", env.getProperty("spring.datasource.db2.driverClassName"));
//		props.put("url", env.getProperty("spring.datasource.db2.url"));
//		props.put("username", env.getProperty("spring.datasource.db2.username"));
//		props.put("password", env.getProperty("spring.datasource.db2.password"));
//		return DruidDataSourceFactory.createDataSource(props);
//	}
//
//	/**
//	 * 动态数据源: 通过AOP在不同数据源之间动态切换
//	 * 
//	 * @return
//	 * @throws Exception 
//	 */
//	@Primary
//	@Bean(name = "dynamicDataSource")
//	public DataSource dynamicDataSource() throws Exception {
//		DynamicDataSource dynamicDataSource = new DynamicDataSource();
//		// 默认数据源
//		dynamicDataSource.setDefaultTargetDataSource(dataSource1());
//		// 配置多数据源
//		Map<Object, Object> dsMap = new HashMap<>();
//		dsMap.put("datasource1", dataSource1());
//		dsMap.put("datasource2", dataSource2());
//		dynamicDataSource.setTargetDataSources(dsMap);
//		return dynamicDataSource;
//	}
//
//	/**
//	 * 配置@Transactional注解事物
//	 * 
//	 * @return
//	 * @throws Exception 
//	 */
//	@Bean
//	public PlatformTransactionManager transactionManager() throws Exception {
//		return new DataSourceTransactionManager(dynamicDataSource());
//	}
//}