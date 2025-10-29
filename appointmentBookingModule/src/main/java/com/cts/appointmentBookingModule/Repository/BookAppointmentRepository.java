package com.cts.appointmentBookingModule.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.appointmentBookingModule.model.BookAppointment;


@Repository
public interface BookAppointmentRepository extends JpaRepository<BookAppointment,Long> {
	List<BookAppointment> findByPatientId(int patientId);
}
