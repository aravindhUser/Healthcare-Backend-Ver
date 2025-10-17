package com.cts.AppointmentBookingModule.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DoctorDTO {
	private int id;
	private String name;
	private String specialization;
	private String qualification;
	private int experience;
	
	

}
