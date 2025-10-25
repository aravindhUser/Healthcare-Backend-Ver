package com.healthcare.Consultations.service;
 
import com.healthcare.Consultations.dto.ConsultationDTO;
import com.healthcare.Consultations.model.Consultation;
 
import java.util.List;
 
public interface ConsultationService {
	
    Consultation saveConsultation(ConsultationDTO dto);
    
    List<Consultation> getAllConsultations();
    
    Consultation getConsultationById(Long id);
    
    List<Consultation> getConsultationsByDoctorId(Long doctorId);
    
    List<Consultation> getConsultationsByPatientId(String patientId);
}
 