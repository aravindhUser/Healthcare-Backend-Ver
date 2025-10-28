package com.cts.DoctorAvailablityManagement.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.DoctorAvailablityManagement.model.AppointmentDTO;
import com.cts.DoctorAvailablityManagement.model.AvailablitySlot;
import com.cts.DoctorAvailablityManagement.model.DoctorDTO;
import com.cts.DoctorAvailablityManagement.service.DoctorAppointmentService;
import com.cts.DoctorAvailablityManagement.service.DoctorAuthService;

@CrossOrigin
@RestController
@RequestMapping("/api/doctors")
public class DoctorAvailablityController {
	
	@Autowired
	DoctorAppointmentService service;
	
	@Autowired
	DoctorAuthService client;
	 
	@GetMapping("availablity")
	public List<AvailablitySlot> viewAllAvailablity(){
		return service.viewAllAvailablity();
		
	}
	@GetMapping
	public List<DoctorDTO> viewDoctors(){
		return service.returnDoctors();
	}
	
	
	@GetMapping("/{id}")
	public DoctorDTO getDoctor(@PathVariable int id) {
		return service.getDoctor(id);
	}
	
	@GetMapping("/{id}/availablity")
	public List<AvailablitySlot> getAvailablity(@PathVariable int id){
		return service.getAvailablity(id);
	}
	
	@PostMapping("/{id}/availablity")
	public AvailablitySlot addAvailablity(@PathVariable int id,@RequestBody AvailablitySlot slot) {
		return service.addAvailablity(id, slot);
	}
	
	@GetMapping("/availablity/{slotId}")
	public AvailablitySlot viewAvailableSlot(@PathVariable int slotId) {
		return service.viewSlot(slotId);
	}
	
	@PostMapping("/availablity/{slotId}/book")
	public boolean markBooked(@PathVariable int slotId) {
		return service.bookAvailablity(slotId);
	}
	
	@PostMapping("/availablity/{slotId}/cancel")
	public boolean cancelBooking(@PathVariable int slotId) {
		return service.cancelBookedSlot(slotId);
	}
	
	@DeleteMapping("/availablity/{slotId}")
	public void deleteAvailablity(@PathVariable int slotId) {
		service.deleteAvailablity(slotId);
		
	}
	
	@GetMapping("search/{id}")
	public DoctorDTO doctorSearch(@PathVariable int id) {
		return service.getDoctor(id);
	}
	
	@GetMapping("{id}/appointment")
	public List<AppointmentDTO> getAppointment(@PathVariable int id){
		return service.getAppointment(id);
	}
	

}
