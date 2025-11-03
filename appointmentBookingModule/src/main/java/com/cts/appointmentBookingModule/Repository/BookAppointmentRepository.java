package com.cts.appointmentBookingModule.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cts.appointmentBookingModule.model.BookAppointment;

import feign.Param;

@Repository
public interface BookAppointmentRepository extends JpaRepository<BookAppointment,Long> {
	List<BookAppointment> findByPatientId(int patientId);
	
	@Query("SELECT b FROM BookAppointment b WHERE b.doctorId = :doctorId AND b.date = :date AND b.startTime = :startTime")
	BookAppointment findByDoctorDateTime(@Param("doctorId") int doctorId, @Param("date") LocalDate date, @Param("startTime") LocalTime startTime);

	List<BookAppointment> findByDoctorId (int doctorId);
}
