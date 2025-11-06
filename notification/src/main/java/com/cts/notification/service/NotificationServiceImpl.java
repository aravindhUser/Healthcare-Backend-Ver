package com.cts.notification.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cts.notification.exception.NotificationNotFoundException;
import com.cts.notification.exception.NotificationProcessingException;
//import com.cts.notification.exception.NotificationNotFoundException;
//import com.cts.notification.exception.NotificationProcessingException;
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
	    try {
	        String msg = "Mr/Mrs." + n.getPatientName() + "'s appointment successfully booked with Dr." + n.getDoctorName()
	                + " on " + n.getDate() + " at " + n.getStartTime() + " is booked.";
	        n.setTimestamp(LocalDateTime.now());
	        n.setMessage(msg);
	        Notification saved = notificationRepo.save(n);
	        log.info("Notification created for booking: Doctor={}, Patient={}", n.getDoctorName(), n.getPatientName());
	        return saved;
	    } catch (Exception e) {
	        log.error("Error while booking notification", e);
	        throw new NotificationProcessingException("Failed to create booking notification", e);
	    }
	}
	@Override
	public Notification notifyCancelledByPatient(Notification n) {
	    try {
	        String msg = "Mr/Mrs." + n.getPatientName() + "'s appointment with Dr." + n.getDoctorName()
	                + " on " + n.getDate() + " at " + n.getStartTime() + " is cancelled by Patient.";
	        n.setTimestamp(LocalDateTime.now());
	        n.setMessage(msg);
	        Notification saved = notificationRepo.save(n);
	        log.info("Notification created for cancellation by patient: Doctor={}, Patient={}", n.getDoctorName(), n.getPatientName());
	        return saved;
	    } catch (Exception e) {
	        log.error("Error while creating cancellation notification by patient", e);
	        throw new NotificationProcessingException("Failed to create cancellation notification by patient", e);
	    }
	}
	@Override
	public Notification notifyCancelledByDoctor(Notification n) {
	    try {
	        String msg = "Mr/Mrs." + n.getPatientName() + "'s appointment with Dr." + n.getDoctorName()
	                + " on " + n.getDate() + " at " + n.getStartTime() + " is cancelled by Doctor.";
	        n.setTimestamp(LocalDateTime.now());
	        n.setMessage(msg);
	        Notification saved = notificationRepo.save(n);
	        log.info("Notification created for cancellation by doctor: Doctor={}, Patient={}", n.getDoctorName(), n.getPatientName());
	        return saved;
	    } catch (Exception e) {
	        log.error("Error while creating cancellation notification by doctor", e);
	        throw new NotificationProcessingException("Failed to create cancellation notification by doctor", e);
	    }
	}
	@Override
	public List<Notification> getNotificationsForPatient(int patientId) {
	    try {
	        List<Notification> notifications = notificationRepo.findByPatientIdOrderByTimestampDesc(patientId);
	        if (notifications.isEmpty()) {
	            throw new NotificationNotFoundException("No notifications found for patient ID: " + patientId);
	        }
	        return notifications;
	    } catch (NotificationNotFoundException e) {
	        throw e;
	    } catch (Exception e) {
	        log.error("Error retrieving notifications for patient {}", patientId, e);
	        throw new NotificationProcessingException("Failed to retrieve notifications for patient", e);
	    }
	}
	@Override
	public List<Notification> getNotificationsForDoctor(int doctorId) {
	    try {
	        List<Notification> notifications = notificationRepo.findByDoctorIdOrderByTimestampDesc(doctorId);
	        if (notifications.isEmpty()) {
	            throw new NotificationNotFoundException("No notifications found for doctor ID: " + doctorId);
	        }
	        log.info("Retrieved {} notifications for doctor ID: {}", notifications.size(), doctorId);
	        return notifications;
	    } catch (NotificationNotFoundException e) {
	        throw e;
	    } catch (Exception e) {
	        log.error("Error retrieving notifications for doctor ID: {}", doctorId, e);
	        throw new NotificationProcessingException("Failed to retrieve notifications for doctor", e);
	    }
	}
}
