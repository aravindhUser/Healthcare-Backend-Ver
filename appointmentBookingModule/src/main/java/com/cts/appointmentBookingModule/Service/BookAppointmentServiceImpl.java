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
	    BookAppointment existingAppointment = repo.findByDoctorDateTime(
	            appointment.getDoctorId(),
	            appointment.getDate(),
	            appointment.getStartTime()
	        );

	        if (existingAppointment != null) {
	            throw new RuntimeException("Slot already booked for this doctor and time.");
	        }
	    
		boolean bookslot=docService.markSlotBooked(slotId);
	    if(!bookslot)
	    	throw new RuntimeException("Slot Already Booked");
	    appointment.setStatus("booked");
	    System.out.println(appointment);
	    DoctorDTO doctor = authService.getDoctorById(appointment.getDoctorId());
	  
	    PatientDTO patient = authService.getPatientById(appointment.getPatientId());
	    appointment.setDoctor(doctor);
	    appointment.setPatient(patient);
		appointment.setSlotId(slotId);
	    BookAppointment app = repo.save(appointment);
	    NotificationDTO noti = setNotification(app);
	    try {
	    	notiClient.appointmentBooked(noti);	
	    }catch(Exception e){
	    	System.out.println("Failed to send Notification");
	    }
	    
	   
	    
	    return repo.save(appointment); 
	      
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
	    System.out.println("Notification created ");
	    System.out.println(notification);
	    
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
	public BookAppointment updateAppointmentWithSlot(int aptId) {
    	Optional<BookAppointment> updatedAppointment = repo.findById((long)aptId);
    	if(updatedAppointment.isPresent()) {
    		BookAppointment found = updatedAppointment.get();
    		found.setStatus("Rescheduled");
    		docService.cancelSlot(found.getSlotId());
    		return repo.save(found);
	
    	}
    	return null;
    	 
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
	    Optional<BookAppointment> optionalAppointment = repo.findById(appointmentId);
	    if (optionalAppointment.isPresent()) {
	        BookAppointment appointment = optionalAppointment.get();
	        appointment.setStatus("Cancel By Doctor");
	        repo.save(appointment);

	        NotificationDTO noti = setNotification(appointment);
	        try {
	            notiClient.appointmentCancelledByDoctor(noti);
	        } catch (Exception e) {
	            System.out.println("Failed to send Cancel Notification: " + e.getMessage());
	        }

	        return new AppointmentDTO(appointment);
	    } else {
	        return null;
	    }
	}



	@Override
	public BookAppointment cancelAppointmentByPatient(long appointmentId) {
	    System.out.println("Inside Cancel by patient service");
	    Optional<BookAppointment> optionalAppointment = repo.findById(appointmentId);
	    if (optionalAppointment.isPresent()) {
	        BookAppointment appointment = optionalAppointment.get();
	        appointment.setStatus("Cancel By Patient");
	        repo.save(appointment);

	        boolean release = docService.cancelSlot(appointment.getSlotId());
	        if (!release) {
	            throw new RuntimeException("Failed to cancel the appointment");
	        }

	        NotificationDTO noti = setNotification(appointment);
	        try {
	            notiClient.appointmentCancelledByPatient(noti);
	        } catch (Exception e) {
	            System.out.println("Failed to send Cancel Notification: " + e.getMessage());
	        }

	        return appointment;
	    } else {
	        return null;
	    }
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

	@Override
	public BookAppointment completedStatus(int apptId) {

	    Optional<BookAppointment> optionalAppointment = repo.findById((long)apptId);
	    if (optionalAppointment.isPresent()) {
	    	BookAppointment appointment = optionalAppointment.get();
	        appointment.setStatus("COMPLETED");
	        repo.save(appointment);
	       return appointment;
	    } else {
	        return null;
	    }

	}


}
