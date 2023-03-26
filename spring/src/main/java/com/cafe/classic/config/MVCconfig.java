package com.cafe.classic.config;


import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@EnableRedisHttpSession
@ComponentScan(basePackages = {"com.cafe.classic"})
@PropertySource("classpath:application.properties")
@ActiveProfiles("prod")
public class MVCconfig implements WebMvcConfigurer{
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
    	registry.addRedirectViewController("/","/main");
	}
	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver() {
		InternalResourceViewResolver resolver= new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		resolver.setContentType("text/html;charset=UTF-8");
		return resolver;
	}
	@Bean(name="multipartResolver")
	public CommonsMultipartResolver commonsMultipartResolver() {
		CommonsMultipartResolver MultipartResolver = new CommonsMultipartResolver();
		MultipartResolver.setMaxUploadSize(MAX_SIZE);
		return MultipartResolver;
	}
	@Bean
	public JavaMailSender mailSender(@Value("${mail.user.id}") String id,
			                         @Value("${mail.user.password}") String passwd) {
		JavaMailSenderImpl sender=new JavaMailSenderImpl();
		sender.setHost("smtp.naver.com");
		sender.setPort(465);
		sender.setUsername(id);
		sender.setPassword(passwd);
		sender.setDefaultEncoding("UTF-8");
		Properties javaMailProperties = new Properties();
		
		javaMailProperties.put("mail.smtp.auth", true);
		javaMailProperties.put("mail.smtp.ssl.enable",true);
		javaMailProperties.put("mail.smtp.ssl.trust","smtp.naver.com");
		javaMailProperties.put("mail.smtp.starttls.enable", true);
		
		sender.setJavaMailProperties(javaMailProperties);
		
		return sender;
	}
}
