package com.cts.AppointmentBookingModule.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.AppointmentBookingModule.model.Appointment;

public interface AppointmentRepo extends JpaRepository<Appointment, Long>{
	List<Appointment> findByPatId(long id);
	List<Appointment> findByDocId(long id);
}
