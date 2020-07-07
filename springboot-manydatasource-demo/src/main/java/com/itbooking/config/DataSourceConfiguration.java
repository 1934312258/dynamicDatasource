package com.itbooking.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSourceFactory;

/**
 * 多数据源配置类 Created by pure on 2018-05-06.
 */
@Configuration
//@MapperScan("com.itbooking.mapper")
public class DataSourceConfiguration {

	@Autowired
	Environment env;

	// 数据源1
	@Bean(name = "writeDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.db1") // application.properteis中对应属性的前缀
	public DataSource writeDataSource() throws Exception {
		Properties props = new Properties();
		props.put("driverClassName", env.getProperty("spring.datasource.db1.driverClassName"));
		props.put("url", env.getProperty("spring.datasource.db1.url"));
		props.put("username", env.getProperty("spring.datasource.db1.username"));
		props.put("password", env.getProperty("spring.datasource.db1.password"));
		return DruidDataSourceFactory.createDataSource(props);
	}

	// 数据源2
	@Bean(name = "readDataSource0")
	@ConfigurationProperties(prefix = "spring.datasource.db2") // application.properteis中对应属性的前缀
	public DataSource readDataSource0() throws Exception {
		Properties props = new Properties();
		props.put("driverClassName", env.getProperty("spring.datasource.db2.driverClassName"));
		props.put("url", env.getProperty("spring.datasource.db2.url"));
		props.put("username", env.getProperty("spring.datasource.db2.username"));
		props.put("password", env.getProperty("spring.datasource.db2.password"));
		return DruidDataSourceFactory.createDataSource(props);
	}

	// 数据源2
	@Bean(name = "readDataSource1")
	@ConfigurationProperties(prefix = "spring.datasource.db3") // application.properteis中对应属性的前缀
	public DataSource readDataSource1() throws Exception {
		Properties props = new Properties();
		props.put("driverClassName", env.getProperty("spring.datasource.db3.driverClassName"));
		props.put("url", env.getProperty("spring.datasource.db3.url"));
		props.put("username", env.getProperty("spring.datasource.db3.username"));
		props.put("password", env.getProperty("spring.datasource.db3.password"));
		return DruidDataSourceFactory.createDataSource(props);
	}

	@Bean(name="sqlSessionFactory")
	public SqlSessionFactory getSqlSessionFactoryBean() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		// 设置数据源
		sqlSessionFactoryBean.setDataSource(dynamicDataSource());
		// 设置实体映射
		sqlSessionFactoryBean.setTypeAliasesPackage("com.itbooking.pojo");
		// 配置映射
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mappers/*.xml"));

		//加载插件
		sqlSessionFactoryBean.setPlugins(new Interceptor[] {new com.itbooking.config.DynamicPlugin()});

		return sqlSessionFactoryBean.getObject();
	}

	/**
	 * 动态数据源: 通过AOP在不同数据源之间动态切换
	 *
	 * @return
	 * @throws Exception
	 */
	@Primary
	@Bean(name = "dynamicDataSource")
	public DataSource dynamicDataSource() throws Exception {
		//DynamicDataSource dynamicDataSource = new DynamicDataSource();
		//return dynamicDataSource;
		DynamicDataManySource dynamicDataManySource = new DynamicDataManySource();
		List<Object> datasoures = new ArrayList<>();
		datasoures.add(readDataSource0());
		datasoures.add(readDataSource1());
		dynamicDataManySource.setReadDataSources(datasoures);
		dynamicDataManySource.setWriteDataSource(writeDataSource());
		dynamicDataManySource.setReadDataSourcePollPattern(1);
		return dynamicDataManySource;
	}

	/**
	 * 配置@Transactional注解事物
	 * 
	 * @return
	 * @throws Exception
	 */
	@Bean
	public PlatformTransactionManager transactionManager() throws Exception {
		return new DataSourceTransactionManager(dynamicDataSource());
	}
}