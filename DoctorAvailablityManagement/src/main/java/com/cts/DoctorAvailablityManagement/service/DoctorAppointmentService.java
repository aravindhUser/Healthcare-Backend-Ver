package com.cts.DoctorAvailablityManagement.service;

import java.util.List;
import java.util.Optional;

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
	
	List<AvailablitySlot> viewAllAvailablity();
	boolean bookAvailablity(int slotId);
	boolean cancelBookedSlot(int slotId);
	AvailablitySlot viewSlot(int slotId);
	
	

}
