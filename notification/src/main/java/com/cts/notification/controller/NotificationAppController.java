package com.cts.notification.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.notification.model.Notification;
import com.cts.notification.service.NotificationService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/notification")
@AllArgsConstructor
public class NotificationAppController {
	
	NotificationService notificationService;
	
	@GetMapping("/patient/{patientId}")
	public List<Notification> getNotificationsByPatient(@PathVariable int patientId) {
		return notificationService.getNotificationsForPatient(patientId);
	}
	@GetMapping("/doctor/{doctorId}")
	public List<Notification> getNotificationsByDoctor(@PathVariable int doctorId) {
		return notificationService.getNotificationsForDoctor(doctorId);
	}
	@PostMapping("/appointment-booked/{appointmentId}")
	public Notification appointmentBooked(@RequestBody Notification n,@PathVariable int appointmentId) {
		Notification data = notificationService.notifyBooked(n);
		return data;
	}	
	@PostMapping("/patient/appointment-cancelled/{appointmentId}")
	public Notification appointmentCancelledByPatient(@RequestBody Notification n){
		Notification data = notificationService.notifyCancelledByPatient(n);
		return data;
	}
	@PostMapping("/doctor/appointment-cancelled/{appointmentId}")
	public Notification appointmentCancelledByDoctor(@RequestBody Notification n){
		Notification data = notificationService.notifyCancelledByDoctor(n);
		return data;
	}
}
