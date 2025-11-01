package com.cts.hmproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Doctor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int doctorId;
	
	private int userId;
	@Column(name="doctor_name")
	private String doctorName;
	private String doctorEmail;
	private String doctorMobileNumber;
//	private String doctorPassword;
	
	private String specialization;
	private String qualification;
	private int experience;
	private String address;
	private String about;
}
