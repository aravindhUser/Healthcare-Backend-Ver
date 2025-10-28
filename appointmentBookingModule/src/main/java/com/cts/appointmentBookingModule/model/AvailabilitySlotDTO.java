package com.cts.appointmentBookingModule.model;

import java.time.LocalDate;
import java.time.LocalTime;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AvailabilitySlotDTO {
	private int id;
	private int doctorId;
	private String doctorName;  //need to add in project
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	private boolean status;
	
	private DoctorDTO doctor;
	
	public boolean isStatus() {
		return status;
	}

}
