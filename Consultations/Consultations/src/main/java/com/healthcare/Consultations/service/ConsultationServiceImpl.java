package com.healthcare.Consultations.service;
 
import com.healthcare.Consultations.dto.ConsultationDTO;
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
 
    private final ConsultationRepo consultationRepository;
   
    
 
    @Override
    @Transactional
    public Consultation saveConsultation(ConsultationDTO dto) {
        Consultation consultation = Consultation.builder()
//                .appointmentId(dto.getAppointmentId())
                .patientId(dto.getPatientId())
                .patientName(dto.getPatientName())
                .doctorId(dto.getDoctorId())
                .doctorName(dto.getDoctorName())
                .date(dto.getDate())
                .notes(dto.getNotes())
                .prescriptions(new ArrayList<>())
                .build();
 
        if (dto.getPrescriptions() != null) {
            for (ConsultationDTO.PrescriptionDTO p : dto.getPrescriptions()) {
                Prescription prescription = Prescription.builder()
                        .medicineName(p.getMedicineName())
                        .dose(p.getDose())
                        .frequency(p.getFrequency())
                        .duration(p.getDuration())
                        .consultation(consultation)
                        .build();
                consultation.getPrescriptions().add(prescription);
            }
           
        }
        
        if (consultation.getPrescriptions() == null) {
            consultation.setPrescriptions(new ArrayList<>());
        }
 
        return consultationRepository.save(consultation);
    }
 
    @Override
    public List<Consultation> getAllConsultations() {
        return consultationRepository.findAll();
        
    }
 
    @Override
    public Consultation getConsultationById(Long id) {
        return consultationRepository.findById(id).orElse(null);
//                .orElseThrow(() -> new RuntimeException("Consultation not found"));
    }
    
    @Override
    public List<Consultation> getConsultationsByDoctorId(Long doctorId) {
        return consultationRepository.findByDoctorId(doctorId);
    }

	@Override
	public List<Consultation> getConsultationsByPatientId(String patientId) {
		return consultationRepository.findByPatientId(patientId);

	}
    
    
    
}
 