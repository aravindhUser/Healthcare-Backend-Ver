package com.cts.hmproject.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.cts.hmproject.model.Patient;
import com.cts.hmproject.model.PatientDTO;
import com.cts.hmproject.service.PatientService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("patient")
@AllArgsConstructor
@CrossOrigin
public class PatientController {
	
	
	PatientService service;
	
	@GetMapping("/{id}")
	private ResponseEntity<PatientDTO> getPatientProfile(@PathVariable int id){
		return ResponseEntity.ok(service.getPatientProfile(id));
	}
	
	@PostMapping("register")
	private ResponseEntity<?> addUser(@RequestBody Patient rp)
	{
		Patient savedPatient = service.addUser(rp);
		return ResponseEntity.ok(savedPatient);
	}
	
	@PostMapping("login")
	private ResponseEntity<?> verify(@RequestBody Patient p)
	{
		return ResponseEntity.ok(service.verify(p));
	}
	
	@PostMapping("profile/{patientEmail}")
	public  ResponseEntity<?> addProfile(@PathVariable String patientEmail,@RequestBody Patient p)
	{
//		System.out.println("Controller Class");
		Patient updated = service.addProfile(patientEmail,p);
		if(updated!=null)
		{
			return ResponseEntity.ok(updated);
		}
		else
		{
			return ResponseEntity.badRequest().body("Doctor not found");
		}
		
	}
	
	@GetMapping("get/{patientEmail}")
	private ResponseEntity<?> getProfile(@PathVariable String patientEmail)
	{
		Patient p = service.getProfile(patientEmail);
		if(p != null)
		{
			return ResponseEntity.ok(p);
		}
		else
		{
			return ResponseEntity.badRequest().body("Doctor not found");
		}
	}

	
}
