package com.test.handler;

import java.io.IOException;

import javax.annotation.Resource;
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
import com.test.Service.UserService;
import com.test.Service.UserServiceImpl;
import com.test.config.auto.PrincipalDetails;
import com.test.domain.Log;


public class loginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

	private RequestCache requestCache = new HttpSessionRequestCache();

	private String DefaultUrl="http://localhost:8801/spring/bbs/main";
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);

		clearAuthenticationAttributes(request);
		
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
	
	
	public String getClientIp(HttpServletRequest req) {
		return req.getRemoteAddr();
	}

}
