package com.cts.AppointmentBookingModule.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import com.cts.AppointmentBookingModule.config.FeignClientConfig;
import com.cts.AppointmentBookingModule.model.AvailablitySlotsDTO;

@FeignClient(name="DOCTORAVAILABILITYMANAGEMENT",configuration = FeignClientConfig.class)
public interface AvailablityClient {
	
	@GetMapping("api/doctors/availablity")
	public List<AvailablitySlotsDTO> viewAllAvailablity();
	
	@PostMapping("api/doctors/availablity/{slotId}/book")
	public boolean markBooked(@PathVariable("slotId") int slotId);
	
	@PostMapping("api/doctors/availablity/{slotId}/cancel")
	public boolean cancelBooking(@PathVariable("slotId") long id);
	
	

}
