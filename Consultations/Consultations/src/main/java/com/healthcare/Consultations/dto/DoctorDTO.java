package com.healthcare.Consultations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO {
	private int doctorId;
	private String doctorName;
	private String specialization;
	private String qualification;
	private int experience;
	

	
}
