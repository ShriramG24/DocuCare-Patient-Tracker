package com.project.PatientTracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.PatientTracker.exception.ResourceNotFoundException;

import com.project.PatientTracker.model.MedicalRecord;
import com.project.PatientTracker.model.Patient;
import com.project.PatientTracker.payload.response.MedicalRecordResponse;
import com.project.PatientTracker.repository.MedicalRecordRepository;
import com.project.PatientTracker.repository.PatientRepository;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/")
public class MedicalRecordController {
    
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private PatientRepository patientRepository;

    // Get All MedicalRecords by Patient ID
    @GetMapping("/medicalRecords/{patientId}")
    public ResponseEntity<List<MedicalRecordResponse>> getFiles(@PathVariable Long patientId) {
        Patient patient = patientRepository.findById(patientId)
            .orElseThrow(() -> new ResourceNotFoundException("Patient with ID not found: " + patientId));

        List<MedicalRecord> records = medicalRecordRepository.findByPatient(patient);

        List<MedicalRecordResponse> response = records.stream().map(mr -> {
            return new MedicalRecordResponse().build(mr);
        }).toList();
        return ResponseEntity.ok(response);
    }
}
