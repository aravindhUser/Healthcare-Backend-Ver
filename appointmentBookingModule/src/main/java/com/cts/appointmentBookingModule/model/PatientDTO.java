package com.cts.appointmentBookingModule.model;

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
	


}
