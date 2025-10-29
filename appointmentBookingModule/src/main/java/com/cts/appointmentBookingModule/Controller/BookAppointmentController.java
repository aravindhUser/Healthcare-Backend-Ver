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
import com.cts.appointmentBookingModule.model.BookAppointment;
import com.cts.appointmentBookingModule.model.DoctorDTO;

import lombok.AllArgsConstructor;



@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/appointments")
@AllArgsConstructor
public class BookAppointmentController {
//	 @Autowired
     BookAppointmentService service;
	   
	   @PostMapping("/book/{slotId}")
	    public BookAppointment bookAppointmentBySlot(@PathVariable int slotId, @RequestBody BookAppointment appointment) {
	        return service.bookAppointmentBySlot(slotId, appointment);
	    }
	  
	   
	   @GetMapping("/all")
	   public List<BookAppointment>getAllAppointments(){
		   return service.getAllAppointments();
	   }
	   
	   @GetMapping("{patientId}")
	   public List<BookAppointment> getAppointmentByPatient(@PathVariable int patientId)
	   {
		   return service.getAppByPatientId(patientId);
	   }
	   
	   
	   @GetMapping("Doctor/{id}")
		public List<DoctorDTO> getAllDoctors(){
			return service.getAllDoctors();	
		}
		
//		@GetMapping("Patient/{id}")
//		public List<BookAppointment> getAllPatientAppointment(@PathVariable Long id){
//			return service.getByPatientId(id);
//		}
//	   
	   
	   
	   @PutMapping("/update/{slotId}")
	   public BookAppointment updateAppointment(@PathVariable int slotId,@RequestBody BookAppointment appointment) {
		   return service.updateAppointmentWithSlot(slotId,appointment);
	   }
	   
	   @PatchMapping("/cancel/doctor/{appointmentId}") 
	   public BookAppointment cancelByDoctor(@PathVariable long appointmentId) { 
		   return service.cancelAppointmentByDoctor(appointmentId); 
		   }  
	   
	   @PatchMapping("/cancel/patient/{appointmentId}") 
	   public BookAppointment cancelByPatient(@PathVariable long appointmentId) { 
		   System.out.println("Inside Cancel by patient controller");
		   return service.cancelAppointmentByPatient(appointmentId); 
	   }
	   
	   
}




