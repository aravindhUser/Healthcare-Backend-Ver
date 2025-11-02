package com.cts.DoctorAvailablityManagement.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cts.DoctorAvailablityManagement.client.DoctorAuthService;
import com.cts.DoctorAvailablityManagement.dto.AppointmentDTO;
import com.cts.DoctorAvailablityManagement.dto.AvailabilitySlotsDTO;
import com.cts.DoctorAvailablityManagement.dto.DoctorDTO;
import com.cts.DoctorAvailablityManagement.dto.DoctorSlotsDTO;
import com.cts.DoctorAvailablityManagement.model.DoctorSlots;
//import com.cts.DoctorAvailablityManagement.service.DoctorAppointmentService;
import com.cts.DoctorAvailablityManagement.service.DoctorAvailablityService;

import lombok.AllArgsConstructor;


//@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping("/api/doctors")
@AllArgsConstructor
public class DoctorAvailablityController {

    @Autowired
    private DoctorAvailablityService service;

    @Autowired
    private DoctorAuthService authService;
    
    //View Slots For Patients.
    @GetMapping("/availability")
    public ResponseEntity<List<AvailabilitySlotsDTO>> getAllAvailability() {
        return ResponseEntity.ok(service.viewAllAvailablity());
    }

    //View Availability Slots for doctorId and Date.
    @GetMapping("/{doctorId}/{date}/availability")
    public ResponseEntity<List<AvailabilitySlotsDTO>> getAvailabilityByDate(
            @PathVariable int doctorId,
            @PathVariable LocalDate date) {
        return ResponseEntity.ok(service.getSlotsbyDate(doctorId, date));
    }
    
    //View AvaibilitySlots by Doctor.
    @GetMapping("/{doctorId}/availability")
    public ResponseEntity<List<DoctorSlotsDTO>> getAvailabilityByDoctor(@PathVariable int doctorId) {
        return ResponseEntity.ok(service.getAvailablity(doctorId));
    }

    //Add AvaibilitySlots by Doctor.
    @PostMapping("/availability")
    public ResponseEntity<DoctorSlots> addAvailability(@RequestBody DoctorSlots slot) {
        return ResponseEntity.ok(service.addAvailablity(slot));
    }

    
//    @GetMapping("/availability/{slotId}")
//    public ResponseEntity<AvailablitySlot> getSlotById(@PathVariable int slotId) {
//        return ResponseEntity.ok(service.viewSlot(slotId));
//    }

    @PostMapping("/availability/{slotId}/book")
    public ResponseEntity<Boolean> bookSlot(@PathVariable int slotId) {
        return ResponseEntity.ok(service.bookAvailablity(slotId));
    }

    @PostMapping("/availability/{slotId}/cancel")
    public ResponseEntity<Boolean> cancelSlot(@PathVariable int slotId) {
    	System.out.println("Called to cancel");
    	System.out.println("Slot id"+slotId);
        return ResponseEntity.ok(service.cancelBookedSlot(slotId));
    }

    @DeleteMapping("/availability/{slotId}")
    public ResponseEntity<String> deleteSlot(@PathVariable int slotId) {
        service.deleteAvailablity(slotId);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/delete/appointment/{aptId}")
    public ResponseEntity<AppointmentDTO> deleteAppointmentByDoc(@PathVariable("aptId") int aptId) {
    	return ResponseEntity.ok(service.deleteByDoctor(aptId));
    }
    @GetMapping("/search/{doctorId}")
    public ResponseEntity<DoctorDTO> searchDoctor(@PathVariable int doctorId) {
        return ResponseEntity.ok(service.getDoctor(doctorId));
    }

    @GetMapping("/{doctorId}/appointments")
    public ResponseEntity<List<AppointmentDTO>> getAppointments(@PathVariable int doctorId) {
        return ResponseEntity.ok(service.getAppointment(doctorId));
    }
    
    @GetMapping
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        return ResponseEntity.ok(authService.getAllDoctors());
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable int doctorId) {
        return ResponseEntity.ok(authService.getDoctorById(doctorId));
    }
    
    
}
