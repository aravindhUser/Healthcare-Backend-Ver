package com.cts.appointmentBookingModule.Service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.appointmentBookingModule.config.FeignClientConfig;
import com.cts.appointmentBookingModule.model.DoctorDTO;
import com.cts.appointmentBookingModule.model.PatientDTO;


@FeignClient(name="AUTH",configuration = FeignClientConfig.class)
public interface AuthenticationService {
	
	@GetMapping("doctor/{id}")
	public DoctorDTO getDoctorById(@PathVariable int id);
	
	@GetMapping("doctor/getAllDoctors")
	public List<DoctorDTO> getAllDoctors();
	
	@GetMapping("patient/{id}")
	public PatientDTO getPatientById(@PathVariable int id);

	@GetMapping("doctor")
	public DoctorDTO getDoctorByName(String doctorName);
	

}
