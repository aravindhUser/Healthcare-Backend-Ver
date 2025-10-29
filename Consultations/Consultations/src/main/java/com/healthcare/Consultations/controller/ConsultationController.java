package com.healthcare.Consultations.controller;

import com.healthcare.Consultations.dto.ConsultationDTO;  
import com.healthcare.Consultations.model.Consultation;  
import com.healthcare.Consultations.service.ConsultationService;  
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;  
  
import java.util.List;  
 
@RestController  
@RequestMapping("/api/consultations")  
@CrossOrigin 
@AllArgsConstructor  
@Slf4j
public class ConsultationController {  
	
    private final ConsultationService consultationService;  
  
    @PostMapping  
    public Consultation createConsultation(@RequestBody ConsultationDTO dto) {  
    	log.info("API Call: Create Consultation request received");
        return consultationService.saveConsultation(dto);  
    }  
  
    @GetMapping  
    public List<Consultation> getAllConsultations() {
    	log.info("API Call: Get all Consultations");
        return consultationService.getAllConsultations();  
    }  
  
    @GetMapping("/doctor/{doctorId}")  
    public List<Consultation> getByDoctor(@PathVariable("doctorId") int doctorId) {
    	log.info("API Call: Get Consultations by doctor {}",doctorId);
        return consultationService.getConsultationsByDoctor(doctorId);  
    }  
  
    @GetMapping("/patient/{patientId}")  
    public List<Consultation> getByPatient(@PathVariable("patientId") int patientId) { 
    	log.info("API Call: Get Consultations by patient {}",patientId);
        return consultationService.getConsultationsByPatient(patientId);  
    }  
  
    @GetMapping("/{id}")  
    public Consultation getById(@PathVariable("id") int id) {  
    	log.info("API Call: Get Consultations by ID{}",id);

        return consultationService.getConsultationById(id);  
    } 
}
