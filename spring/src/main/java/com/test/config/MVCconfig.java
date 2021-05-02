package com.test.config;


import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import com.test.Utils.AES256Util;
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.test"})
@PropertySource("classpath:application.properties")
public class MVCconfig extends WebMvcConfigurerAdapter{
	@Autowired 
	Environment env;
	private final int MAX_SIZE = 10* 1024*1024;
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.//addMapping("/user/change/**")
			addMapping("*")
			.allowedOrigins("*")
			.allowedHeaders("*")
			.allowCredentials(true)
			.allowedMethods("*");
			
		registry.addMapping("/api/**")
			.allowedOrigins("http://localhost:8801/spring/")
			.allowedMethods("*");
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String uploadPath=env.getProperty("uploadFile.path.handler");
		String resourcePath=env.getProperty("uploadFile.resourcePath.handler");
		 registry.addResourceHandler("/css/**").addResourceLocations("/static/css/").setCachePeriod(0);
	     registry.addResourceHandler("/img/**").addResourceLocations("/static/img/").setCachePeriod(0);
	     registry.addResourceHandler("/js/**").addResourceLocations("/static/js/").setCachePeriod(0);
	     registry.addResourceHandler(resourcePath+"**").addResourceLocations("file:///"+uploadPath).setCachePeriod(0);
	 		
	}
	@Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
	}
	@Override
    public void addViewControllers(final ViewControllerRegistry registry) {
    		System.out.println("addViewControllers가 호출됩니다. ");
        registry.addViewController("/").setViewName("home");
	}
	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver() {
		InternalResourceViewResolver resolver= new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	@Bean(name="multipartResolver")
	public CommonsMultipartResolver commonsMultipartResolver() {
		CommonsMultipartResolver MultipartResolver = new CommonsMultipartResolver();
		MultipartResolver.setMaxUploadSize(MAX_SIZE);
		return MultipartResolver;
	}
	@Bean
	public AES256Util AES256Util() throws UnsupportedEncodingException {
		String key="aes256realusedtempkey";
		AES256Util AES256Util;
		AES256Util= new AES256Util(key);
		return AES256Util;
	}
	@Bean
	public JavaMailSender mailSender() {
		JavaMailSenderImpl sender=new JavaMailSenderImpl();
		String id=env.getProperty("gmail.user.id");
		String passwd=env.getProperty("gmail.user.password");
		
		sender.setHost("smtp.gmail.com");
		sender.setPort(587);
		sender.setUsername(id);
		sender.setPassword(passwd);
		sender.setDefaultEncoding("UTF-8");
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", true);
		javaMailProperties.put("mail.smtp.starttls.enable", true);
		
		sender.setJavaMailProperties(javaMailProperties);
		
		return sender;
	}
}
