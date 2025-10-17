package com.cts.hmproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.hmproject.model.Doctor;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Integer> {
	Optional<Doctor> findOptionalByDoctorName(String DoctorName);
	Optional<Doctor> findOptionalByDoctorEmail(String DoctorEmail);
	Doctor findByDoctorName(String DoctorName);
	Doctor findByDoctorEmail(String DoctorEmail);
}
