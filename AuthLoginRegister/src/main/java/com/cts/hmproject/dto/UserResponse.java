package com.cts.hmproject.dto;

import com.cts.hmproject.model.Doctor;
import com.cts.hmproject.model.Patient;

import lombok.Data;

@Data
public class UserResponse {
	private int id;
	private String name;
	
	public UserResponse(Doctor d)
	{
		this.id = d.getDoctorId();
		this.name = d.getDoctorName();
	}
	
	public UserResponse(Patient p) {
		this.id = p.getPatientId();
		this.name = p.getPatientName();
	}
}
