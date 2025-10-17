package com.cts.AppointmentBookingModule.service;

import java.util.List;

import com.cts.AppointmentBookingModule.model.Appointment;
import com.cts.AppointmentBookingModule.model.AvailablitySlotsDTO;

public interface AppointmentService {

	List<Appointment> getByPatient(long patientId);

	List<Appointment> getByDoctor(long doctorId);

	List<AvailablitySlotsDTO> getAvailableSlots();

	Appointment bookAppointment(int id, Appointment ap);

	Appointment cancelByPatient(long id);

	Appointment cancelByDoctor(long id);

}