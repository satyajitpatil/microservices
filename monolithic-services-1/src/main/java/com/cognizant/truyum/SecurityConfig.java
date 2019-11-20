package com.cognizant.truyum;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.cognizant.truyum.security.JwtAuthorizationFilter;
import com.cognizant.truyum.service.AppUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	AppUserDetailService appUserDetailService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//auth.userDetailsService(inMemoryUserDetailsManager());
		
		auth.authenticationProvider(authProvider());
	}
	
	@Bean
	public DaoAuthenticationProvider authProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(appUserDetailService);
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return authProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors();
		httpSecurity.csrf().disable().httpBasic().and().authorizeRequests()
				.antMatchers("/authenticate", "/menu-items").permitAll().anyRequest()
				.authenticated().and()
				.addFilter(new JwtAuthorizationFilter(authenticationManager()));
	}

	@Bean
	public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		// LOGGER.info("Start");
		List<UserDetails> userDetailsList = new ArrayList<>();

		userDetailsList.add(User.withUsername("user").password(passwordEncoder().encode("pwd"))
				.roles("USER").build());

		userDetailsList.add(User.withUsername("admin").password(passwordEncoder().encode("admin"))
				.roles("ADMIN").build());

		// LOGGER.info("End");
		return new InMemoryUserDetailsManager(userDetailsList);
	}

}
