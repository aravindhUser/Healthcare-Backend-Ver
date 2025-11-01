package com.cts.hmproject.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.cts.hmproject.dto.DoctorDTO;
import com.cts.hmproject.dto.UserInfo;
import com.cts.hmproject.dto.UserResponse;
import com.cts.hmproject.model.Doctor;

public interface DoctorService {

//	Doctor addUser(Doctor rpage);

	Doctor addProfile(int doctorId,Doctor up);

	Doctor getProfile(int doctorId);

//	Map<String, Object> verify(Doctor doctor);

	DoctorDTO getDoctorById(int id);
	
	List<DoctorDTO> getAllDoctors();

	void saveDoctor(UserInfo info);

	UserResponse getDoctorbyUserId(int userId);

//	List<Doctor> getUser();

}
