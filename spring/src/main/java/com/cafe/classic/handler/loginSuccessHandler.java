package com.cafe.classic.handler;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mybatis.spring.mapper.MapperFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.cafe.classic.Mapper.UserMapper;
import com.cafe.classic.Service.Bbs.BbsService;
import com.cafe.classic.Service.user.UserService;
import com.cafe.classic.Service.user.UserServiceImpl;
import com.cafe.classic.config.auth.PrincipalDetails;
@Component
public class loginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

	
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	private RequestCache requestCache = new HttpSessionRequestCache();


	private String DefaultUrl="http://localhost:8801/spring/main";
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		String ip=this.getClientIp(request);
		String id= request.getParameter("id");
		clearAuthenticationAttributes(request);
		logger.info("Login Success!! ip: {}, id : {}",ip,id );
		if (savedRequest != null) {
			String targetUrl= savedRequest.getRedirectUrl();
			getRedirectStrategy().sendRedirect(request, response, targetUrl);
			return;
		}
		
		getRedirectStrategy().sendRedirect(request, response, DefaultUrl);
	}
	
	
	
	public void setRequestCache(RequestCache requestCache) {
		this.requestCache = requestCache;
	}
	
	
	public String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		     ip = request.getHeader("Proxy-Client-IP"); 
		 } 
		 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		     ip = request.getHeader("WL-Proxy-Client-IP"); 
		 } 
		 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		     ip = request.getHeader("HTTP_CLIENT_IP"); 
		 } 
		 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		     ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
		 } 
		 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		     ip = request.getRemoteAddr(); 
		 }

		return ip;
	}

}
