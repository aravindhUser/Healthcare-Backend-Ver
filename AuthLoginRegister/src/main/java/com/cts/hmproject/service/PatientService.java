package com.cts.hmproject.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.cts.hmproject.dto.PatientDTO;
import com.cts.hmproject.dto.UserInfo;
import com.cts.hmproject.dto.UserResponse;
import com.cts.hmproject.model.Patient;

public interface PatientService {
	
	void savePatient(UserInfo info);
	
//	Patient addUser(Patient rpage);
	
//	Map<String, Object> verify(Patient p);
	
	Patient addProfile(int patientId,Patient p);

	Patient getProfile(int patientId);

	PatientDTO getPatientProfile(int id);

	UserResponse getPatientbyUserId(int userId);
}
