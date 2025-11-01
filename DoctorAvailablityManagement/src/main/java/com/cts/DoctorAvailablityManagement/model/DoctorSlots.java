package com.cts.DoctorAvailablityManagement.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.cts.DoctorAvailablityManagement.dto.DoctorDTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DoctorSlots {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int slotid;

    private int doctorId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    @OneToMany(mappedBy = "doctorSlot", cascade = CascadeType.ALL)
    private List<AvailablitySlot> availablitySlots;

}