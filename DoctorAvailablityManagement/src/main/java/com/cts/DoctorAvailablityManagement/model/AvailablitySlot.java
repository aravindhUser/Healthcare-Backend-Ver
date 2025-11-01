package com.cts.DoctorAvailablityManagement.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.cts.DoctorAvailablityManagement.dto.DoctorDTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "availablity_slot")
public class AvailablitySlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int doctorId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "doctor_slot_id")
    private DoctorSlots doctorSlot;

    @Transient
    private DoctorDTO doctor;
}