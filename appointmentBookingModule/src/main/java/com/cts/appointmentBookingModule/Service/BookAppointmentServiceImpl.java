package com.cts.appointmentBookingModule.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.appointmentBookingModule.Repository.BookAppointmentRepository;
import com.cts.appointmentBookingModule.model.AppointmentDTO;
import com.cts.appointmentBookingModule.model.AvailabilitySlotDTO;
import com.cts.appointmentBookingModule.model.BookAppointment;
import com.cts.appointmentBookingModule.model.DoctorDTO;
import com.cts.appointmentBookingModule.model.NotificationDTO;
import com.cts.appointmentBookingModule.model.PatientDTO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookAppointmentServiceImpl implements BookAppointmentService {
	@Autowired
	BookAppointmentRepository repo;
	
	AuthenticationService authService;
	DoctorAvailabilityClient docService;
	NotificationClient notiClient;
//	@Autowired
//	AuthenticationService authService;
//	
//	@Autowired
//	DoctorAvailabilityClient docService;
	
	@Override
	public BookAppointment bookAppointmentBySlot(int slotId, BookAppointment appointment) {
	    System.out.println("Called the Booking");
		boolean bookslot=docService.markSlotBooked(slotId);
	    if(!bookslot)
	    	throw new RuntimeException("Slot Already Booked");
	    appointment.setStatus("booked");
	    System.out.println(appointment);
	    DoctorDTO doctor = authService.getDoctorById(appointment.getDoctorId());
	  
	    PatientDTO patient = authService.getPatientById(appointment.getPatientId());
	    appointment.setDoctor(doctor);
	    appointment.setPatient(patient);
<<<<<<< HEAD
	    BookAppointment app = repo.save(appointment);
	    NotificationDTO noti = setNotification(app);
	    notiClient.appointmentBooked(noti);
	    return app;
=======
	    appointment.setSlotId(slotId);
	    return repo.save(appointment); 
>>>>>>> 52cb8a455fb3f1493910f618900975f9f7affc4f
	      
	}
	
	@Override
	public NotificationDTO setNotification(BookAppointment app) {
		
	    NotificationDTO notification = new NotificationDTO();
	    notification.setAppointmentId(app.getId());
	    notification.setDate(app.getDate());
	    notification.setDoctorId(app.getDoctorId());
	    notification.setDoctorName(app.getDoctorName());
	    notification.setPatientId(app.getPatientId());
	    notification.setPatientName(app.getPatientName());
	    notification.setStartTime(app.getStartTime());
	    
	    return notification;
		
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
	public AppointmentDTO cancelAppointmentByDoctor(long appointmentId) {
		BookAppointment app=repo.findById(appointmentId).orElse(null);
		app.setStatus("Cancel by Doctor");
		repo.save(app);
<<<<<<< HEAD
	    
		boolean release=docService.cancelSlot(appointmentId);
		if(!release) {
			throw new RuntimeException("Failed to cancel the appointment");
		}
		NotificationDTO noti = setNotification(app);
	    notiClient.appointmentCancelledByDoctor(noti);
		return app;
=======
		AppointmentDTO found = new AppointmentDTO(app);
		return found;
>>>>>>> 52cb8a455fb3f1493910f618900975f9f7affc4f
	}


	@Override
	public BookAppointment cancelAppointmentByPatient(long appointmentId) {
		System.out.println("Inside Cancel by patient service");
		BookAppointment app=repo.findById(appointmentId).orElse(null);
		app.setStatus("Cancel by Patient");
		repo.save(app);
	    
//		boolean release=docService.cancelSlot(appointmentId);
//		if(!release) {
//			throw new RuntimeException("Failed to cancel the appointment");
//		}
		NotificationDTO noti = setNotification(app);
	    notiClient.appointmentCancelledByPatient(noti);
		return app;
		
	}


	@Override
	public List<DoctorDTO> getAllDoctors() {
		return authService.getAllDoctors();
	}


	@Override
	public List<BookAppointment> getAppByPatientId(int patientId) {
		// TODO Auto-generated method stub
		return repo.findByPatientId(patientId);
	}


	@Override
	public List<AppointmentDTO> getAppointmentsDoctor(int doctorId) {
		List<BookAppointment> found = repo.findByDoctorId(doctorId);
		System.out.println(found);
		List<AppointmentDTO> docSlots = new ArrayList<>();
		
		for(BookAppointment ap : found) {
			AppointmentDTO slot = new AppointmentDTO(ap);
			
			docSlots.add(slot);
		}
		System.out.println(docSlots);
		return docSlots;	
	}


	@Override
	public AppointmentDTO fetchByDoctor(int apptId) {
		Optional<BookAppointment> found = repo.findById((long)apptId);
		if(found==null) {
			throw new RuntimeException("No Appointment Found");
		}
		BookAppointment getFound=found.get();
		AppointmentDTO ap = new AppointmentDTO(getFound);
		return ap;
		
	}


}
