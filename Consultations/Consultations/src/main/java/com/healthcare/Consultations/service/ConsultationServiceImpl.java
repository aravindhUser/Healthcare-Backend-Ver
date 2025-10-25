package com.healthcare.Consultations.service;

import com.healthcare.Consultations.dto.ConsultationDTO;
import com.healthcare.Consultations.dto.DoctorDTO;
import com.healthcare.Consultations.dto.PatientDTO;
import com.healthcare.Consultations.model.Consultation;  
import com.healthcare.Consultations.model.Prescription;  
import com.healthcare.Consultations.repository.ConsultationRepo;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;  
import org.springframework.transaction.annotation.Transactional;  
  
import java.util.ArrayList;  
import java.util.List;  
  
@Service  
@AllArgsConstructor
public class ConsultationServiceImpl implements ConsultationService {  
  
    private final ConsultationRepo consultationRepo;  
    private final AuthClient client;
    
    @Override
    public Consultation saveConsultation(ConsultationDTO dto) {
    	System.out.println(dto);
        Consultation.ConsultationBuilder builder = Consultation.builder()
                .appointmentId(dto.getAppointmentId())
                .date(dto.getDate())
                .doctorId(dto.getDoctorId())
                .doctorName(dto.getDoctorName())
                .patientId(dto.getPatientId())
                .patientName(dto.getPatientName())
                .notes(dto.getNotes());

        if (dto.getDoctor() != null) {
            DoctorDTO doctor = client.getDoctorById(dto.getDoctor().getDoctorId());
            builder.doctorId(doctor.getDoctorId()).doctor(doctor);
        }

        if (dto.getPatient() != null) {
            PatientDTO patient = client.getPatientById(dto.getPatient().getPatientId());
            builder.patientId(patient.getPatientId()).patient(patient);
        }

        Consultation consultation = builder.build();

        if (dto.getPrescriptions() != null) {
            List<Prescription> prescriptionList = new ArrayList<>();
            for (ConsultationDTO.PrescriptionDTO p : dto.getPrescriptions()) {
                Prescription pres = Prescription.builder()
                        .medicineName(p.getMedicineName())
                        .dose(p.getDose())
                        .frequency(p.getFrequency())
                        .duration(p.getDuration())
                        .consultation(consultation)
                        .build();
                prescriptionList.add(pres);
            }
            consultation.setPrescriptions(prescriptionList);
        }

        return consultationRepo.save(consultation);
    }
  
  
    @Override  
    public List<Consultation> getAllConsultations() {  
        return consultationRepo.findAll();  
    }  
  
    @Override  
    public Consultation getConsultationById(int id) {  
        return consultationRepo.findById(id)  
                .orElseThrow(() -> new RuntimeException("Consultation not found"));  
    }  
  
    @Override  
    public List<Consultation> getConsultationsByDoctor(int doctorId) {  
        return consultationRepo.findByDoctorId(doctorId);  
    }  
  
    @Override  
    public List<Consultation> getConsultationsByPatient(int patientId) {  
        return consultationRepo.findByPatientId(patientId);  
    }  
} 






















//@Override  
//@Transactional  
//public Consultation saveConsultation(ConsultationDTO dto) {
//
//  DoctorDTO doctor = client.getDoctorById(dto.getDoctor().getId());  
//  PatientDTO patient = client.getPatientById(dto.getPatient().getPatientId());  
//
//  Consultation consultation = Consultation.builder()  
//          .appointmentId(dto.getAppointmentId())  
//          .doctorId(doctor.getId())  
//          .patientId(patient.getPatientId())  
//          .date(dto.getDate())  
//          .notes(dto.getNotes())  
//          .doctor(doctor)  
//          .patient(patient)  
//          .build();  
//
//  if (dto.getPrescriptions() != null) {  
//      List<Prescription> prescriptionList = new ArrayList<>();  
//      for (ConsultationDTO.PrescriptionDTO p : dto.getPrescriptions()) {  
//          Prescription pres = Prescription.builder()  
//                  .medicineName(p.getMedicineName())  
//                  .dose(p.getDose())  
//                  .frequency(p.getFrequency())  
//                  .duration(p.getDuration())  
//
//                  .consultation(consultation)  
//                  .build();  
//          prescriptionList.add(pres);  
//      }  
//      consultation.setPrescriptions(prescriptionList);  
//  }  
//
//  return consultationRepo.save(consultation);  
//} 
