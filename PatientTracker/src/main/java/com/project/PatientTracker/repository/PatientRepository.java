package com.project.PatientTracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.PatientTracker.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>{
    Optional<Patient> findByEmail(String email);

    Boolean existsByEmail(String username);
}