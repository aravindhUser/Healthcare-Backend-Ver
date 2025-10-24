package com.cts.DoctorAvailablityManagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.cts.DoctorAvailablityManagement.model.AppointmentDTO;
import com.cts.DoctorAvailablityManagement.model.AvailablitySlot;
import com.cts.DoctorAvailablityManagement.model.DoctorDTO;
import com.cts.DoctorAvailablityManagement.repo.AvailablityRepo;

import lombok.AllArgsConstructor;

@Primary
@Service
@AllArgsConstructor
public class DoctorAppointmentImpl implements DoctorAppointmentService{
	
	private AvailablityRepo availablityRepo;
//	private AppointmentRepo appointmentRepo;
	private DoctorAuthService doctorService;
	private AppointmentClient appointmentService;

	public DoctorDTO getDoctor(int id) {
//		return doctorRepo.findById(id).orElseThrow();
		return doctorService.getDoctorById(id);
		
	}
	
	public List<DoctorDTO> returnDoctors() {
//		return doctorRepo.findAll();
		return doctorService.getAllDoctors();
//		return null;
	}
	
	public List<AvailablitySlot> getAvailablity(int doctorId){
		List<AvailablitySlot> as = availablityRepo.findByDoctorId(doctorId);
		for(AvailablitySlot slot : as) {
			DoctorDTO doctor = doctorService.getDoctorById(slot.getDoctorId()); 
			slot.setDoctor(doctor);
			
		}
		return availablityRepo.findByDoctorId(doctorId);
	}
	
	public AvailablitySlot addAvailablity(int doctorId,AvailablitySlot slot) {
		DoctorDTO doctor = getDoctor(doctorId);
		slot.setDoctor(doctor);
		return availablityRepo.save(slot);
	}
	
	public void deleteAvailablity(int slotId) {
		availablityRepo.deleteById(slotId);
	}
	
	public List<AppointmentDTO> getAppointment(int doctorId){
		return appointmentService.getAllDoctorsAppointment(doctorId);
		
	}
	
	public AppointmentDTO deleteByDoctor(int aptId) {
		return appointmentService.deleteByDoctor(aptId);
		
	}

	@Override
	public List<AvailablitySlot> viewAllAvailablity() {
		List<AvailablitySlot> available = availablityRepo.findAll();
		for(AvailablitySlot as:available) {
			System.out.println("  " + as.getDoctorId()+"Doctor ID trying to be Fetched");
			DoctorDTO doctor = doctorService.getDoctorById(as.getDoctorId());
			as.setDoctor(doctor);
		}
		return availablityRepo.findAll();

	}

	@Override
	public boolean bookAvailablity(int slotId) {
		Optional<AvailablitySlot> al = availablityRepo.findById(slotId);
		AvailablitySlot found = al.get();
		found.setStatus(true);
		availablityRepo.save(found);
		return true;
	}

	@Override
	public boolean cancelBookedSlot(int slotId) {
		Optional<AvailablitySlot> al = availablityRepo.findById(slotId);
		AvailablitySlot found = al.get();
		found.setStatus(false);
		availablityRepo.save(found);
		
		return false;
	}

}
