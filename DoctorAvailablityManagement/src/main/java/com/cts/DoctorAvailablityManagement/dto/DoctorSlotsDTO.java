package com.cts.DoctorAvailablityManagement.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.cts.DoctorAvailablityManagement.model.DoctorSlots;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorSlotsDTO {
    private int slotid;
    private int doctorId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    
    public DoctorSlotsDTO(DoctorSlots slot){
    	this.doctorId=slot.getDoctorId();
    	this.slotid=slot.getSlotid();
    	this.date=slot.getDate();
    	this.startTime=slot.getStartTime();
    	this.endTime=slot.getEndTime();
    }
}