package com.cts.appointmentBookingModule.Service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cts.appointmentBookingModule.model.NotificationDTO;


@FeignClient(name = "notification")
public interface NotificationClient {
	
	@PostMapping("/appointment-booked")
	public NotificationDTO appointmentBooked(@RequestBody NotificationDTO n);
	
	@PostMapping("/patient/appointment-cancelled")
	public NotificationDTO appointmentCancelledByPatient(@RequestBody NotificationDTO n);
	
	@PostMapping("/doctor/appointment-cancelled")
	public NotificationDTO appointmentCancelledByDoctor(@RequestBody NotificationDTO n);

}
