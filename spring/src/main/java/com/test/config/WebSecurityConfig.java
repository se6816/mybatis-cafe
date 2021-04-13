package com.test.config;

import com.test.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

import com.test.config.auto.PrincipalDetailsService;
import com.test.handler.LoginEntryPoint;
import com.test.handler.loginSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
    PrincipalDetailsService PrincipalDetailsService;
	
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
        auth.userDetailsService(PrincipalDetailsService).passwordEncoder(passwordEncoder());
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
		   .antMatchers("/user/**").authenticated()
		   .antMatchers("/file/**").permitAll()
		   .antMatchers("/reply/**").permitAll()
		   .antMatchers("/loginForm","/join").anonymous()
		   .antMatchers(HttpMethod.POST, "/api/user/**").permitAll()
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
		   .usernameParameter("username")
		   .passwordParameter("password")
		   .loginProcessingUrl("/login")
		   .successHandler(new loginSuccessHandler())
		   .failureUrl("/loginForm/fail")
		   .and()
		   .exceptionHandling()
		   .accessDeniedPage("/err/accessDenied")
		   .authenticationEntryPoint(new LoginEntryPoint("/loginForm"))
		   .and()
		   .logout()
		   .logoutUrl("/logout")
		   .logoutSuccessUrl("/bbs/main")
		   .invalidateHttpSession(true);
		
	}
}

