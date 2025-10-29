package com.cts.DoctorAvailablityManagement.service;

import java.time.LocalDate;
import java.util.List;

import com.cts.DoctorAvailablityManagement.model.AppointmentDTO;
import com.cts.DoctorAvailablityManagement.model.AvailablitySlot;
import com.cts.DoctorAvailablityManagement.model.DoctorDTO;

public interface DoctorAppointmentService {
	DoctorDTO getDoctor(int id);
	List<AvailablitySlot> getAvailablity(int doctorId);
	AvailablitySlot addAvailablity(int doctorId,AvailablitySlot slot);
	void deleteAvailablity(int slotId);
	List<DoctorDTO> returnDoctors();
	
	
	List<AppointmentDTO> getAppointment(int doctorId);
	AppointmentDTO deleteByDoctor(int aptId);
	public List<AvailablitySlot> getSlotsbyDate(int doctorId,LocalDate date);
	
	List<AvailablitySlot> viewAllAvailablity();
	boolean bookAvailablity(int slotId);
	boolean cancelBookedSlot(int slotId);
	
	

}
