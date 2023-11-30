package com.project.PatientTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.PatientTracker.model.MedicalRecord;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    
}
