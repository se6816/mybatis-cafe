package com.test.handler;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.test.Mapper.UserMapper;
import com.test.Service.BbsService;
import com.test.Service.UserService;
import com.test.Service.UserServiceImpl;
import com.test.config.auth.PrincipalDetails;
import com.test.domain.Log;
@Component
public class loginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

	
	private final UserService uSvc;
	private RequestCache requestCache = new HttpSessionRequestCache();
	
	public loginSuccessHandler(UserService uSvc) {
		super();
		this.uSvc = uSvc;
	}


	private String DefaultUrl="http://localhost:8801/spring/bbs/main";
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		String ip=this.getClientIp(request);
		String userid= request.getParameter("id");
		clearAuthenticationAttributes(request);
		uSvc.writeLog(new Log(userid,ip,true));
		
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
