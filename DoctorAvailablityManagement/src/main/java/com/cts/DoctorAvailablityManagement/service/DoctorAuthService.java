package com.cts.DoctorAvailablityManagement.service;


import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.DoctorAvailablityManagement.config.FeignClientConfig;
import com.cts.DoctorAvailablityManagement.model.DoctorDTO;

@FeignClient(name="AUTH",configuration = FeignClientConfig.class)
public interface DoctorAuthService {
	
	@GetMapping("doctor/{id}")
	public DoctorDTO getDoctorById(@PathVariable int id) ;
	
	@GetMapping("doctor/getAllDoctors")
	public List<DoctorDTO> getAllDoctors();

}
