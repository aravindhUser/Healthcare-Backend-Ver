package com.cts.DoctorAvailablityManagement.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.cts.DoctorAvailablityManagement.client.AppointmentClient;
import com.cts.DoctorAvailablityManagement.client.DoctorAuthService;
import com.cts.DoctorAvailablityManagement.dto.AppointmentDTO;
import com.cts.DoctorAvailablityManagement.dto.AvailabilitySlotsDTO;
import com.cts.DoctorAvailablityManagement.dto.DoctorDTO;
import com.cts.DoctorAvailablityManagement.dto.DoctorSlotsDTO;
import com.cts.DoctorAvailablityManagement.exceptions.DoctorSlotsException;
import com.cts.DoctorAvailablityManagement.model.AvailablitySlot;
import com.cts.DoctorAvailablityManagement.model.DoctorSlots;
import com.cts.DoctorAvailablityManagement.repo.AvailablityRepo;
import com.cts.DoctorAvailablityManagement.repo.DoctorSlotsRepo;

import lombok.AllArgsConstructor;

@Primary
@Service
@AllArgsConstructor
public class DoctorAvailablityImpl implements DoctorAvailablityService{
	
	//For posting the Doctor Statements
	private AvailablityRepo availablityRepo;
	private DoctorSlotsRepo doctorSlotsRepo;
	
	//Feign Clients Call
	private DoctorAuthService doctorService;
	private AppointmentClient appointmentService;


	public List<AvailabilitySlotsDTO> getSlotsbyDate(int doctorId, LocalDate date) {
	    List<AvailablitySlot> available;

	    LocalDate today = LocalDate.now();
	    if (date.isEqual(today)) {
	        LocalTime now = LocalTime.now();
	        available = availablityRepo.findByDocIdAndDate(doctorId, date, now); // filter by time
	    } else {
	        available = availablityRepo.findByDocIdAndDate(doctorId, date); // no time filter
	    }

	    List<AvailabilitySlotsDTO> copySlots = new ArrayList<>();
	    for (AvailablitySlot as : available) {
	        DoctorDTO doctor = doctorService.getDoctorById(as.getDoctorId());
	        AvailabilitySlotsDTO newSlot = new AvailabilitySlotsDTO(as);
	        newSlot.setDoctor(doctor);
	        copySlots.add(newSlot);
	    }

	    return copySlots;
	}

	
	//Get Availability For Doctor
	public List<DoctorSlotsDTO> getAvailablity(int doctorId){
		LocalDate today = LocalDate.now();
		LocalTime now = LocalTime.now();
		List<DoctorSlots> slot = doctorSlotsRepo.findUpcomingSlotsByDoctorId(doctorId,today,now);
		List<DoctorSlotsDTO> docSlots = new ArrayList<>();
		for(DoctorSlots as : slot) {
			DoctorSlotsDTO dto = new DoctorSlotsDTO(as);
			docSlots.add(dto);
		}
		return docSlots;
		
	}
	
	//Add Doctor Available Slots in the both the Tables 
	public DoctorSlots addAvailablity(DoctorSlots freeSlot) {
		System.out.println(freeSlot);
		DoctorDTO doctor = getDoctor(freeSlot.getDoctorId());
		
	
	    boolean slotsExist = doctorSlotsRepo.existsByDoctorIdAndDateAndStartTimeAndEndTime(
	        freeSlot.getDoctorId(),
	        freeSlot.getDate(),
	        freeSlot.getStartTime(),
	        freeSlot.getEndTime()
	    );
	    
	    if (slotsExist) 
	    {
        throw new DoctorSlotsException("Slots already exist for this doctor and time range.");
	    }
	    DoctorSlots docSlot = doctorSlotsRepo.save(freeSlot);
	    
		List<AvailablitySlot> slotList = new ArrayList<>();
		LocalTime currentStart = freeSlot.getStartTime();
		LocalTime endTime = freeSlot.getEndTime();
		while(currentStart.plusMinutes(30).isBefore(endTime)||currentStart.plusMinutes(30).equals(endTime)) {
			AvailablitySlot slot = new AvailablitySlot();
            slot.setDoctorId(freeSlot.getDoctorId());
            slot.setDate(freeSlot.getDate());
            slot.setStartTime(currentStart);
            slot.setEndTime(currentStart.plusMinutes(30));
            slot.setStatus(false);
            slot.setDoctorSlot(docSlot);
            slot.setDoctor(doctor);
            slotList.add(slot);
            currentStart = currentStart.plusMinutes(30);      
		}
		availablityRepo.saveAll(slotList);
		return docSlot;	
	}
	
	//Delete Slots for Doctor
	@Override
	public String deleteAvailablity(int slotId) {
	    if (!doctorSlotsRepo.existsById(slotId)) {
	        throw new DoctorSlotsException("Doctor slot with ID " + slotId + " not found.");
	    }

	    // Step 1: Get all availability entries for the slot
	    List<AvailablitySlot> availablities = availablityRepo.findByDoctorSlot_Slotid(slotId);

	    // Step 2: Cancel appointments for each availability ID
	    for (AvailablitySlot availability : availablities) {
	        int availabilityId = availability.getId();
	        appointmentService.deleteByDoctor(availabilityId);
	    }

	    // Step 3: Delete availability entries
	    availablityRepo.deleteAll(availablities);

	    // Step 4: Delete the slot
	    doctorSlotsRepo.deleteById(slotId);
	    return "SuccessFully Deleted the Slots";
	}

	
	//Book Availability Status to True for the Doctor. For Patient Client
	@Override
	public boolean bookAvailablity(int slotId) {
		Optional<AvailablitySlot> al = availablityRepo.findById(slotId);
		AvailablitySlot found = al.get();
		found.setStatus(true);
		availablityRepo.save(found);
		return true;
	}
	
	//Cancel Booked Slots for Patient Client.
	@Override
	public boolean cancelBookedSlot(int slotId) {
		System.out.println("This works fine?");
		System.out.println("Slot Id"+slotId);
		Optional<AvailablitySlot> al = availablityRepo.findById(slotId);
		System.out.println("al.get()  "+al.get());
		if(al.isPresent()) {
			System.out.println("You are present");
			AvailablitySlot found = al.get();
			System.out.println("Slot Details: "+ found.isStatus());
			found.setStatus(false);
			System.out.println("Slot Details: "+ found.isStatus());
			availablityRepo.save(found);
			return true;
		}
		return false;
	}
	
	//Get Appointments Booked for Doctor in Doctor Page.
	@Override
	public List<AppointmentDTO> getAppointment(int doctorId){
		List<AppointmentDTO> appointments = appointmentService.getAllDoctorsAppointment(doctorId);
		List<AppointmentDTO> bookedOnly = new ArrayList<>();
		for(AppointmentDTO ap:appointments) {
			if(ap.getStatus().equals("booked")) {
				bookedOnly.add(ap);
			}
			
		}
		return bookedOnly;
		
	}
	
	
	//Delete Appointment Booked for Doctor.
	@Override
	public AppointmentDTO deleteByDoctor(int aptId) {
		AppointmentDTO appointment = appointmentService.getAppointmentByDoctor(aptId);
		availablityRepo.deleteById(appointment.getSlotId());
		AppointmentDTO deletedAppoinment = appointmentService.deleteByDoctor(aptId);
		System.out.println(deletedAppoinment);
		System.out.println((deletedAppoinment.getStatus().equals("Cancel By Doctor")));
		if(!(deletedAppoinment.getStatus().equals("Cancel By Doctor"))) {
			throw new DoctorSlotsException("Unable to Cancel the Appointment.");
			
		}
		return deletedAppoinment;
		
	}
	
	//Get Doctors By Feign Client.
	
	@Override

	public DoctorDTO getDoctor(int id) {
		if(doctorService.getDoctorById(id)==null) {
			throw new DoctorSlotsException("Doctor Not Found From the Client");	
		}
		return doctorService.getDoctorById(id);	
	}
	
	//View Doctors By Feign Client
	@Override
	public List<DoctorDTO> returnDoctors() {
		return doctorService.getAllDoctors();
	}
	
	@Override
	public List<AvailabilitySlotsDTO> viewAllAvailablity() {
	    List<AvailablitySlot> available = availablityRepo.findAll();
	    List<AvailabilitySlotsDTO> copySlots = new ArrayList<>();

	    for (AvailablitySlot as : available) {
	        System.out.println("  " + as.getDoctorId() + " Doctor ID trying to be Fetched");
	        DoctorDTO doctor = doctorService.getDoctorById(as.getDoctorId()); // Moved inside loop
	        AvailabilitySlotsDTO newSlot = new AvailabilitySlotsDTO(as);
	        newSlot.setDoctor(doctor);
	        copySlots.add(newSlot);
	    }

	    return copySlots;
	}
	
	

}
