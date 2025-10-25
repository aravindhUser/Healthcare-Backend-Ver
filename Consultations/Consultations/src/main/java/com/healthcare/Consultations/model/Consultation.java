package com.healthcare.Consultations.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "consultations")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Consultation {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long consultationId;

//   @Column(nullable = false)
//   private String appointmentId;

   @Column(nullable = false)
   private String patientId;

   private String patientName;

   @Column(nullable = false)
   private Long doctorId;

   private String doctorName;

   private LocalDate date;

   @Column(columnDefinition = "TEXT")
   private String notes;
   
   
   @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL, orphanRemoval = true)
   @JsonManagedReference
   private List<Prescription> prescriptions = new ArrayList<>();
   
   
   
   public List<Prescription> getPrescriptions(){
	   return prescriptions;
   }
   
   public void setPrescriptions(List<Prescription> prescriptions) {
	   this.prescriptions = prescriptions;
   }

   private LocalDateTime createdAt;
//   private LocalDateTime updatedAt;

   @PrePersist
   public void prePersist() {
       createdAt = LocalDateTime.now();
//       updatedAt = createdAt;
   }

//   @PreUpdate
//   public void preUpdate() {
//       updatedAt = LocalDateTime.now();
//   }
}
