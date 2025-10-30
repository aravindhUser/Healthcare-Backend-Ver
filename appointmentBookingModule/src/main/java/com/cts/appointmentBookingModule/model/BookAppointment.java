package com.cts.appointmentBookingModule.model;

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
@Table(name="bookappointments")
public class BookAppointment {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private int patientId;
	private int doctorId;
	private String patientName;
	private String doctorName;
	private LocalDate date;
    private LocalTime startTime;
	private LocalTime endTime;
	private String problem;
	private String status;
	
	@Transient
	DoctorDTO doctor;
	
	@Transient
	PatientDTO patient;

}
