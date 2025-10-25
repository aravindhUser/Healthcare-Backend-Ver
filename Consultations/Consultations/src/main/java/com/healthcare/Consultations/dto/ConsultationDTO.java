package com.healthcare.Consultations.dto;


import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data 
public class ConsultationDTO {

//   private String appointmentId;
   private String patientId;
   private String patientName;
   private Long doctorId;
   private String doctorName;
   private LocalDate date;
   private String notes;
   
   
   private List<PrescriptionDTO> prescriptions;
   @NoArgsConstructor
   @AllArgsConstructor
   @Builder
   @Data
   public static class PrescriptionDTO {
       private String medicineName;
       private String dose;
       private String frequency;
       private String duration;
   }
   
   
//   private List<AttachmentDTO> attachments;
//
//   @NoArgsConstructor
//   @AllArgsConstructor
//   @Builder
//   @Data
//   public static class AttachmentDTO {
//       private String filename;
//       private long size;
//       private String mimeType;
//   }
}

