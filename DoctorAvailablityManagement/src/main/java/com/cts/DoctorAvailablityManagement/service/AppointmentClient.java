package com.cts.DoctorAvailablityManagement.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


import com.cts.DoctorAvailablityManagement.config.FeignClientConfig;
import com.cts.DoctorAvailablityManagement.model.AppointmentDTO;

@FeignClient(name="AppointmentBookingModule",configuration=FeignClientConfig.class)
public interface AppointmentClient {
	
	@GetMapping("appointments/viewByDoctor/{id}")
	public List<AppointmentDTO> getAllDoctorsAppointment(@PathVariable("id") int id);
	
	@PutMapping("appointments/doctorCancel/{aptId}")
	public AppointmentDTO deleteByDoctor(@PathVariable("aptId") int aptId);
	
	
	

}
