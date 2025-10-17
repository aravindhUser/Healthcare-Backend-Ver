package com.cts.hmproject.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.cts.hmproject.model.Patient;
import com.cts.hmproject.model.PatientDTO;

public interface PatientService {
	
	Patient addUser(Patient rpage);
	
	Map<String, Object> verify(Patient p);
	
	Patient addProfile(String patientEmail,Patient p);

	Patient getProfile(String patientEmail);

	PatientDTO getPatientProfile(int id);
}
