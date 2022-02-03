package com.test.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.test.Service.UserService;
import com.test.domain.Log;
@Component
public class loginFailHandler extends SimpleUrlAuthenticationFailureHandler {
	
//	private final Logger logger= new LoggerFactory.getLogger(this.getClass());
	private final UserService uSvc;

	private final String defaultFailureUrl="/loginForm/fail";
	private boolean forwardToDestination = false;
	private boolean allowSessionCreation = true;
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public loginFailHandler(UserService uSvc) {
		this.uSvc = uSvc;
	}



	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		String ip=this.getClientIp(request);
		String id= request.getParameter("id");
		uSvc.writeLog(new Log(id,ip,false));
		saveException(request, exception);

		if (forwardToDestination) {
				
			request.getRequestDispatcher(defaultFailureUrl+"?id="+id)
					.forward(request, response);
		}
		else {
			redirectStrategy.sendRedirect(request, response, defaultFailureUrl+"?id="+id);
		}
		
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
