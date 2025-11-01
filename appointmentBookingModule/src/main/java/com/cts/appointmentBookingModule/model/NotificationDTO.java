package com.cts.appointmentBookingModule.model;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
	private int patientId;
	private int doctorId;
	private int appointmentId;
	private LocalDate date;
	private LocalTime startTime;
	private String doctorName;
	private String patientName;
}
