package com.project.PatientTracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.PatientTracker.model.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long>{

    List<Prescription> findByPatientId(Long patientId);
}