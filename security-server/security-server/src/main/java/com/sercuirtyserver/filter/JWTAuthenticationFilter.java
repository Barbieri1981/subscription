package com.sercuirtyserver.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sercuirtyserver.constants.Constants;
import com.sercuirtyserver.dto.request.AuthenticationRequestDTO;
import com.sercuirtyserver.dto.response.AuthenticationResponseDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			AuthenticationRequestDTO credenciales = new ObjectMapper().readValue(request.getInputStream(), AuthenticationRequestDTO.class);

			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					credenciales.getUserName(), credenciales.getPassword(), new ArrayList<>()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		final String authorities = auth.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		String token = Jwts.builder().setIssuedAt(new Date()).setIssuer(Constants.ISSUER_INFO)
				.setSubject(((User)auth.getPrincipal()).getUsername()).claim(Constants.AUTHORITIES_KEY, authorities)
				.setExpiration(new Date(System.currentTimeMillis() + Constants.TOKEN_EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, Constants.SECRET_KEY).compact();
		response.addHeader(Constants.HEADER_AUTHORIZACION_KEY, Constants.TOKEN_BEARER_PREFIX + " " + token);
		response.addHeader("Content-Type","application/json");
		response.getWriter().write(mapper.writeValueAsString(AuthenticationResponseDTO.builder().accessToken(token).build()));
		response.getWriter().flush();
	}
}
