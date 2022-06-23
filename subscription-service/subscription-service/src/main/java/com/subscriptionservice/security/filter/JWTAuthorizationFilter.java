package com.subscriptionservice.security.filter;

import com.subscriptionservice.security.constants.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;


public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	public JWTAuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String header = req.getHeader(Constants.HEADER_AUTHORIZACION_KEY);
		if (header == null || !header.startsWith(Constants.TOKEN_BEARER_PREFIX)) {
			chain.doFilter(req, res);
			return;
		}
		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(Constants.HEADER_AUTHORIZACION_KEY);
		if (token != null) {
			String user = Jwts.parser()
					.setSigningKey(Constants.SECRET_KEY)
					.parseClaimsJws(token.replace(Constants.TOKEN_BEARER_PREFIX, ""))
					.getBody()
					.getSubject();

			Date expiration = Jwts.parser()
					.setSigningKey(Constants.SECRET_KEY)
					.parseClaimsJws(token.replace(Constants.TOKEN_BEARER_PREFIX, ""))
					.getBody()
					.getExpiration();

			if (expiration.before(new Date())){
				throw new AccessDeniedException("token has expired");
			}

			Claims claims=Jwts.parser()
					.setSigningKey(Constants.SECRET_KEY)
					.parseClaimsJws(token.replace(Constants.TOKEN_BEARER_PREFIX, ""))
					.getBody();

			final Collection<SimpleGrantedAuthority> authorities =
					Arrays.stream(claims.get(Constants.AUTHORITIES_KEY).toString().split(","))
							.map(SimpleGrantedAuthority::new)
							.collect(Collectors.toList());
			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, authorities);
			}
			return null;
		}
		return null;
	}

}