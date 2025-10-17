package com.cts.DoctorAvailablityManagement.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.DoctorAvailablityManagement.model.AvailablitySlot;

@Repository
public interface AvailablityRepo extends JpaRepository<AvailablitySlot, Integer>{
	List<AvailablitySlot> findByDoctorId(int doctorId);

}
