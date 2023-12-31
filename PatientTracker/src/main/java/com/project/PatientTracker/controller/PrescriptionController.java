package com.project.PatientTracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.project.PatientTracker.model.Prescription;
import com.project.PatientTracker.repository.PrescriptionRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/")
@PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_PATIENT')")
public class PrescriptionController {
    @Autowired
    private PrescriptionRepository prescriptionRepository;

    // Get All Prescriptions by Patient
    /**
     * @param patientId Patient ID
     * @return List of prescriptions by patient
     */
    @GetMapping("/prescriptions/patients/{patientId}")
    public ResponseEntity<List<Prescription>> getPrescriptionsByPatient(@PathVariable Long patientId) {
        List<Prescription> prescriptions = prescriptionRepository.findByPatientId(patientId);

        return ResponseEntity.ok(prescriptions);
    }

    // Create Prescription
    /**
     * @param prescriptionRequest Prescription object
     * @return Created Prescription object
     */
    @PostMapping("/prescriptions")
    public ResponseEntity<Prescription> createPrescription(@RequestBody Prescription prescriptionRequest) {
        Prescription prescription = new Prescription()
            .setDoctor(prescriptionRequest.getDoctor())
            .setPatient(prescriptionRequest.getPatient())
            .setMedicines(prescriptionRequest.getMedicines())
            .setReports(prescriptionRequest.getReports())
            .setInstructions(prescriptionRequest.getInstructions());

        return ResponseEntity.ok(prescriptionRepository.save(prescription));
    }

    // Delete Prescription
    /**
     * @param patientId Patient ID
     * @return Deleted Prescription object
     */
    @DeleteMapping("/prescriptions/patients/{patientId}")
    public ResponseEntity<Prescription> deletePrescription(@PathVariable Long patientId) {
        Prescription prescription = prescriptionRepository.findByPatientId(patientId).get(0);

        prescriptionRepository.delete(prescription);
        return ResponseEntity.ok(prescription);
    }
}
