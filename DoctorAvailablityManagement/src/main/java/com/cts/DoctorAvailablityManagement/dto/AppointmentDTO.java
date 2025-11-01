package com.cts.DoctorAvailablityManagement.dto;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {
	private int id;
	
	private int patId;
	private int docId;
	private int slotId;
	private String patientName;
	
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	
	private String status;


}
