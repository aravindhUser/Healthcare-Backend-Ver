package com.cts.appointmentBookingModule.model;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {
	
	private int id;
	
	private int patId;
	private String patientName;
	private int docId;
	private int slotId;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	private String status;
	
	public AppointmentDTO(BookAppointment ap){
		this.id=ap.getId();
		this.docId=ap.getDoctorId();
		this.patId=ap.getPatientId();
		this.patientName=ap.getPatientName();
		this.slotId=ap.getSlotId();
		this.date=ap.getDate();
		this.startTime = ap.getStartTime();
		this.endTime=ap.getEndTime();
		this.status=ap.getStatus();	
	}
	
	
}
