package com.healthcare.Consultations.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;  
import lombok.*;  

@Entity  
@Table(name = "prescriptions")  
@Data  
@NoArgsConstructor  
@AllArgsConstructor  
@Builder  
public class Prescription {  

  @Id  
  @GeneratedValue(strategy = GenerationType.IDENTITY)  
  private Long id;  

  private String medicineName;  
  private String dose;  
  private String frequency;  
  private String duration;  

  @ManyToOne(fetch = FetchType.LAZY)  
  @JoinColumn(name = "consultation_id")  
  @JsonBackReference
  private Consultation consultation;  
} 
