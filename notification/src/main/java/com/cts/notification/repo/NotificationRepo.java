
package com.cts.notification.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.notification.model.Notification;

import java.util.List;

public interface NotificationRepo extends JpaRepository<Notification, Integer> {
	List<Notification> findByPatientIdOrderByTimestampDesc(int patientId);
	List<Notification> findByDoctorIdOrderByTimestampDesc(int doctorId);
}
