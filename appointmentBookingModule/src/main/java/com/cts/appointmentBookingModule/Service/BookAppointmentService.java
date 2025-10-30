package com.cts.appointmentBookingModule.Service;

import java.util.List;


import com.cts.appointmentBookingModule.model.BookAppointment;
import com.cts.appointmentBookingModule.model.DoctorDTO;


public interface BookAppointmentService {
	List<BookAppointment> getAllAppointments();
	BookAppointment updateAppointmentWithSlot(int slotId,BookAppointment updateAppointment);
	BookAppointment bookAppointmentBySlot(int slotId, BookAppointment appointment);
//	List<BookAppointment> getByDoctorId(Long id);
//	List<BookAppointment> getByPatientId(int id);
    BookAppointment cancelAppointmentByDoctor(long appointmentId);
    BookAppointment cancelAppointmentByPatient(long appointmentId);
	List<DoctorDTO> getAllDoctors();
	List<BookAppointment> getAppByPatientId(int patientId);

}
