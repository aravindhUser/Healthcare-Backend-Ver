package com.cts.AppointmentBookingModule.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.AppointmentBookingModule.config.FeignClientConfig;
import com.cts.AppointmentBookingModule.model.DoctorDTO;
import com.cts.AppointmentBookingModule.model.PatientDTO;


@FeignClient(name="RESGISTRATIONPAGE",configuration = FeignClientConfig.class)
public interface AuthClient {
	@GetMapping("doctor/{id}")
	public DoctorDTO getDoctorById(@PathVariable("id") long l);
	
	@GetMapping("patient/{id}")
	public PatientDTO getPatientProfile(@PathVariable("id") long l);
}
