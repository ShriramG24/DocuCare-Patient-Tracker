package com.project.PatientTracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.PatientTracker.model.File;
import com.project.PatientTracker.model.Patient;
import com.project.PatientTracker.model.Doctor;

public interface FileRepository extends JpaRepository<File, Long> {

    public List<File> findByDoctor(Doctor doctor);

    public List<File> findByPatient(Patient patient);
}