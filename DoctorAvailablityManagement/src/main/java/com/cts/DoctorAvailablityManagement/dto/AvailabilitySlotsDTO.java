package com.cts.DoctorAvailablityManagement.dto;


import java.time.LocalDate;
import java.time.LocalTime;

import com.cts.DoctorAvailablityManagement.model.AvailablitySlot;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilitySlotsDTO {
    private int id;

    private int doctorId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean status;
    
    public AvailabilitySlotsDTO(AvailablitySlot slot){
    	this.id = slot.getId();
    	this.doctorId = slot.getDoctorId();
    	this.startTime=slot.getStartTime();
    	this.endTime=slot.getEndTime();
    	this.status = slot.isStatus();
    }
    
    @Transient
    DoctorDTO doctor;

}
