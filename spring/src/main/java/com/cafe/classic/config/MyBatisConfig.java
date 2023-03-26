package com.cafe.classic.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.cafe.classic.Mapper.BbsMapper;
import com.cafe.classic.Mapper.FileMapper;
import com.cafe.classic.Mapper.ReplyMapper;
import com.cafe.classic.Mapper.UserMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
@Configuration
@MapperScan(basePackages= {"com.cafe.classic.Mapper"},
			annotationClass=org.apache.ibatis.annotations.Mapper.class)
@EnableTransactionManagement
public class MyBatisConfig implements TransactionManagementConfigurer{
	@Autowired 
	private Environment env;
	
	private  String driverClassName;
	
	private  String url;
	
	private  String userName;
	
	private  String password;
	@Bean
	public DataSource dataSource() {
		HikariConfig config= new HikariConfig();
		driverClassName=env.getProperty("spring.datasource.driver");
		url=env.getProperty("spring.datasource.url");
		userName=env.getProperty("spring.datasource.username");
		password=env.getProperty("spring.datasource.password");
		config.setDriverClassName(driverClassName);
		config.setJdbcUrl(url);
		config.setUsername(userName);
		config.setPassword(password);
		config.setMaximumPoolSize(10);
		HikariDataSource hikariDataSource= new HikariDataSource(config);
		return hikariDataSource;
	}
	@Bean 
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return transactionManager();
	} 
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		Resource[] res= new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*.xml");
		Resource configurationResource = new PathMatchingResourcePatternResolver().getResource("classpath:MyBatisConfig.xml");
	   SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
	   factoryBean.setDataSource(dataSource());
	   factoryBean.setConfigLocation(configurationResource);
	   factoryBean.setMapperLocations(res);
	   return factoryBean.getObject();
	}
	
}
