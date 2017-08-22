package org.donnex.user.security.domain;

import java.util.Collection;

import org.donnex.user.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

public class CustomUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;
    private User user;

    public CustomUserDetails(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    String roles=StringUtils.collectionToCommaDelimitedString(user.getRoles());
        return AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isActive();
    }

    
    @Override
    public String getPassword() {
    	return user.getPassword();
    }

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	public User getUser() {
		return user;
	}
    
}