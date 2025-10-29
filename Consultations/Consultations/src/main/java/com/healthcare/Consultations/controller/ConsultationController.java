package com.healthcare.Consultations.controller;

import com.healthcare.Consultations.dto.ConsultationDTO;  
import com.healthcare.Consultations.model.Consultation;  
import com.healthcare.Consultations.service.ConsultationService;  
import lombok.AllArgsConstructor;  
import org.springframework.web.bind.annotation.*;  
  
import java.util.List;  
  
@RestController  
@RequestMapping("/api/consultations")  
@CrossOrigin 
@AllArgsConstructor  
public class ConsultationController {  
  
    private final ConsultationService consultationService;  
  
    @PostMapping  
    public Consultation createConsultation(@RequestBody ConsultationDTO dto) {  
        return consultationService.saveConsultation(dto);  
    }  
  
    @GetMapping  
    public List<Consultation> getAllConsultations() {  
        return consultationService.getAllConsultations();  
    }  
  
    @GetMapping("/doctor/{doctorId}")  
    public List<Consultation> getByDoctor(@PathVariable("doctorId") int doctorId) {  
        return consultationService.getConsultationsByDoctor(doctorId);  
    }  
  
    @GetMapping("/patient/{patientId}")  
    public List<Consultation> getByPatient(@PathVariable("patientId") int patientId) { 
    	System.out.println("Patient Conrtoller Called");
        return consultationService.getConsultationsByPatient(patientId);  
    }  
  
    @GetMapping("/{id}")  
    public Consultation getById(@PathVariable("id") int id) {  
        return consultationService.getConsultationById(id);  
    } 
}
