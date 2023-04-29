package com.ferro.termin.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ferro.termin.user.User;

public class CustomUserDetails extends User implements UserDetails {

	private Collection<? extends GrantedAuthority> authorities;

	public CustomUserDetails(User user) {
		super(user.getId(), user.getPassword(), user.getName(), user.getLastName(),
				user.getEmail(), user.getRoles());
		authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.toString()))
				.collect(Collectors.toList());
	}
	
	@Override
	public String getUsername() {
		return getEmail();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
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
		return true;
	}

}
