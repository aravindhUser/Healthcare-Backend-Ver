package com.cts.DoctorAvailablityManagement.repo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.DoctorAvailablityManagement.model.DoctorSlots;

public interface DoctorSlotsRepo extends JpaRepository<DoctorSlots, Integer> {
	
	List<DoctorSlots> findByDoctorId(int id);
	boolean existsByDoctorIdAndDateAndStartTimeAndEndTime(int doctorId,LocalDate date,LocalTime startTime,LocalTime endTime);

}
