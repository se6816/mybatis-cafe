package com.cafe.classic.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

	
	public SecurityWebApplicationInitializer() {
		super(WebSecurityConfig.class,MVCconfig.class);
	}

	@Override
	protected boolean enableHttpSessionEventPublisher() {
		return true;
	}
	
	
}
