package com.cts.notification.service;

import java.util.List;

import com.cts.notification.model.Notification;

public interface NotificationService {
	public Notification notifyBooked(Notification n);
	public Notification notifyCancelledByPatient(Notification n);
	public Notification notifyCancelledByDoctor(Notification n);
	public List<Notification> getNotificationsForPatient(int patientId);
	public List<Notification> getNotificationsForDoctor(int doctorId);
	
}