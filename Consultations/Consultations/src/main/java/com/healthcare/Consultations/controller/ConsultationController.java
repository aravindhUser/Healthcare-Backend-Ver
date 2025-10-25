package com.healthcare.Consultations.controller;
 
import com.healthcare.Consultations.dto.ConsultationDTO;
import com.healthcare.Consultations.model.Consultation;
import com.healthcare.Consultations.service.ConsultationService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
 
@RestController
@RequestMapping("/api/consultations")
@AllArgsConstructor
@CrossOrigin// Angular dev server
public class ConsultationController {
 
    private final ConsultationService consultationService;
 
    //Save a new consultation
    @PostMapping
    public ResponseEntity<Consultation> createConsultation(@RequestBody ConsultationDTO dto) {
        Consultation saved = consultationService.saveConsultation(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
 
    // Get all consultations
    @GetMapping
    public ResponseEntity<List<Consultation>> getAllConsultations() {
        List<Consultation> consultations = consultationService.getAllConsultations();
        return new ResponseEntity<>(consultations, HttpStatus.OK);
    }
 
    // Get a consultation by its ID 
    @GetMapping("/{id}")
    public ResponseEntity<Consultation> getConsultationById(@PathVariable Long id) {
        Consultation consultation = consultationService.getConsultationById(id);
        return new ResponseEntity<>(consultation, HttpStatus.OK);
    }
    
    
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Consultation>> getConsultationsByDoctor(@PathVariable Long doctorId) {
        List<Consultation> consultations = consultationService.getConsultationsByDoctorId(doctorId);
        return new ResponseEntity<>(consultations, HttpStatus.OK);
    }
    
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Consultation>> getConsultationByPatient(@PathVariable String patientId){
    	List<Consultation> consultation = consultationService.getConsultationsByPatientId(patientId);
    	return new ResponseEntity<>(consultation, HttpStatus.OK);
    	
    }
}
 