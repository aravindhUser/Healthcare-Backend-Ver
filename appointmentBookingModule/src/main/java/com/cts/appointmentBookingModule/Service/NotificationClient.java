package com.cts.appointmentBookingModule.Service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cts.appointmentBookingModule.config.FeignClientConfig;
import com.cts.appointmentBookingModule.model.NotificationDTO;


@FeignClient(name = "NOTIFICATION",configuration = FeignClientConfig.class)
public interface NotificationClient {
	
	@PostMapping("notification/appointment-booked")
	public NotificationDTO appointmentBooked(@RequestBody NotificationDTO n);
	
	@PostMapping("notification/patient/appointment-cancelled")
	public NotificationDTO appointmentCancelledByPatient(@RequestBody NotificationDTO n);
	
	@PostMapping("notification/doctor/appointment-cancelled")
	public NotificationDTO appointmentCancelledByDoctor(@RequestBody NotificationDTO n);

}
