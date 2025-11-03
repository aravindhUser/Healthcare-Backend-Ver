package com.healthcare.Consultations.service;

import com.healthcare.Consultations.dto.DoctorDTO;
import com.healthcare.Consultations.dto.PatientDTO;

import org.springframework.cloud.openfeign.FeignClient;  
import org.springframework.web.bind.annotation.GetMapping;  
import org.springframework.web.bind.annotation.PathVariable;  
  
@FeignClient(name="USERSERVICE", configuration = FeignClientConfig.class)  
public interface AuthClient { 
	
    @GetMapping("doctor/{id}")  
    DoctorDTO getDoctorById(@PathVariable("id") int id);
    
    @GetMapping("patient/{id}")  
    PatientDTO getPatientById(@PathVariable("id") int id); 
    
    
}  