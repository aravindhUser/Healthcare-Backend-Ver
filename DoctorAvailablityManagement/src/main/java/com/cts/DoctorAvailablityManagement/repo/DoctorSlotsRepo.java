package com.cts.DoctorAvailablityManagement.repo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cts.DoctorAvailablityManagement.model.DoctorSlots;

import feign.Param;

public interface DoctorSlotsRepo extends JpaRepository<DoctorSlots, Integer> {
	
	List<DoctorSlots> findByDoctorId(int id);
	boolean existsByDoctorIdAndDateAndStartTimeAndEndTime(int doctorId,LocalDate date,LocalTime startTime,LocalTime endTime);
	@Query ("SELECT ds FROM DoctorSlots ds WHERE ds.doctorId = :doctorId AND (ds.date > :today OR (ds.date = :today AND ds.endTime >= :now)) ORDER BY ds.date DESC, ds.endTime DESC")
	List<DoctorSlots> findUpcomingSlotsByDoctorId(
	    @Param ("doctorId") int doctorId,
	    @Param("today") LocalDate today,
	    @Param("now") LocalTime now
	);

}
