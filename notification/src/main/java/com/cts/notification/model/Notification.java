package com.cts.notification.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int patientId;
	private int doctorId;
	private int appointmentId;
	private String message;
	private LocalDate date;
	private LocalTime startTime;
	private LocalDateTime timestamp;
	
	@Transient
	private String doctorName;
	
	@Transient
	private String patientName;
}
