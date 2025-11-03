package com.cts.DoctorAvailablityManagement.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.DoctorAvailablityManagement.client.AppointmentClient;
import com.cts.DoctorAvailablityManagement.client.DoctorAuthService;
import com.cts.DoctorAvailablityManagement.dto.DoctorDTO;
import com.cts.DoctorAvailablityManagement.exceptions.DoctorSlotsException;
import com.cts.DoctorAvailablityManagement.model.*;
import com.cts.DoctorAvailablityManagement.repo.*;

@SpringBootTest
class DoctorAvailablityManagementServiceTest {

    @Mock
    private AvailablityRepo availablityRepo;

    @Mock
    private DoctorSlotsRepo doctorSlotsRepo;

    @Mock
    private DoctorAuthService doctorService;

    @Mock
    private AppointmentClient appointmentService;

    @InjectMocks
    private DoctorAvailablityImpl doctorAvailablityService;

    private DoctorSlots freeSlot;
    private DoctorDTO doctor;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        freeSlot = new DoctorSlots();
        freeSlot.setDoctorId(1);
        freeSlot.setDate(LocalDate.of(2025, 11, 3));
        freeSlot.setStartTime(LocalTime.of(9, 0));
        freeSlot.setEndTime(LocalTime.of(10, 0));

        doctor = new DoctorDTO();
        doctor.setId(1);
        doctor.setName("Dr. John Doe");
    }

    // ✅ TEST 1: Add availability successfully
    @Test
    void testAddAvailablity_Success() {
        when(doctorService.getDoctorById(1)).thenReturn(doctor);
        when(doctorSlotsRepo.existsByDoctorIdAndDateAndStartTimeAndEndTime(anyInt(), any(), any(), any())).thenReturn(false);
        when(doctorSlotsRepo.save(any(DoctorSlots.class))).thenReturn(freeSlot);

        DoctorSlots savedSlot = doctorAvailablityService.addAvailablity(freeSlot);

        assertNotNull(savedSlot);
        verify(doctorSlotsRepo, times(1)).save(any(DoctorSlots.class));
        verify(availablityRepo, times(1)).saveAll(anyList());
    }

    // ✅ TEST 2: Add availability throws exception when slot already exists
    @Test
    void testAddAvailablity_AlreadyExists() {
        when(doctorService.getDoctorById(1)).thenReturn(doctor);
        when(doctorSlotsRepo.existsByDoctorIdAndDateAndStartTimeAndEndTime(anyInt(), any(), any(), any()))
                .thenReturn(true);

        assertThrows(DoctorSlotsException.class, () -> {
            doctorAvailablityService.addAvailablity(freeSlot);
        });

        verify(doctorSlotsRepo, never()).save(any());
    }

    // ✅ TEST 3: Delete availability successfully
    @Test
    void testDeleteAvailablity_Success() {
        int slotId = 101;

        AvailablitySlot slot1 = new AvailablitySlot();
        slot1.setId(1);
        slot1.setDoctorId(1);

        List<AvailablitySlot> slotList = List.of(slot1);

        when(doctorSlotsRepo.existsById(slotId)).thenReturn(true);
        when(availablityRepo.findByDoctorSlot_Slotid(slotId)).thenReturn(slotList);

        String result = doctorAvailablityService.deleteAvailablity(slotId);

        assertEquals("SuccessFully Deleted the Slots", result);
        verify(appointmentService, times(1)).deleteByDoctor(slot1.getId());
        verify(availablityRepo, times(1)).deleteAll(slotList);
        verify(doctorSlotsRepo, times(1)).deleteById(slotId);
    }

    // ✅ TEST 4: Delete availability when slot not found
    @Test
    void testDeleteAvailablity_NotFound() {
        int slotId = 999;
        when(doctorSlotsRepo.existsById(slotId)).thenReturn(false);

        assertThrows(DoctorSlotsException.class, () -> {
            doctorAvailablityService.deleteAvailablity(slotId);
        });

        verify(availablityRepo, never()).deleteAll(any());
    }
}
