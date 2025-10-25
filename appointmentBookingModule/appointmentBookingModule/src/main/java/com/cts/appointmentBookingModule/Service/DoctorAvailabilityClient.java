package com.cts.appointmentBookingModule.Service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.cts.appointmentBookingModule.config.FeignClientConfig;
import com.cts.appointmentBookingModule.model.AvailabilitySlotDTO;
import com.cts.appointmentBookingModule.model.DoctorDTO;

@FeignClient(name = "DoctorAvailabilityManagement", configuration = FeignClientConfig.class)
public interface DoctorAvailabilityClient {
	@GetMapping("/api/doctors/{id}")
    DoctorDTO getDoctorById(@PathVariable long  id);

    @GetMapping("/api/doctors/{id}/availablity")
    List<AvailabilitySlotDTO> getSlotsByDoctor(@PathVariable long id);

    @PostMapping("/api/doctors/availablity/{slotId}/book")
    boolean markSlotBooked(@PathVariable long slotId);

    @PostMapping("/api/doctors/availablity/{slotId}/cancel")
    boolean cancelSlot(@PathVariable long slotId);

}
