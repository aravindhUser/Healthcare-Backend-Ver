package com.healthcare.Consultations.model;

import jakarta.persistence.*;  
import lombok.*;  

import java.time.LocalDate;  
import java.time.LocalDateTime;  
import java.util.ArrayList;  
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.healthcare.Consultations.dto.DoctorDTO;
import com.healthcare.Consultations.dto.PatientDTO;  

@Entity  
@Table(name = "consultations")  
@Data 
@NoArgsConstructor  
@AllArgsConstructor  
@Builder  
public class Consultation {  

  @Id  
  @GeneratedValue(strategy = GenerationType.IDENTITY)  
  private int consultationId;  
  
  private int appointmentId;  

  private int patientId;  
  
  private String patientName;

  private int doctorId;  
  
  
  private String doctorName;

  private LocalDate date;  

  @Column(columnDefinition = "TEXT")  
  private String notes;  

  @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL, orphanRemoval = true)  
  @JsonManagedReference
  private List<Prescription> prescriptions = new ArrayList<>();  
  
  
  private LocalDateTime createdAt;  

  @PrePersist  
  public void prePersist() {  
      this.createdAt = LocalDateTime.now();  
      
  }  
  
  @Transient  
  private PatientDTO patient;  

  @Transient  
  private DoctorDTO doctor;
} 
