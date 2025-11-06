package com.cts.DoctorAvailablityManagement.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.cts.DoctorAvailablityManagement.dto.DoctorDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    @JsonBackReference
    @ToString.Exclude
    private DoctorSlots doctorSlot;

    @Transient
    private DoctorDTO doctor;
}