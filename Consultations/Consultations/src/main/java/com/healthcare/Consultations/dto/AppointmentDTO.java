package com.healthcare.Consultations.dto;

//import java.beans.Transient;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {

	private int id;
	
	private int patId;
	private int docId;
	private int slotId;
	
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	
	private String status;
	
	@Transient
	DoctorDTO doctor;
	@Transient
	PatientDTO patient;
	
	
	
}
