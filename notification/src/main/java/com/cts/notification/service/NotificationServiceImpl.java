package com.cts.notification.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cts.notification.model.Notification;
import com.cts.notification.repo.NotificationRepo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
	
	
	NotificationRepo notificationRepo;

	@Override
	public Notification notifyBooked(Notification n) {
		String msg = "Mr/Mrs."+n.getPatientName()+"'s appointment successfully booked with Dr." + n.getDoctorName() +" on " + n.getDate() +" at " + n.getStartTime() + " is booked.";
		Notification data = n;
		data.setTimestamp(LocalDateTime.now());
		data.setMessage(msg);
		notificationRepo.save(data);
		log.info("Creating Notification for Doctor :{} and Patient :{} for booking", n.getDoctorName(),n.getPatientName());
		return data;
	}
	@Override
	public Notification notifyCancelledByPatient(Notification n) {
		String msg = "Mr/Mrs."+n.getPatientName()+"'s appointment with " + n.getDoctorName() +" on " + n.getDate() +" at " + n.getStartTime() + " is cancelled by Patient.";
		Notification data = n;
		data.setTimestamp(LocalDateTime.now());
		data.setMessage(msg);
		notificationRepo.save(data);
		log.info("Creating Notification for Doctor :{} and Patient :{} for Cancelled by Patient", n.getDoctorName(),n.getPatientName());
		return data;
	}
	@Override
	public Notification notifyCancelledByDoctor(Notification n) {
		
		String msg = "Mr/Mrs."+n.getPatientName()+"'s appointment with " + n.getDoctorName() +" on " + n.getDate() +" at " + n.getStartTime() + " is cancelled by Doctor.";
		Notification data = n;
		data.setTimestamp(LocalDateTime.now());
		data.setMessage(msg);
		notificationRepo.save(data);
		log.info("Creating Notification for Doctor :{} and Patient :{} for Cancelled by Doctor", n.getDoctorName(),n.getPatientName());
		return data;
	}
	@Override
	public List<Notification> getNotificationsForPatient(int patientId) {
		log.info("Listing all Patient's Notification for Patient:{}",patientId);
		return notificationRepo.findByPatientIdOrderByTimestampDesc(patientId);
	}
	@Override
	public List<Notification> getNotificationsForDoctor(int doctorId) {
		log.info("Listing all Doctor's Notification for Doctor:{}",doctorId);
		return notificationRepo.findByDoctorIdOrderByTimestampDesc(doctorId);
	}

}
