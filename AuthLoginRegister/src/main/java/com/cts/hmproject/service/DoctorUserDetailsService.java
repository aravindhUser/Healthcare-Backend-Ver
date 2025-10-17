package com.cts.hmproject.service;


import com.cts.hmproject.model.Doctor;
import com.cts.hmproject.model.DoctorPrincipal;
import com.cts.hmproject.model.Patient;
import com.cts.hmproject.repository.DoctorRepo;
//import com.cts.hmproject.model.UserPrincipal;
//import com.sakthi.JWTValidation.model.Users;
//import com.cts.hmproject.repository.UserRepo;
import com.cts.hmproject.repository.PatientRepo;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

 
@Service
public class DoctorUserDetailsService implements UserDetailsService {
 
    @Autowired
    private DoctorRepo doctorRepo;
    
    @Autowired
	  private PatientRepo patientRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Doctor user = doctorRepo.findByDoctorEmail(email);
        if (user != null) {
        	return new User(user.getDoctorEmail(), user.getDoctorPassword(), Arrays.asList(new SimpleGrantedAuthority("ROLE_DOCTOR")));
        }
        
        Patient patient = patientRepo.findByPatientEmail(email);
        if (patient != null) {
            return new User(patient.getPatientEmail(), patient.getPatientPassword(), Arrays.asList(new SimpleGrantedAuthority("ROLE_PATIENT")));
        }

        throw new UsernameNotFoundException("User Not Found");
        
//        return new DoctorPrincipal(user);
	}
 
    
 
    
}
 
