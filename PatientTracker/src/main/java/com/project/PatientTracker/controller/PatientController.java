package com.project.PatientTracker.controller;

import com.project.PatientTracker.exception.ResourceNotFoundException;
import com.project.PatientTracker.model.*;
import com.project.PatientTracker.repository.PatientRepository;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/")
@PreAuthorize("hasRole('ROLE_PATIENT')")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    // Get All Patients
    /**
     * @return List of patients
     */
    @GetMapping("/patients")
    public ResponseEntity<List<Patient>> getPatients() {
        List<Patient> patients = patientRepository.findAll();

        return ResponseEntity.ok(patients);
    }

    // Get Patient by ID
    /**
     * @param id Patient ID
     * @return Patient object 
     */
    @GetMapping("/patients/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Patient patient = patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient with ID not found: " + id));

        return ResponseEntity.ok(patient);
    }

    // Get All Appointments by Patient
    /**
     * @param id Patient ID
     * @return List of appointments by patient
     */
    @GetMapping("/patients/{id}/appointments")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctor(@PathVariable Long id) {
        Set<Appointment> appointments = patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient with ID not found: " + id))
            .getAppointments();

        return ResponseEntity.ok(new ArrayList<Appointment>(appointments));
    }

    // Get All Files by Patient
    /**
     * @param id Patient ID
     * @return List of files uploaded by patient
     */
    @GetMapping("/patients/{id}/files")
    public ResponseEntity<List<File>> getRecordsByDoctor(@PathVariable Long id) {
        Set<File> files = patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient with ID not found: " + id))
            .getFiles();

        return ResponseEntity.ok(new ArrayList<File>(files));
    }

    // Create Patient
    /**
     * @param patientRequest Patient object
     * @return Created Patient object
     */
    @PostMapping("/patients")
	public ResponseEntity<Patient> createPatient(@RequestBody Patient patientRequest) {
		Patient patient = (Patient) new Patient()
            .setDiagnoses(patientRequest.getDiagnoses())
            .setMedications(patientRequest.getMedications())
            .setAllergies(patientRequest.getAllergies())
            .setFirstName(patientRequest.getFirstName())
            .setLastName(patientRequest.getLastName())
            .setAge(patientRequest.getAge())
            .setGender(patientRequest.getGender())
            .setEmail(patientRequest.getEmail())
            .setPhone(patientRequest.getPhone())
            .setPassword(patientRequest.getPassword());
        
        return ResponseEntity.ok(patientRepository.save(patient));
	}

    // Update Patient Profile
    /**
     * @param id Patient ID
     * @param patientRequest Patient object
     * @return Updated Patient object
     */
    @PutMapping("/patients/{id}")
	public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patientRequest){
		Patient patient = patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient with ID not found: " + id));
		
		patient.setDiagnoses(patientRequest.getDiagnoses())
            .setMedications(patientRequest.getMedications())
            .setAllergies(patientRequest.getAllergies())
            .setFirstName(patientRequest.getFirstName())
            .setLastName(patientRequest.getLastName())
            .setAge(patientRequest.getAge())
            .setGender(patientRequest.getGender())
            .setEmail(patientRequest.getEmail())
            .setPhone(patientRequest.getPhone())
            .setPassword(patientRequest.getPassword());
		
        return ResponseEntity.ok(patientRepository.save(patient));
	}

    // Delete Patient
	/**
	 * @param id Patient ID
	 * @return Deleted Patient object
	 */
	@DeleteMapping("/patients/{id}")
	public ResponseEntity<Patient> deletePatient(@PathVariable Long id){
		Patient patient = patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient with ID not found: " + id));
		
		patientRepository.delete(patient);
		return ResponseEntity.ok(patient);
	}
}
