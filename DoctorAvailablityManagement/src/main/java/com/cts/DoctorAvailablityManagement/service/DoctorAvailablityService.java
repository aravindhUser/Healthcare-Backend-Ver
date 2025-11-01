package com.cts.DoctorAvailablityManagement.service;

import java.time.LocalDate;
import java.util.List;

import com.cts.DoctorAvailablityManagement.dto.AppointmentDTO;
import com.cts.DoctorAvailablityManagement.dto.AvailabilitySlotsDTO;
import com.cts.DoctorAvailablityManagement.dto.DoctorDTO;
import com.cts.DoctorAvailablityManagement.dto.DoctorSlotsDTO;
import com.cts.DoctorAvailablityManagement.model.AvailablitySlot;
import com.cts.DoctorAvailablityManagement.model.DoctorSlots;

public interface DoctorAvailablityService {
	DoctorDTO getDoctor(int id);
	List<DoctorSlotsDTO> getAvailablity(int doctorId);
	DoctorSlots addAvailablity(DoctorSlots slot);
	String deleteAvailablity(int slotId);
	List<DoctorDTO> returnDoctors();
	
	
	List<AppointmentDTO> getAppointment(int doctorId);
	AppointmentDTO deleteByDoctor(int aptId);
	public List<AvailabilitySlotsDTO> getSlotsbyDate(int doctorId,LocalDate date);
	
	List<AvailabilitySlotsDTO> viewAllAvailablity();
	boolean bookAvailablity(int slotId);
	boolean cancelBookedSlot(int slotId);
	
	

}
