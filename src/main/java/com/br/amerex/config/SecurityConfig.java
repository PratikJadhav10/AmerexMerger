package com.br.amerex.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/*
	 * @Override protected void configure(HttpSecurity http) throws Exception {
	 * http.csrf().disable() // Disable CSRF protection for the /api/csrf-token
	 * endpoint .authorizeRequests().antMatchers("/api/csrf-token").permitAll() //
	 * Allow access to /api/csrf-token // without authentication
	 * .anyRequest().authenticated().and().httpBasic(); }
	 */
	
	/*
	 * @Override protected void configure(HttpSecurity http) throws Exception { http
	 * .csrf().disable() // Disable CSRF protection .authorizeRequests()
	 * .anyRequest().permitAll(); // Allow access to all endpoints without
	 * authentication }
	 */
}
