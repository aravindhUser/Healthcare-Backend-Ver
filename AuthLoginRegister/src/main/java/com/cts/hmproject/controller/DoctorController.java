package com.cts.hmproject.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.cts.hmproject.dto.DoctorDTO;
import com.cts.hmproject.dto.UserInfo;
import com.cts.hmproject.dto.UserResponse;
import com.cts.hmproject.model.Doctor;
import com.cts.hmproject.service.DoctorService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("doctor")
@AllArgsConstructor
public class DoctorController {
	
	DoctorService service;
	
	//store the doctor details which they give during the registration
	@PostMapping("/register")
	public ResponseEntity<UserInfo> saveDoctor(@RequestBody UserInfo info)
	{
		service.saveDoctor(info);
		return ResponseEntity.ok().build();
	}
	
	//get the doctor details for the feign clients
	@GetMapping("/{id}")
	public DoctorDTO getDoctorById(@PathVariable int id) {
		System.out.println("Called");
		return service.getDoctorById(id);
	}
	
	//get the list of doctor details for the feign clients
	@GetMapping("/getAllDoctors")
	public List<DoctorDTO> getAllDoctors(){
		
		return service.getAllDoctors();
	}
	
	//store the doctor profiles
	@PostMapping("profile/{doctorId}")
	public  ResponseEntity<?> addProfile(@PathVariable int doctorId,@RequestBody Doctor up)
	{
		System.out.println("Controller Class");
		Doctor updated = service.addProfile(doctorId,up);
		if(updated!=null)
		{
			return ResponseEntity.ok(updated);
		}
		else
		{
			return ResponseEntity.badRequest().body("Doctor not found");
		}
		
	}
	
	//get the doctor profiles
	@PreAuthorize("hasRole('DOCTOR')")
	@GetMapping("get/{doctorId}")
	public ResponseEntity<?> getProfile(@PathVariable int doctorId)
	{
		System.out.println(doctorId);
		Doctor d = service.getProfile(doctorId);
		if(d != null)
		{
			return ResponseEntity.ok(d);
		}
		else
		{
			return ResponseEntity.badRequest().body("Doctor not found");
		}
	}
	
	//get the Doctor Id and Doctor Name and send it to the feign client
	@GetMapping("/getDoctor/{userId}")
	public UserResponse getDoctor(@PathVariable int userId)
	{
		return service.getDoctorbyUserId(userId);
	}
}
