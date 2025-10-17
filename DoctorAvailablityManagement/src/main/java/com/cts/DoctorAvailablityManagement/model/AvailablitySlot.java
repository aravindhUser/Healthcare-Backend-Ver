package com.cts.DoctorAvailablityManagement.model;

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
@Table(name="availablity_slot")
public class AvailablitySlot {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int doctorId;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	private boolean status;
	
	@Transient
	private DoctorDTO doctor;

}
