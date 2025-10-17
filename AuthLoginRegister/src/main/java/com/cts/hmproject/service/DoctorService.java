package com.cts.hmproject.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.cts.hmproject.model.Doctor;
import com.cts.hmproject.model.DoctorDTO;

public interface DoctorService {

	Doctor addUser(Doctor rpage);

	Doctor addProfile(String doctorName,Doctor up);

	Doctor getProfile(String doctorEmail);

	Map<String, Object> verify(Doctor doctor);

	DoctorDTO getDoctorById(int id);
	
	List<DoctorDTO> getAllDoctors();

//	List<Doctor> getUser();

}
