package com.cts.appointmentBookingModule.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.appointmentBookingModule.Repository.BookAppointmentRepository;

import com.cts.appointmentBookingModule.model.AvailabilitySlotDTO;
import com.cts.appointmentBookingModule.model.BookAppointment;
import com.cts.appointmentBookingModule.model.DoctorDTO;
import com.cts.appointmentBookingModule.model.PatientDTO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookAppointmentServiceImpl implements BookAppointmentService {
	@Autowired
	BookAppointmentRepository repo;
	
	AuthenticationService authService;
	DoctorAvailabilityClient docService;
//	@Autowired
//	AuthenticationService authService;
//	
//	@Autowired
//	DoctorAvailabilityClient docService;
	
	@Override
	public BookAppointment bookAppointmentBySlot(int slotId, BookAppointment appointment) {
	    boolean bookslot=docService.markSlotBooked(slotId);
	    if(!bookslot)
	    	throw new RuntimeException("Slot Already Booked");
	    appointment.setStatus("Booked");
	    DoctorDTO doctor = authService.getDoctorById(appointment.getDoctorId());
	    PatientDTO patient = authService.getPatientById(appointment.getPatientId());
	    appointment.setDoctor(doctor);
	    appointment.setPatient(patient);
	    return repo.save(appointment); 
	      
	}


//	@Override
//	public List<BookAppointment> getByDoctorId(Long id) {
//		return repo.findbyDoctorId(id);
//	}


//	@Override
//	public List<BookAppointment> getByPatientId(Long id) {
//		return repo.findbyPatientId(id);
//	}


    @Override
	public BookAppointment updateAppointmentWithSlot(int slotId, BookAppointment updated) {
//    	AvailabilitySlotDTO slot = docService.viewAvailableSlot(slotId);
    	AvailabilitySlotDTO slot = docService.viewAvailableSlot(slotId);
    	if(slot.isStatus()) {
    		return null;
    	}
    	PatientDTO patient =authService.getPatientById(updated.getPatientId());
    	docService.markSlotBooked(slotId);
    	updated.setDate(slot.getDate());
    	updated.setDoctorId(slot.getDoctorId());
    	updated.setDoctorName(slot.getDoctor().getName());
    	updated.setStartTime(slot.getStartTime());
    	updated.setEndTime(slot.getEndTime());
    	updated.setStatus("Booked");
    	updated.setDoctor(slot.getDoctor());
    	updated.setPatient(patient);
    	
    	
    	return repo.save(updated);
    }

	@Override
	public List<BookAppointment> getAllAppointments() {
		List<BookAppointment> appointments = repo.findAll();
		for (BookAppointment appointment : appointments) {
		    DoctorDTO doctor = authService.getDoctorById(appointment.getDoctorId());
		    appointment.setDoctor(doctor);
		    appointment.setDoctorName(doctor.getName());

		    PatientDTO patient = authService.getPatientById(appointment.getPatientId());
		    appointment.setPatient(patient);
		    appointment.setPatientName(patient.getPatientName());
		}
		return appointments;
	}


	@Override
	public BookAppointment cancelAppointmentByDoctor(long appointmentId) {
		BookAppointment app=repo.findById(appointmentId).orElse(null);
		app.setStatus("Cancel by Doctor");
		repo.save(app);
	    
		boolean release=docService.cancelSlot(appointmentId);
		if(!release) {
			throw new RuntimeException("Failed to cancel the appointment");
		}
		return app;
	}


	@Override
	public BookAppointment cancelAppointmentByPatient(long appointmentId) {
		BookAppointment app=repo.findById(appointmentId).orElse(null);
		app.setStatus("Cancel by Patient");
		repo.save(app);
	    
		boolean release=docService.cancelSlot(appointmentId);
		if(!release) {
			throw new RuntimeException("Failed to cancel the appointment");
		}
		return app;
		
	}


	@Override
	public List<DoctorDTO> getAllDoctors() {
		return authService.getAllDoctors();
	}


//	@Override
//	public List<BookAppointment> getByPatientId(int id) {
//		// TODO Auto-generated method stub
//		return null;
//	}



}
