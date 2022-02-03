package com.test.config;

import com.test.config.auth.PrincipalDetailsService;
import com.test.handler.*;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.test.handler.LoginEntryPoint;
import com.test.handler.loginSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
    private final PrincipalDetailsService PrincipalDetailsService;	
	private final loginSuccessHandler loginSuccessHandler;
	private final loginFailHandler loginFailHandler;
	

	public WebSecurityConfig(PrincipalDetailsService principalDetailsService,
			loginSuccessHandler loginSuccessHandler,
			loginFailHandler loginFailHandler) {
		PrincipalDetailsService = principalDetailsService;
		this.loginSuccessHandler = loginSuccessHandler;
		this.loginFailHandler = loginFailHandler;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(PrincipalDetailsService).
        passwordEncoder(passwordEncoder());
    }

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/css/**","/js/**","/img/**");
		web.httpFirewall(defaultHttpFirewall());
	}
	@Bean
	public HttpFirewall defaultHttpFirewall() {
		return new DefaultHttpFirewall();
		
	}
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable();
		http.authorizeRequests()
		   .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
		   .antMatchers("/api/admin/**").access("hasRole('ROLE_ADMIN')")
		   .antMatchers("/user/change/**").permitAll()
		   .antMatchers("/user/**/bbs").permitAll()
		   .antMatchers("/user/**/reply").permitAll()
		   .antMatchers("/user/email/**").permitAll()
		   .antMatchers("/user/**").authenticated()
		   .antMatchers("/file/**").permitAll()
		   .antMatchers("/reply/**").permitAll()
		   .antMatchers("/loginForm","/join","/find_id_pw").anonymous()
		   .antMatchers(HttpMethod.POST, "/api/user/**").permitAll()
		   .antMatchers(HttpMethod.PUT,"/api/user/**").permitAll()
		   .antMatchers("/api/email/check").permitAll()
		   .antMatchers("/api/user/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MEMBER')")
		   .antMatchers("/api/report/**").access("hasRole('ROLE_MEMBER')")
		   .antMatchers("/api/reply/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MEMBER')")
		   .antMatchers("/api/lovers/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MEMBER')")
		   .antMatchers("/api/board/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MEMBER')")
		   .antMatchers("/bbs/**/write").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MEMBER')")
		   .antMatchers("/bbs/**/modify").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MEMBER')")
		   .anyRequest().permitAll()
		   .and()
		   .formLogin()
		   .loginPage("/loginForm")
		   .usernameParameter("id")
		   .passwordParameter("password")
		   .loginProcessingUrl("/login")
		   .successHandler(loginSuccessHandler)
		   .failureHandler(loginFailHandler)
		   .and()
		   .exceptionHandling()		 
		   .accessDeniedPage("/bbs/main")
		   .authenticationEntryPoint(new LoginEntryPoint("/loginForm"))
		   .and()
		   .logout()
		   .logoutUrl("/logout")
		   .logoutSuccessUrl("/bbs/main")
		   .deleteCookies("JSESSIONID")
		   .invalidateHttpSession(true)
		   .and()
		   .sessionManagement()
		   .maximumSessions(1)
		   .maxSessionsPreventsLogin(true)
		   .sessionRegistry(sessionRegistry())
		   .expiredUrl("/loginForm?expired=true");
		http.cors()
			.configurationSource(corsConfigurationSource()).and();
			
			
	}
	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config= new CorsConfiguration();
		config.addAllowedOrigin("*");
		config.addAllowedMethod("*");
		config.addAllowedHeader("*");
		config.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source= new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

}

