package com.cts.AppointmentBookingModule.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.AppointmentBookingModule.model.Appointment;
import com.cts.AppointmentBookingModule.service.AppointmentService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/appointments")
@AllArgsConstructor
public class AppointmentController {

	AppointmentService service;
	
	@GetMapping("viewByDoctor/{id}")
	public List<Appointment> getAllDoctorsAppointment(@PathVariable int id){
		return service.getByDoctor(id);	
	}
	
	@GetMapping("viewByPatient/{id}")
	public List<Appointment> getAllPatientAppointment(@PathVariable int id){
		return service.getByPatient(id);
	}
	
	@PostMapping("book/{slotId}")
	public Appointment bookForPatient(@PathVariable int slotId,@RequestBody Appointment ap){
		return service.bookAppointment(slotId, ap);
	}
	
	@PutMapping("doctorCancel/{aptId}")
	public Appointment deleteByDoctor(@PathVariable int aptId) {
		return service.cancelByPatient(aptId);
	}
	
	@PostMapping("patientCancel/{aptId}")
	public Appointment deleteByPatient(@PathVariable int aptId) {
		return service.cancelByDoctor(aptId);
	}
	
	
	
}

