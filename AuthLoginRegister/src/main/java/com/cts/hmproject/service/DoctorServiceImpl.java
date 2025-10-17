package com.cts.hmproject.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.cts.hmproject.model.Doctor;
import com.cts.hmproject.model.DoctorDTO;
import com.cts.hmproject.repository.DoctorRepo;

//import com.cts.hmproject.model.Doctor;
//import com.cts.hmproject.repository.UserRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;


@Service
//@AllArgsConstructor
public class DoctorServiceImpl implements DoctorService {

	@Autowired
	DoctorRepo repo;
	
	@Autowired
    private JWTService jwtService;
 
    @Autowired
    AuthenticationManager authManager;
	
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	
	@Override
	public Doctor addUser(Doctor d) {
		d.setDoctorPassword(encoder.encode(d.getDoctorPassword()));
		Doctor rp = repo.save(d);
		return rp;
	}
	
	@Override
	public Map<String, Object> verify(Doctor doctor) {
		Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(doctor.getDoctorEmail(),doctor.getDoctorPassword()));
        if (authentication.isAuthenticated()) {
        	List<String> roles = List.of("ROLE_DOCTOR");
           String token =  jwtService.generateToken(doctor.getDoctorEmail(),roles);
           Doctor foundDoctor = repo.findByDoctorEmail(doctor.getDoctorEmail());
           Map<String, Object> response = new HashMap<>();
           response.put("token", token);
           response.put("doctorId", foundDoctor.getDoctorId());
           response.put("doctorName", foundDoctor.getDoctorName());
           response.put("doctorEmail", doctor.getDoctorEmail());
           System.out.println(response);
           return response;
           
        } else {
            throw new RuntimeException("Invalid credentials");
        }
	}
	
//	public String verify(Doctor ) {
//        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getDoctorEmail(), user.getDoctorPassword()));
//        if (authentication.isAuthenticated()) {
//            return jwtService.generateToken(user.getDoctorEmail());
//        } else {
//            return "fail";
//        }
//    }

	@Override
	public Doctor addProfile(String doctorEmail,Doctor up) {
		// TODO Auto-generated method stub
		Optional<Doctor> optDoctor = repo.findOptionalByDoctorEmail(doctorEmail);
//      	Optional<Doctor> optDoctor = repo.findById(doctorEmail);
	
		if(optDoctor!=null)
		{
			Doctor doctor = optDoctor.get();
			
			doctor.setSpecialization(up.getSpecialization());
			doctor.setQualification(up.getQualification());
			doctor.setExperience(up.getExperience());
//			doctor.setClinicName(up.getClinicName());
			doctor.setAddress(up.getAddress());
			doctor.setAbout(up.getAbout());
			System.out.println(doctor);
			
			return repo.save(doctor);
		}
		else
		{
			System.out.println("Null Element in doctor");
			return null;
		}
	}

	@Override
	public Doctor getProfile(String doctorEmail) {
		// TODO Auto-generated method stu
		Doctor d=repo.findByDoctorEmail(doctorEmail);
		return d;
		
	}

	@Override
	public DoctorDTO getDoctorById(int id) {
		// TODO Auto-generated method stub
		Optional<Doctor> d = repo.findById(id);
		Doctor doctor = d.get();
		DoctorDTO dt = new DoctorDTO(doctor); 
		return dt;
	}

	@Override
	public List<DoctorDTO> getAllDoctors() {
	    List<Doctor> doctors = repo.findAll();

	    List<DoctorDTO> found = doctors.stream()
	                                   .map(doctor -> new DoctorDTO(doctor)) // assuming DoctorDTO has a constructor that accepts Doctor
	                                   .collect(Collectors.toList());

	    return found;
	}

	
	

//	@Override
//	public List<Doctor> getUser() {
//		List<Doctor> rplist = repo.findAll();
//		return rplist;
//	}

}
