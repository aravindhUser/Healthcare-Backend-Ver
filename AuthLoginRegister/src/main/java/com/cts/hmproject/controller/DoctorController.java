package com.cts.hmproject.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.hmproject.model.Doctor;
import com.cts.hmproject.model.DoctorDTO;
import com.cts.hmproject.service.DoctorService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("doctor")
@AllArgsConstructor
@CrossOrigin
public class DoctorController {
	
	DoctorService service;
	
	
	
	@PostMapping("register")
	public ResponseEntity<?> addUser(@RequestBody Doctor doctor)
	{
		Doctor savedDoctor = service.addUser(doctor);
		return ResponseEntity.ok(savedDoctor);
	}
	
	@GetMapping("/{id}")
	public DoctorDTO getDoctorById(@PathVariable int id) {
		System.out.println("Called");
		return service.getDoctorById(id);
	}
	
	@GetMapping("/getAllDoctors")
	public List<DoctorDTO> getAllDoctors(){
		
		return service.getAllDoctors();
	}
	
	@PostMapping("login")
	public ResponseEntity<?> login(@RequestBody Doctor doctor)
	{
		return ResponseEntity.ok(service.verify(doctor));
		
	}
	

//	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("profile/{doctorEmail}")
	public  ResponseEntity<?> addProfile(@PathVariable String doctorEmail,@RequestBody Doctor up)
	{
		System.out.println("Controller Class");
		Doctor updated = service.addProfile(doctorEmail,up);
		if(updated!=null)
		{
			return ResponseEntity.ok(updated);
		}
		else
		{
			return ResponseEntity.badRequest().body("Doctor not found");
		}
		
	}
	
	@GetMapping("get/{doctorEmail}")
	public ResponseEntity<?> getProfile( String doctorEmail)
	{
		Doctor d = service.getProfile(doctorEmail);
		if(d != null)
		{
			return ResponseEntity.ok(d);
		}
		else
		{
			return ResponseEntity.badRequest().body("Doctor not found");
		}
	}
	


}
