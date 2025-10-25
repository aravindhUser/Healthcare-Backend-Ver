package com.healthcare.Consultations.repository;


import com.healthcare.Consultations.model.Consultation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface ConsultationRepo extends JpaRepository<Consultation, Integer> {
	List<Consultation> findByDoctorId(int doctorId);
	List<Consultation> findByPatientId(int patientId);
}
