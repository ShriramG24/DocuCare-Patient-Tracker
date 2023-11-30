package com.project.PatientTracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.PatientTracker.model.Patient;
import com.project.PatientTracker.model.MedicalRecord;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    
    public List<MedicalRecord> findByPatient(Patient patient);
}
