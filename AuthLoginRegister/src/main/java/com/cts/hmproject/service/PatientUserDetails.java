//package com.cts.hmproject.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.cts.hmproject.model.Doctor;
//import com.cts.hmproject.model.DoctorPrincipal;
//import com.cts.hmproject.model.Patient;
//import com.cts.hmproject.model.PatientPrincipal;
//import com.cts.hmproject.repository.DoctorRepo;
//import com.cts.hmproject.repository.PatientRepo;
//
//@Service
//public class PatientUserDetails implements UserDetailsService {
//
//	
//	  @Autowired
//	  private PatientRepo userRepo;
//	  
//	@Override
//	public UserDetails loadUserByUsername(String PatientEmail) throws UsernameNotFoundException {
//		Patient user = userRepo.findByPatientEmail(PatientEmail);
//        if (user == null) {
//            System.out.println("User Not Found");
//            throw new UsernameNotFoundException("user not found");
//        }
//        
//        return new PatientPrincipal(user);
//	}
//
//}
