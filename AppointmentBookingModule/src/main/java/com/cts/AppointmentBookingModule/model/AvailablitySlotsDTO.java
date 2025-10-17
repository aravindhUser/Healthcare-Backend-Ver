package com.cts.AppointmentBookingModule.model;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailablitySlotsDTO {
	
	private long id;
	private long doctorId;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	
	private DoctorDTO doctor;

}
