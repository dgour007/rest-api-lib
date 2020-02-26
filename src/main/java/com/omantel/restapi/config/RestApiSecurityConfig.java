/**
 * 
 */
package com.omantel.restapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.omantel.restapi.security.CustomBasicAuthenticationEntryPoint;

/**
 * @author Dhiraj Gour
 * @date 20 August 2019
 *
 */
@Configuration
@EnableWebSecurity
public class RestApiSecurityConfig extends WebSecurityConfigurerAdapter {

	private static String REALM = "CRM+_REALM";

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("crm").password("{noop}m3r2c1").roles("USER").and()
		.withUser("pos").password("{noop}pos321").roles("USER").and()
		.withUser("cma").password("{noop}cma987").roles("USER").and()
		.withUser("uoms").password("{noop}uo12ms34").roles("USER").and()
		.withUser("cicm").password("{noop}cic123m4").roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				.anyRequest().authenticated()
				.and().httpBasic().realmName(REALM)
				.authenticationEntryPoint(getBasicAuthEntryPoint())
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // We don't need sessions to be created.
				.and().formLogin().disable();

		http.csrf().disable();
	}

	@Bean
	public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint() {
		return new CustomBasicAuthenticationEntryPoint();
	}

	//To allow Pre-flight [OPTIONS] request from browser 
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
	}
}