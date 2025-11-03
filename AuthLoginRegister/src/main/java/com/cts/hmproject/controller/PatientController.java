package com.cts.hmproject.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.hmproject.dto.PatientDTO;
import com.cts.hmproject.dto.UserInfo;
import com.cts.hmproject.dto.UserResponse;
import com.cts.hmproject.model.Patient;
import com.cts.hmproject.service.PatientService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("patient")
@AllArgsConstructor
//@CrossOrigin
public class PatientController {
	
	
	PatientService service;
	
	@GetMapping("/{id}")
	private ResponseEntity<PatientDTO> getPatientProfile(@PathVariable int id){
		return ResponseEntity.ok(service.getPatientProfile(id));
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserInfo> savePatient(@RequestBody UserInfo info)
	{
		service.savePatient(info);
		return ResponseEntity.ok().build();
	}
	
//	@PostMapping("register")
//	private ResponseEntity<?> addUser(@RequestBody Patient rp)
//	{
//		Patient savedPatient = service.addUser(rp);
//		return ResponseEntity.ok(savedPatient);
//	}
	
	
	
//	@PostMapping("login")
//	private ResponseEntity<?> verify(@RequestBody Patient p)
//	{
//		return ResponseEntity.ok(service.verify(p));
//	}
	
	@PostMapping("profile/{patientId}")
	public  ResponseEntity<?> addProfile(@PathVariable int patientId,@RequestBody Patient p)
	{
//		System.out.println("Controller Class");
		Patient updated = service.addProfile(patientId,p);
		if(updated!=null)
		{
			return ResponseEntity.ok(updated);
		}
		else
		{
			return ResponseEntity.badRequest().body("Doctor not found");
		}
		
	}
	
	@GetMapping("get/{patientId}")
	private ResponseEntity<?> getProfile(@PathVariable int patientId)
	{
		Patient p = service.getProfile(patientId);
		if(p != null)
		{
			return ResponseEntity.ok(p);
		}
		else
		{
			return ResponseEntity.badRequest().body("Patient not found");
		}
	}

	@GetMapping("/getPatient/{userId}")
	public UserResponse getDoctor(@PathVariable int userId)
	{
		return service.getPatientbyUserId(userId);
	}
	
}
