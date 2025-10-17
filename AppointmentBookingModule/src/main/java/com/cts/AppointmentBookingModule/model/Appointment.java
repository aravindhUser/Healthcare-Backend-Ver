package com.cts.AppointmentBookingModule.model;

//import java.beans.Transient;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Appointments")
public class Appointment {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
