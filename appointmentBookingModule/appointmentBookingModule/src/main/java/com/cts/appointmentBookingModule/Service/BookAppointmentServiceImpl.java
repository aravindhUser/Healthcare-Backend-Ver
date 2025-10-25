package com.cts.appointmentBookingModule.Service;

import java.util.List;

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
	public BookAppointment updateAppointmentWithSlot(int slotId, BookAppointment updateAppointment) {
    	List<AvailabilitySlotDTO> slots=docService. getSlotsByDoctor(slotId);
    	if (slots == null) {
            throw new RuntimeException("Slot not found for ID: " + slotId);
        }

    	AvailabilitySlotDTO slot = null;

    // Loop through the list to find the matching slot
    for (AvailabilitySlotDTO s : slots) {
        if (s.getId() == slotId) {
            slot = s;
            break;
        }
    }

    if (slot == null) {
        throw new RuntimeException("Slot not found for ID: " + slotId);
    }

    boolean booked = docService.markSlotBooked(slotId);
    if (!booked) {
        throw new RuntimeException("Slot already booked or unavailable");
    }
    

//    	BookAppointment existingAppointment = repo.findById((long) updateAppointment.getId()).orElseThrow(() -> new RuntimeException("Appointment not found with ID: " + updateAppointment.getId()));


    	BookAppointment existingAppointment = repo.findById((long) updateAppointment.getId())
        .orElseThrow(() -> new RuntimeException("Appointment not found with ID: " + updateAppointment.getId()));

    	existingAppointment.setDoctorName(((AvailabilitySlotDTO) slot).getDoctorName());
    	existingAppointment.setDate(((AvailabilitySlotDTO) slot).getDate());
    	existingAppointment.setStartTime(((AvailabilitySlotDTO) slot).getStartTime());
    	existingAppointment.setEndTime(((AvailabilitySlotDTO) slot).getEndTime());
    	existingAppointment.setStatus("Booked");
    	
		return repo.save(existingAppointment);

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
