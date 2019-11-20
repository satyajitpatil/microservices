package com.cognizant.truyum.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cognizant.truyum.model.User;

public class AppUser implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5809604222055009313L;
	
	
	private User user;
	private Collection<? extends GrantedAuthority> authorities;

	public AppUser(User user) {
		super();
		this.user = user;
		this.authorities = user.getRoleList().stream().map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toList());
		
		System.out.println("AUTHORITIES : "+this.authorities);
	}
	 

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
