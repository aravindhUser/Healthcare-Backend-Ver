package com.cts.hmproject.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class DoctorPrincipal implements UserDetails {

	
	private Doctor user;
	
	
	public DoctorPrincipal(Doctor user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return Collections.singleton(new SimpleGrantedAuthority("ROLE_DOCTOR"));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getDoctorPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getDoctorEmail();
	}

}
