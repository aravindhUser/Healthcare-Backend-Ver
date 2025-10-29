package com.cts.hmproject.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import com.cts.hmproject.model.Patient;
import com.cts.hmproject.model.PatientDTO;
import com.cts.hmproject.repository.PatientRepo;

//import lombok.AllArgsConstructor;

@Service
//@AllArgsConstructor
public class PatientServiceImpl implements PatientService {

	@Autowired
	PatientRepo repo;
	
	@Autowired
    private JWTService jwtService;
 
    @Autowired
    AuthenticationManager authManager;
	
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	
	@Override
	public Patient addUser(Patient rpage) {
		rpage.setPatientPassword(encoder.encode(rpage.getPatientPassword()));
		Patient rp = repo.save(rpage);
		return rp;
	}

	@Override
	public Map<String, Object> verify(Patient p) {
		Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(p.getPatientEmail(),p.getPatientPassword()));
        if (authentication.isAuthenticated()) {
        	List<String> roles = List.of("ROLE_PATIENT");   
           String token =  jwtService.generateToken(p.getPatientEmail(),roles);
           Patient foundPatient = repo.findByPatientEmail(p.getPatientEmail());
           Map<String, Object> response = new HashMap<>();
           response.put("token", token);
           response.put("patientId", foundPatient.getPatientId());
           response.put("patientName", foundPatient.getPatientName());
           response.put("patientEmail", p.getPatientEmail());
           System.out.println(response);
           return response;
           
        } else {
            throw new RuntimeException("Invalid credentials");
        }
	}

	@Override
	public Patient addProfile(String patientEmail, Patient p) {
		Optional<Patient> optPatient = repo.findOptionalByPatientEmail(patientEmail);
//  	Optional<Doctor> optDoctor = repo.findById(doctorEmail);

	if(optPatient!=null)
	{
		Patient p1 = optPatient.get();
		
		p1.setDob(p.getDob());
		p1.setGender(p.getGender());
		p1.setBloodgroup(p.getBloodgroup());
//		doctor.setClinicName(up.getClinicName());
		p1.setAddress(p.getAddress());
		p1.setEmergencyContact(p.getEmergencyContact());
		
		return repo.save(p1);
	}
	else
	{
		System.out.println("Null Element in patient");
		return null;
	}
	}

	@Override
	public Patient getProfile(String patientEmail) {
		Patient p=repo.findByPatientEmail(patientEmail);
		return p;
	}

	@Override
	public PatientDTO getPatientProfile(int id) {
		Optional<Patient> dt = repo.findById(id);
		Patient pt = dt.get();
		PatientDTO transferObj =  new PatientDTO(pt);
		return transferObj;
		
		
		
//		return repo.findById(id);
	}

}
