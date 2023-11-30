package com.project.PatientTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.PatientTracker.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>{
    
}