package com.project.PatientTracker.controller;

import com.project.PatientTracker.exception.ResourceNotFoundException;
import com.project.PatientTracker.model.*;
import com.project.PatientTracker.repository.PatientRepository;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    // Get All Patients
    @GetMapping("/patients")
    public ResponseEntity<List<Patient>> getPatients() {
        List<Patient> patients = patientRepository.findAll();

        return ResponseEntity.ok(patients);
    }

    // Get Patient by ID
    @GetMapping("/patients/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Patient patient = patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient with ID not found: " + id));

        return ResponseEntity.ok(patient);
    }

    // Get All Appointments by Patient
    @GetMapping("/patients/{id}/appointments")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctor(@PathVariable Long id) {
        Set<Appointment> appointments = patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient with ID not found: " + id))
            .getAppointments();

        return ResponseEntity.ok(new ArrayList<Appointment>(appointments));
    }

    // Get All Files by Patient
    @GetMapping("/patients/{id}/files")
    public ResponseEntity<List<File>> getRecordsByDoctor(@PathVariable Long id) {
        Set<File> files = patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient with ID not found: " + id))
            .getFiles();

        return ResponseEntity.ok(new ArrayList<File>(files));
    }

    // Create Patient
    @PostMapping("/patients")
	public ResponseEntity<Patient> createPatient(@RequestBody Patient patientRequest) {
		Patient patient = (Patient) new Patient().setAddress(patientRequest.getAddress())
            .setDiagnoses(patientRequest.getDiagnoses())
            .setMedications(patientRequest.getMedications())
            .setAllergies(patientRequest.getAllergies())
            .setFirstName(patientRequest.getFirstName())
            .setLastName(patientRequest.getLastName())
            .setAge(patientRequest.getAge())
            .setGender(patientRequest.getGender())
            .setEmail(patientRequest.getEmail())
            .setPhone(patientRequest.getPhone());
        
        return ResponseEntity.ok(patientRepository.save(patient));
	}

    // Update Patient Profile
    @PutMapping("/patients/{id}")
	public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patientRequest){
		Patient patient = patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient with ID not found: " + id));
		
		patient.setAddress(patientRequest.getAddress())
            .setDiagnoses(patientRequest.getDiagnoses())
            .setMedications(patientRequest.getMedications())
            .setAllergies(patientRequest.getAllergies())
            .setFirstName(patientRequest.getFirstName())
            .setLastName(patientRequest.getLastName())
            .setAge(patientRequest.getAge())
            .setGender(patientRequest.getGender())
            .setEmail(patientRequest.getEmail())
            .setPhone(patientRequest.getPhone());
		
        return ResponseEntity.ok(patientRepository.save(patient));
	}

    // Delete Patient
	@DeleteMapping("/patients/{id}")
	public ResponseEntity<Patient> deletePatient(@PathVariable Long id){
		Patient patient = patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient with ID not found: " + id));
		
		patientRepository.delete(patient);
		return ResponseEntity.ok(patient);
	}
}
