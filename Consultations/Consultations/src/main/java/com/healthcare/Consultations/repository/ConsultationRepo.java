package com.healthcare.Consultations.repository;

import com.healthcare.Consultations.model.Consultation;  
import org.springframework.data.jpa.repository.JpaRepository;  
import org.springframework.stereotype.Repository;  
  
import java.util.List;  
  
@Repository  
public interface ConsultationRepo extends JpaRepository<Consultation, Integer> {  
    List<Consultation> findByDoctorId(int doctorId);  
    List<Consultation> findByPatientId(int patientId);  
}  