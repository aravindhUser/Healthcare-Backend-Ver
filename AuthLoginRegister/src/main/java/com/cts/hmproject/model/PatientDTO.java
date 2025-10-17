package com.cts.hmproject.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {
	
	private int patientId;
	private String patientName;
	private LocalDate dob;
	private String gender;
	private String bloodgroup;
	
	public PatientDTO(Patient p) {
		this.patientId=p.getPatientId();
		this.patientName=p.getPatientName();
		this.dob=p.getDob();
		this.gender=p.getGender();
		this.bloodgroup=p.getBloodgroup();
	}

}
