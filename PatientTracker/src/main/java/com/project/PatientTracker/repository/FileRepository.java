package com.project.PatientTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.PatientTracker.model.File;

public interface FileRepository extends JpaRepository<File, Long> {
    
}