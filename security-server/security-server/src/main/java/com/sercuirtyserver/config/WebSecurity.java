package com.sercuirtyserver.config;


import com.sercuirtyserver.constants.Constants;
import com.sercuirtyserver.filter.JWTAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

	private UserDetailsService userDetailsService;

	public WebSecurity(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		httpSecurity
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.cors().configurationSource(request -> {
					final CorsConfiguration configuration = new CorsConfiguration();
					configuration.setAllowedOrigins(new ArrayList<>(Arrays.asList("*")));
					configuration.setAllowedMethods(
							new ArrayList<>(Arrays.asList("HEAD",
									"GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")));
					configuration.setAllowedHeaders(new ArrayList<>(Arrays.asList("*")));
					configuration.setExposedHeaders(new ArrayList<>(Arrays.asList("X-Auth-Token","Authorization","Access-Control-Allow-Origin",
							"Access-Control-Allow-Credentials")));
					return  configuration;
				}).and()
				.csrf().disable()
				.authorizeRequests().antMatchers(HttpMethod.POST, Constants.LOGIN_URL).permitAll()
				.antMatchers(
						"/v2/api-docs",
						"/swagger-resources/**",
						"/swagger-ui.html",
						"/webjars/**" ,
						"/swagger.json")
				.permitAll()
				.anyRequest().authenticated().and()
				.addFilter(new JWTAuthenticationFilter(authenticationManager()));
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}

}
