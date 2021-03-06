package org.donnex.user.security.filter;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.donnex.user.domain.User;
import org.donnex.user.security.domain.AccountCredentials;
import org.donnex.user.security.domain.CustomUserDetails;
import org.donnex.user.security.domain.UserDTO;
import org.donnex.user.security.service.TokenAuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
	
	public JWTLoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException, IOException, ServletException {

		AccountCredentials creds = new ObjectMapper().readValue(req.getInputStream(), AccountCredentials.class);

		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(),creds.getPassword(), Collections.emptyList()));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		CustomUserDetails details = (CustomUserDetails) auth.getPrincipal();
		User user = details.getUser();
		res.addHeader("userId", user.getId().toString());
		res.addHeader("username", user.getEmail());
		res.addHeader("firstName", user.getName());
		
		TokenAuthenticationService.addAuthentication(res, new UserDTO(user.getEmail(), user.getRoles()));
	}
}
