package com.cts.hmproject.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class PatientPrincipal implements UserDetails {

	
	private Patient user;
	
	public PatientPrincipal(Patient user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return Collections.singleton(new SimpleGrantedAuthority("ROLE_PATIENT"));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPatientPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getPatientEmail();
	}

}
