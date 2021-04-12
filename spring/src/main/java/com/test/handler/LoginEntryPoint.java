package com.test.handler;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

public class LoginEntryPoint extends LoginUrlAuthenticationEntryPoint {

	public LoginEntryPoint(String loginFormUrl) {
		super(loginFormUrl);
		// TODO Auto-generated constructor stub
	}


	private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		
		String isAjax=request.getHeader("X-Requested-With");
		if("XMLHttpRequest".equals(isAjax)) {
			System.out.println("ajax");
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
		else {
			System.out.println("forward");
			super.commence(request, response, authException);
		}
		
	}

}
