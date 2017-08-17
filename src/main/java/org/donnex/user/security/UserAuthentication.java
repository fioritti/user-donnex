package org.donnex.user.security;

import java.util.Collection;

import org.donnex.user.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class UserAuthentication implements Authentication{

	private static final long serialVersionUID = 1L;
	private final User user;
	private boolean authentication = true;
	
	public UserAuthentication(User user) {
		super();
		this.user = user;
	}

	@Override
	public String getName() {
		return user.getName();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return (Collection<? extends GrantedAuthority>) user.getRoles();
	}

	@Override
	public Object getCredentials() {
		return user.getPassword();
	}

	@Override
	public Object getDetails() {
		return user;
	}

	@Override
	public Object getPrincipal() {
		return user.getEmail();
	}

	@Override
	public boolean isAuthenticated() {
		return authentication;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.authentication = isAuthenticated;
	}

}
