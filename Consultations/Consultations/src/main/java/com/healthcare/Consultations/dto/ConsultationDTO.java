package com.healthcare.Consultations.dto;

import lombok.*;  
import java.time.LocalDate;  
import java.util.List;  
  
@Data  
@NoArgsConstructor  
@AllArgsConstructor  
@Builder  
public class ConsultationDTO {  
    private int appointmentId;  
    private int doctorId;
    private int patientId;
    private String doctorName;  
    private String patientName; 
    private DoctorDTO doctor;  
    private PatientDTO patient;  
    private LocalDate date;  
    private String notes;  
    private List<PrescriptionDTO> prescriptions;  
  
    @Data  
    @NoArgsConstructor  
    @AllArgsConstructor  
    @Builder  
    public static class PrescriptionDTO {  
        private String medicineName;  
        private String dose;  
        private String frequency;  
        private String duration;  
    }  
}  