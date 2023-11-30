package com.project.PatientTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.PatientTracker.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{
    
}