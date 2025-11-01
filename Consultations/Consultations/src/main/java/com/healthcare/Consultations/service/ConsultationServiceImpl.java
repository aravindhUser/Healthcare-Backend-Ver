package com.healthcare.Consultations.service;

import com.healthcare.Consultations.dto.ConsultationDTO;
import com.healthcare.Consultations.dto.DoctorDTO;
import com.healthcare.Consultations.dto.PatientDTO;
import com.healthcare.Consultations.exception.ResourceNotFoundException;
import com.healthcare.Consultations.model.Consultation;  
import com.healthcare.Consultations.model.Prescription;  
import com.healthcare.Consultations.repository.ConsultationRepo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.healthcare.Consultations.exception.BadRequestException;
import org.springframework.stereotype.Service;   
  
import java.util.ArrayList;  
import java.util.List;  
 
@Slf4j
@Service  
@AllArgsConstructor
public class ConsultationServiceImpl implements ConsultationService {  
  
    private final ConsultationRepo consultationRepo;  
    private final AuthClient client;
    
    @Override
    public Consultation saveConsultation(ConsultationDTO dto) {
    	
    	log.info("Saving Consultation for Doctor ID:{} and Patient ID:{}", dto.getDoctorId(),dto.getPatientId());
    	
    	//Validating required fields
    	
//    	if(dto == null) {
//    		throw new BadRequestException("Consultation Data cannot be null.");
//    	}
   	
    	if(dto.getDoctorId() <= 0) {
    		throw new BadRequestException("Doctor ID must be valid and greater than zero");
    	}
    	
    	if(dto.getPatientId() <= 0) {
    		throw new BadRequestException("Patient ID must be valid and greater than zero");
    	}
    	
    	if(dto.getDate() == null) {
    		throw new BadRequestException("Consulation Date is Required");
    	}
    	
    	// Building Consultation Entity 
        Consultation.ConsultationBuilder builder = Consultation.builder()
                .appointmentId(dto.getAppointmentId())
                .date(dto.getDate())
                .doctorId(dto.getDoctorId())
                .doctorName(dto.getDoctorName())
                .patientId(dto.getPatientId())
                .patientName(dto.getPatientName())
                .notes(dto.getNotes());
        
        // Fetching doctor info via FeignClient

        if (dto.getDoctor() != null) {
        	log.debug("Fetching doctor details for Doctor ID:{}",dto.getDoctor().getDoctorId());
            DoctorDTO doctor = client.getDoctorById(dto.getDoctor().getDoctorId());
            
            if (doctor == null) {
            	throw new ResourceNotFoundException("Doctor not found with ID:"+dto.getDoctor().getDoctorId());
            }
            builder.doctorId(doctor.getDoctorId()).doctor(doctor);
        }
       // Fetching doctor info via FeignClient
        
        if (dto.getPatient() != null) {
        	log.debug("Fetching patient details for Patient ID:{}",dto.getPatient().getPatientId());

            PatientDTO patient = client.getPatientById(dto.getPatient().getPatientId());
            
            if (patient == null) {
            	throw new ResourceNotFoundException("Patient not found with ID:"+dto.getPatient().getPatientId());
            }
            
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
        Consultation saved =  consultationRepo.save(consultation);
        log.info("Consultation successfully saved with ID:{}",saved.getConsultationId());
        return saved;
    }
  
  
    @Override  
    public List<Consultation> getAllConsultations() { 
    	log.info("Fetching all consultations");
    	List<Consultation> list = consultationRepo.findAll();
    	if(list.isEmpty()) {
    		log.warn("No consultations found in the System.");
    		throw new ResourceNotFoundException("No consultations available");
    	}
    	log.info("{} consultation found",list.size());
        return list; 
    }  
  
    @Override  
    public Consultation getConsultationById(int id) { 
    	log.info("Fetching consultations by ID:{},id");

//        return consultationRepo.findById(id)
//                .orElseThrow(() -> {
//                	log.error("Consultation with ID {} not found",id);
//                	return new ResourceNotFoundException("Consultation with ID" + id + "not found"); 
//                });
    	
    	Consultation consultation = consultationRepo.findById(id)
    		    .orElseGet(() -> {
    		        log.error("Consultation with ID {} not found", id);
    		        return null;
    		    });
		return consultation;

    }  
  
    @Override  
    public List<Consultation> getConsultationsByDoctor(int doctorId) { 
    	log.info("Fetching consultations for Doctor ID:{}",doctorId);
    	List<Consultation> list = consultationRepo.findByDoctorId(doctorId);
    	if(list.isEmpty()) {
    		log.warn("No consultations for Doctor ID:",doctorId);
    		throw new ResourceNotFoundException("No consultation found for DoctorId"+doctorId);
    	}
    	log.info("{} consultations for Doctor ID:{}",list.size(),doctorId);

        return list;  
    }  
  
    @Override  
    public List<Consultation> getConsultationsByPatient(int patientId) { 
    	log.info("Fetching consultations for Patient ID:{}",patientId);
    	List<Consultation> list = consultationRepo.findByPatientId(patientId);
    	if(list.isEmpty()) {
    		log.warn("No consultations for Patient ID:",patientId);
    		throw new ResourceNotFoundException("No consultation found for PatientId"+patientId);
    	}
    	log.info("{} consultations for Patient ID:{}",list.size(),patientId);
        return list;  
    }  
} 





