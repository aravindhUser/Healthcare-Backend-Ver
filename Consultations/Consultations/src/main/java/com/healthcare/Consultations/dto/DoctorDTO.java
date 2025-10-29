package com.healthcare.Consultations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO {
	private int DoctorId;
	private String DoctorName;
	private String specialization;
	private String qualification;
	private int experience;
	

	
}
