package com.healthcare.Consultations.service;
 
import com.healthcare.Consultations.dto.ConsultationDTO;
import com.healthcare.Consultations.dto.DoctorDTO;
import com.healthcare.Consultations.dto.PatientDTO;
import com.healthcare.Consultations.exception.ResourceNotFoundException;
import com.healthcare.Consultations.model.Consultation;
import com.healthcare.Consultations.repository.ConsultationRepo;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
   
import java.time.LocalDate;
import java.util.*;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
@SpringBootTest
class ConsultationServiceTest {
 
    @Mock
    private ConsultationRepo consultationRepo;
 
    @Mock
    private UserClient userClient;
 
    @InjectMocks
    private ConsultationServiceImpl consultationService;
 
    private ConsultationDTO consultationDTO;
 
    @BeforeEach
    void setUp() {
        consultationDTO = ConsultationDTO.builder()
                .appointmentId(101)
                .doctorId(1)
                .patientId(2)
                .doctorName("Dr. Ajay")
                .patientName("Arjun")
                .date(LocalDate.now())
                .notes("Follow-up required")
                .build();
    }
 
    // Test 1: Successful saveConsultation()
    @Test
    void testSaveConsultation_Success() {
        DoctorDTO doctor = new DoctorDTO(1, "Dr. Adam", "Cardiology", "MBBS-MD", 10);
        PatientDTO patient = new PatientDTO(2, "Alice", LocalDate.of(2025, 11, 07), "Female", "O+");
 
        consultationDTO.setDoctor(doctor);
        consultationDTO.setPatient(patient);
 
        Consultation consultation = new Consultation();
        consultation.setConsultationId(1);
 
        when(userClient.getDoctorById(1)).thenReturn(doctor);
        when(userClient.getPatientById(2)).thenReturn(patient);
        when(consultationRepo.save(any(Consultation.class))).thenReturn(consultation);
 
        Consultation saved = consultationService.saveConsultation(consultationDTO);
 
        assertNotNull(saved);
        assertEquals(1, saved.getConsultationId());
        verify(consultationRepo, times(1)).save(any(Consultation.class));
    }

 
    // Test 2: getAllConsultations() returns list
    @Test
    void testGetAllConsultations_Success() {
        List<Consultation> consultations = List.of(new Consultation(), new Consultation());
        when(consultationRepo.findAll()).thenReturn(consultations);
 
        List<Consultation> result = consultationService.getAllConsultations();
 
        assertEquals(2, result.size());
        verify(consultationRepo, times(1)).findAll();
    }
 
    // Test 3: getAllConsultations() throws ResourceNotFoundException when empty
    @Test
    void testGetAllConsultations_Empty() {
        when(consultationRepo.findAll()).thenReturn(Collections.emptyList());
        assertThrows(ResourceNotFoundException.class, () -> consultationService.getAllConsultations());
    }
 
    // Test 4: getConsultationsByDoctor()
    @Test
    void testGetConsultationsByDoctor_Success() {
        List<Consultation> consultations = List.of(new Consultation());
        when(consultationRepo.findByDoctorId(1)).thenReturn(consultations);
 
        List<Consultation> result = consultationService.getConsultationsByDoctor(1);
        assertFalse(result.isEmpty());
    }
 
    // Test 5: getConsultationsByDoctor() throws exception when empty
    @Test
    void testGetConsultationsByDoctor_Empty() {
        when(consultationRepo.findByDoctorId(1)).thenReturn(Collections.emptyList());
        assertThrows(ResourceNotFoundException.class, () -> consultationService.getConsultationsByDoctor(1));
    }
 
    // Test 6: getConsultationById() returns consultation
    @Test
    void testGetConsultationById_Success() {
        Consultation consultation = new Consultation();
        consultation.setConsultationId(1);
        when(consultationRepo.findById(1)).thenReturn(Optional.of(consultation));
 
        Consultation result = consultationService.getConsultationById(1);
        assertNotNull(result);
        assertEquals(1, result.getConsultationId());
    }
 
    // Test 7: getConsultationById() returns null when not found
    @Test
    void testGetConsultationById_NotFound() {
        when(consultationRepo.findById(999)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> {
        	consultationService.getConsultationById(999);
        });
    	}
    
}
 