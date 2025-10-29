package com.cts.DoctorAvailablityManagement.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cts.DoctorAvailablityManagement.model.AvailablitySlot;

import feign.Param;

@Repository
public interface AvailablityRepo extends JpaRepository<AvailablitySlot, Integer>{
	List<AvailablitySlot> findByDoctorId(int doctorId);
	
	@Query("SELECT a FROM AvailablitySlot a WHERE a.doctorId = :doctorId AND a.date = :date")
	List<AvailablitySlot> findByDocIdAndDate(@Param("doctorId") int doctorId, @Param("date") LocalDate date);

}
