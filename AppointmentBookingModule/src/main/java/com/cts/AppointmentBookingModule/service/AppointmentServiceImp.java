package com.cts.AppointmentBookingModule.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cts.AppointmentBookingModule.model.Appointment;
import com.cts.AppointmentBookingModule.model.AvailablitySlotsDTO;
import com.cts.AppointmentBookingModule.model.DoctorDTO;
import com.cts.AppointmentBookingModule.model.PatientDTO;
import com.cts.AppointmentBookingModule.repo.AppointmentRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppointmentServiceImp implements AppointmentService {
	AppointmentRepo repo;
	
	AvailablityClient availablityClient;
	
	AuthClient authClient;
	
	@Override
	public List<Appointment> getByPatient(long patientId) {
	    List<Appointment> appointments = repo.findByPatId(patientId);
	    for (Appointment appointment : appointments) {
	        DoctorDTO doctor = authClient.getDoctorById(appointment.getDocId());
	        appointment.setDoctor(doctor); // assuming Appointment has a setDoctor method
	    }
	    return appointments;
	}

	
	@Override
	public List<Appointment> getByDoctor(long doctorId){
		List<Appointment> appointments = repo.findByDocId(doctorId);
		for (Appointment appointment : appointments) {
			PatientDTO patient = authClient.getPatientProfile(appointment.getPatId());
			appointment.setPatient(patient);
		}
		return appointments;
	}
	
	@Override
	public List<AvailablitySlotsDTO> getAvailableSlots(){
		return availablityClient.viewAllAvailablity();
	}
	
	@Override
	public Appointment bookAppointment(int id,Appointment ap) {
		boolean slotBooked = availablityClient.markBooked(id);
		if(!slotBooked) {
			throw new RuntimeException("Slot already Booked Not Available");
		}
		ap.setStatus("Booked");
		return repo.save(ap);
	}
	
	@Override
	public Appointment cancelByPatient(long id) {
		Appointment ap = repo.findById(id).orElse(null);
		ap.setStatus("CANCELLED BY PATIENT");
		repo.save(ap);
		
		boolean released = availablityClient.cancelBooking(id);
		if(!released) {
			throw new RuntimeException("Failed to cancel the Appointment");
		}
		
		return ap;
	}
	
	@Override
	public Appointment cancelByDoctor(long id) {
		Appointment ap = repo.findById(id).orElse(null);
		ap.setStatus("CANCELLED_BY_DOCTOR");
		repo.save(ap);
		
		boolean released = availablityClient.cancelBooking(id);
		if(!released) {
			throw new RuntimeException("Failed to cancel the Appointment");
		}
		return ap;
	}
	
	
	
	
	
	

}
