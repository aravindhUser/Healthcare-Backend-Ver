package com.cts.hmproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.hmproject.dto.UserResponse;
import com.cts.hmproject.model.Patient;

@Repository
public interface PatientRepo extends JpaRepository<Patient, Integer> {
	
	Optional<Patient> findOptionalByPatientEmail(String patientEmail);
	Patient findByPatientEmail(String patientEmail);
	
	Patient findByUserId(int userId);
}
