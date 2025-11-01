package com.cts.notification.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cts.notification.model.Notification;
import com.cts.notification.repo.NotificationRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {
	
	
	private final NotificationRepo notificationRepo;

	@Override
	public Notification notifyBooked(Notification n) {
		String msg = "Mr/Mrs."+n.getPatientName()+"'s appointment successfully booked with with Dr." + n.getDoctorName() +" on " + n.getDate() +" at " + n.getStartTime() + " is booked.";
		Notification data = n;
		
		data.setTimestamp(LocalDateTime.now());
		data.setMessage(msg);
//		Notification data = new Notification(n.getId(),n.getPatientId(),n.getDoctorId(),n.getAppointmentId(),msg,n.getDate(),n.getStartTime(),LocalDateTime.now(),n.getDoctorName(),n.getPatientName());
		notificationRepo.save(data);
		return data;
	}
	@Override
	public Notification notifyCancelledByPatient(Notification n) {
		String msg = "Mr/Mrs."+n.getPatientName()+"'s appointment with " + n.getDoctorName() +" on " + n.getDate() +" at " + n.getStartTime() + " is cancelled by Patient.";
		Notification data = n;
		data.setTimestamp(LocalDateTime.now());
		data.setMessage(msg);
//		Notification data = new Notification(n.getId(),n.getPatientId(),n.getDoctorId(),n.getAppointmentId(),msg,n.getDate(),n.getStartTime(),LocalDateTime.now(),n.getDoctorName(),n.getPatientName());
		notificationRepo.save(data);
		return data;
	}
	@Override
	public Notification notifyCancelledByDoctor(Notification n) {
		
		String msg = "Mr/Mrs."+n.getPatientName()+"'s appointment with " + n.getDoctorName() +" on " + n.getDate() +" at " + n.getStartTime() + " is cancelled by Doctor.";
		Notification data = n;
		data.setTimestamp(LocalDateTime.now());
		data.setMessage(msg);
//		Notification data = new Notification(n.getId(),n.getPatientId(),n.getDoctorId(),n.getAppointmentId(),msg,n.getDate(),n.getStartTime(),LocalDateTime.now(),n.getDoctorName(),n.getPatientName());
		notificationRepo.save(data);
		return data;
	}
	@Override
	public List<Notification> getNotificationsForPatient(int patientId) {
		return notificationRepo.findByPatientIdOrderByTimestampDesc(patientId);
	}
	@Override
	public List<Notification> getNotificationsForDoctor(int doctorId) {
		return notificationRepo.findByDoctorIdOrderByTimestampDesc(doctorId);
	}

}
