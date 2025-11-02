package com.cts.DoctorAvailablityManagement.client;


import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.DoctorAvailablityManagement.config.FeignClientConfig;
import com.cts.DoctorAvailablityManagement.dto.DoctorDTO;

@FeignClient(name="APIGATEWAY",configuration = FeignClientConfig.class)
public interface DoctorAuthService {
	
	@GetMapping("user/doctor/{id}")
	public DoctorDTO getDoctorById(@PathVariable int id) ;
	
	@GetMapping("user/doctor/getAllDoctors")
	public List<DoctorDTO> getAllDoctors();

}
