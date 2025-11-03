package com.cts.DoctorAvailablityManagement.service;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cts.DoctorAvailablityManagement.dto.DoctorDTO;
import com.cts.DoctorAvailablityManagement.model.DoctorSlots;
import com.cts.DoctorAvailablityManagement.repo.AvailablityRepo;
import com.cts.DoctorAvailablityManagement.repo.DoctorSlotsRepo;

@ExtendWith(MockitoExtension.class)
public class DoctorAvailablityManagementServiceTest {
	@Mock
	private AvailablityRepo availablityRepo;
	@Mock
	private DoctorSlotsRepo doctorSlotsRepo;
	@InjectMocks
	DoctorAvailablityImpl service;
	
	@Test
	void addAvailablitySuccessfully_createsDoctorSlotAndAvailabilitySlots() {
	    // Arrange
	    DoctorSlots slot = new DoctorSlots();
	    slot.setSlotid(1);
	    slot.setDoctorId(101);
	    slot.setDate(LocalDate.parse("2025-11-03"));
	    slot.setStartTime(LocalTime.parse("13:00"));
	    slot.setEndTime(LocalTime.parse("14:00"));

	    DoctorDTO doctor = new DoctorDTO();
	    doctor.setId(101);
	    doctor.setName("Dr. Test");

	    Mockito.when(service.getDoctor(101)).thenReturn(doctor);
	    Mockito.when(doctorSlotsRepo.existsByDoctorIdAndDateAndStartTimeAndEndTime(
	        slot.getDoctorId(), slot.getDate(), slot.getStartTime(), slot.getEndTime()
	    )).thenReturn(false);
	    Mockito.when(doctorSlotsRepo.save(slot)).thenReturn(slot);

	    // Act
	    DoctorSlots result = service.addAvailablity(slot);

	    // Assert
	    Assertions.assertNotNull(result);
	    Assertions.assertEquals(1, result.getSlotid());
	    Mockito.verify(availablityRepo, Mockito.times(1)).saveAll(Mockito.anyList());
	}


}
