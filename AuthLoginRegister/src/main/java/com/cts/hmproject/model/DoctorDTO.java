package com.cts.hmproject.model;

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
	
	public DoctorDTO(Doctor doctor) {
		this.id=doctor.getDoctorId();
		this.name=doctor.getDoctorName();
		this.specialization=doctor.getSpecialization();
		this.qualification=doctor.getSpecialization();
		this.experience=doctor.getExperience();
	}
	
}
