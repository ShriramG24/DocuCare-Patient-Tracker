package com.project.PatientTracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.PatientTracker.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{
    
    public List<Doctor> findBySpecialty(String specialty);

    public List<Doctor> findByRatingGreaterThanEqual(Double rating);

    Optional<Doctor> findByEmail(String email);

    Boolean existsByEmail(String username);
}