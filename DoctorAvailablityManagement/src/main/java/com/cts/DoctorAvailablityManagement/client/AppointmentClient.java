package com.cts.DoctorAvailablityManagement.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


import com.cts.DoctorAvailablityManagement.config.FeignClientConfig;
import com.cts.DoctorAvailablityManagement.dto.AppointmentDTO;

@FeignClient(name="APPOINTMENTBOOKINGMODULE",configuration=FeignClientConfig.class)
public interface AppointmentClient {
	
	@GetMapping("appointments/doctor/fetch/{id}")
	public List<AppointmentDTO> getAllDoctorsAppointment(@PathVariable("id") int id);
	
	@PutMapping("appointments/cancel/doctor/{aptId}")
	public AppointmentDTO deleteByDoctor(@PathVariable("aptId") int aptId);
	
	@GetMapping("appointments/doctor/get/{apptId}")
	public AppointmentDTO getAppointmentByDoctor(@PathVariable("apptId") int apptId);
		
}
	
	


