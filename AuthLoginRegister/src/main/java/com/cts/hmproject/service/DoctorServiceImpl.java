package com.cts.hmproject.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.hmproject.dto.DoctorDTO;
import com.cts.hmproject.dto.UserInfo;
import com.cts.hmproject.dto.UserResponse;
import com.cts.hmproject.model.Doctor;
import com.cts.hmproject.repository.DoctorRepo;



@Service
public class DoctorServiceImpl implements DoctorService {

	@Autowired
	DoctorRepo repo;

	@Override
	public Doctor addProfile(int doctorId,Doctor up) {
		Optional<Doctor> optDoctor = repo.findById(doctorId);
	
		if(optDoctor!=null)
		{
			Doctor doctor = optDoctor.get();
			
			doctor.setSpecialization(up.getSpecialization());
			doctor.setQualification(up.getQualification());
			doctor.setExperience(up.getExperience());
			doctor.setAddress(up.getAddress());
			doctor.setAbout(up.getAbout());
			System.out.println(doctor);
			
			return repo.save(doctor);
		}
		else
		{
			System.out.println("Null Element in doctor");
			return null;
		}
	}

	@Override
	public Doctor getProfile(int doctorId) {
		// TODO Auto-generated method stu
		Doctor d=repo.findById(doctorId).orElse(null);
		return d;
		
	}

	@Override
	public DoctorDTO getDoctorById(int id) {
		// TODO Auto-generated method stub
		Optional<Doctor> d = repo.findById(id);
		Doctor doctor = d.get();
		DoctorDTO dt = new DoctorDTO(doctor); 
		return dt;
	}

	@Override
	public List<DoctorDTO> getAllDoctors() {
	    List<Doctor> doctors = repo.findAll();

	    List<DoctorDTO> found = doctors.stream()
	                                   .map(doctor -> new DoctorDTO(doctor)) // assuming DoctorDTO has a constructor that accepts Doctor
	                                   .collect(Collectors.toList());

	    return found;
	}

	@Override
	public void saveDoctor(UserInfo info) {
		// TODO Auto-generated method stub
		Doctor d = new Doctor();
		
		d.setUserId(info.getUserId());
		d.setDoctorName(info.getName());
		d.setDoctorEmail(info.getEmail());
		d.setDoctorMobileNumber(info.getMobileNumber());
		
		repo.save(d);
		
	}

	@Override
	public UserResponse getDoctorbyUserId(int userId) {
		Doctor dt = repo.findByUserId(userId);
		System.out.println(dt);
				
		UserResponse user = new UserResponse(dt);
		return user;
	}

}
