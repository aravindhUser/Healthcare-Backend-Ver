package com.cts.appointmentBookingModule.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.appointmentBookingModule.Service.BookAppointmentService;
import com.cts.appointmentBookingModule.model.AppointmentDTO;
import com.cts.appointmentBookingModule.model.BookAppointment;
import com.cts.appointmentBookingModule.model.DoctorDTO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@RestController
//@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/appointments")
@AllArgsConstructor
@Slf4j
public class BookAppointmentController {
//	 @Autowired
     BookAppointmentService service;
	   
	   @PostMapping("/book/{slotId}")
	    public BookAppointment bookAppointmentBySlot(@PathVariable int slotId, @RequestBody BookAppointment appointment) {
		   log.info("API Call:  Booking Appointment by SlotId");
	        return service.bookAppointmentBySlot(slotId, appointment);
	    }
	  
	   
	   @GetMapping("/all")
	   public List<BookAppointment>getAllAppointments(){
		   log.info("API Call: Getting all Appointments");
		   return service.getAllAppointments();
	   }
	   
	   @GetMapping("{patientId}")
	   public List<BookAppointment> getAppointmentByPatient(@PathVariable int patientId)
	   {
		   log.info("API Call: Getting Appointment by Patientid");
		   return service.getAppByPatientId(patientId);
	   }
	   
	   
	   @GetMapping("Doctor/{id}")
		public List<DoctorDTO> getAllDoctors(){
		   log.info("API Call: Getting all available doctors");
			return service.getAllDoctors();	
		}
		
//		@GetMapping("Patient/{id}")
//		public List<BookAppointment> getAllPatientAppointment(@PathVariable Long id){
//			return service.getByPatientId(id);
//		}
//	   
	   
	   
	   @PutMapping("/update/{aptId}")
	   public BookAppointment updateAppointment(@PathVariable int aptId) {
		   log.info("API Call: Rescheduling appointment");
		   return service.updateAppointmentWithSlot(aptId);
	   }
	   
	   @PutMapping("/cancel/doctor/{appointmentId}") 
	   public AppointmentDTO cancelByDoctor(@PathVariable long appointmentId) { 
		   log.info("API Call: Appointment cancel by doctor");
		   return service.cancelAppointmentByDoctor(appointmentId); 
		   }  
	   
	   @PutMapping("/cancel/patient/{appointmentId}") 
	   public BookAppointment cancelByPatient(@PathVariable long appointmentId) { 
		   log.info("Inside Cancel by patient controller");
		   return service.cancelAppointmentByPatient(appointmentId); 
	   }
	   
	   @GetMapping("/doctor/fetch/{docId}")
	   public List<AppointmentDTO> getAppointmentsDoctor(@PathVariable("docId") int docId){
		   log.info("API Call: Fetch all appointments for specific doctor");
		   return service.getAppointmentsDoctor(docId);
	   }
	   
	   @GetMapping("doctor/get/{aptId}")
	   public AppointmentDTO fetchAppointmentForDoctor(@PathVariable("aptId") int aptId) {
		   log.info("API Call: Fetches a specific appointment by appointmentId");
		   return service.fetchByDoctor(aptId);
	   }
	   
	   @PostMapping("doctor/completed/{apptId}")
	   public BookAppointment completedStatusUpdate(@PathVariable("apptId") int apptId)
	   {
		   log.info("API Call: Completed status updating");
		   return service.completedStatus(apptId);
	   }
	   
	   
}




