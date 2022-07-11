package com.subscriptionservice.security.client;


import com.subscriptionservice.security.constants.Constants;
import com.subscriptionservice.security.filter.JWTAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {



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
				.authorizeRequests().antMatchers(HttpMethod.POST, Constants.CREATE_SUBSCRIPTION_URL).permitAll()
				.antMatchers(HttpMethod.POST, Constants.CANCEL_SUBSCRIPTION_URL).permitAll()
				.antMatchers(
						"/v2/api-docs",
						"/swagger-resources/**",
						"/actuator/**",
						"/swagger-ui.html",
						"/webjars/**" ,
						"/swagger.json")
				.permitAll()
				.anyRequest().authenticated().and()
				.addFilter(new JWTAuthorizationFilter(authenticationManager()));
	}



	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}

}
