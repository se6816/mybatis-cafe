package com.test.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.test.Mapper.UserMapper;
import com.test.Mapper.BbsMapper;
import com.test.Mapper.FileMapper;
import com.test.Mapper.ReplyMapper;
@Configuration
@MapperScan("com.test.Mapper")
@EnableTransactionManagement
public class MyBatisConfig implements TransactionManagementConfigurer{
	@Autowired 
	Environment env;
	
	private  String driverClassName;
	
	private  String url;
	
	private  String userName;
	
	private  String password;
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource= new BasicDataSource();
		driverClassName=env.getProperty("spring.datasource.logdriver");
		url=env.getProperty("spring.datasource.logurl");
		userName=env.getProperty("spring.datasource.username");
		password=env.getProperty("spring.datasource.password");
		
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		return dataSource;
	}
	@Bean public PlatformTransactionManager transactionManager() {
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
	@Bean 
	public MapperFactoryBean <UserMapper> UserMapper () throws Exception { 
		MapperFactoryBean <UserMapper> factoryBean = new MapperFactoryBean<>(UserMapper.class); 
		factoryBean.setSqlSessionFactory(sqlSessionFactory());
        return factoryBean ;
    } 
	@Bean 
	public MapperFactoryBean <ReplyMapper> ReplyMapper () throws Exception { 
		MapperFactoryBean <ReplyMapper> factoryBean = new MapperFactoryBean<>(ReplyMapper.class); 
		factoryBean.setSqlSessionFactory(sqlSessionFactory());
        return factoryBean ;
    } 
	
	@Bean 
	public MapperFactoryBean <BbsMapper> BbsMapper () throws Exception { 
		MapperFactoryBean <BbsMapper> factoryBean = new MapperFactoryBean<>(BbsMapper.class); 
		factoryBean.setSqlSessionFactory(sqlSessionFactory());
        return factoryBean ;
    } 
	@Bean 
	public MapperFactoryBean<FileMapper> FileMapper () throws Exception { 
		MapperFactoryBean <FileMapper> factoryBean =  new MapperFactoryBean<>(FileMapper.class); 
		factoryBean.setSqlSessionFactory(sqlSessionFactory());
        return factoryBean ;
    }
	
	
}
