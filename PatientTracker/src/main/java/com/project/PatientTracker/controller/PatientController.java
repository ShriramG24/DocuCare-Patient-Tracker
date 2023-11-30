package com.project.PatientTracker.controller;

import com.project.PatientTracker.exception.ResourceNotFoundException;
import com.project.PatientTracker.model.Appointment;
import com.project.PatientTracker.model.MedicalRecord;
import com.project.PatientTracker.model.Patient;
import com.project.PatientTracker.payload.request.PatientRequest;
import com.project.PatientTracker.repository.DoctorRepository;
import com.project.PatientTracker.repository.MedicalRecordRepository;
import com.project.PatientTracker.repository.PatientRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

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
    public ResponseEntity<Set<Appointment>> getAppointmentsByDoctor(@PathVariable Long id) {
        Set<Appointment> appointments = patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient with ID not found: " + id))
            .appointments();
        return ResponseEntity.ok(appointments);
    }

    // Create Patient
    @PostMapping("/patients")
	public ResponseEntity<Patient> createPatient(@RequestBody PatientRequest patientRequest) {
		Patient patient = new Patient();
        
        patient.firstName(patientRequest.firstName())
            .lastName(patientRequest.lastName())
            .dateOfBirth(patientRequest.dateOfBirth())
            .age(patientRequest.age())
            .email(patientRequest.email())
            .phone(patientRequest.phone());

        patient.address(patientRequest.address())
            .doctor(doctorRepository.findById(patientRequest.doctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID not found: " + patientRequest.doctorId()))
            );
            
        return ResponseEntity.ok(patientRepository.save(patient));
	}

    // Update Patient Profile
    @PutMapping("/patients/{id}")
	public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody PatientRequest patientRequest){
		Patient patient = patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient with ID not found: " + id));
		
		patient.firstName(patientRequest.firstName())
            .lastName(patientRequest.lastName())
            .dateOfBirth(patientRequest.dateOfBirth())
            .age(patientRequest.age())
            .email(patientRequest.email())
            .phone(patientRequest.phone());

        patient.address(patientRequest.address())
            .doctor(doctorRepository.findById(patientRequest.doctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID not found: " + patientRequest.doctorId()))
            );
		
		Patient updatedPatient = patientRepository.save(patient);
		return ResponseEntity.ok(updatedPatient);
	}

    // Add Medical Record to Patient's View
    @PutMapping("/patients/{id}/add-record")
	public ResponseEntity<Patient> addRecord(@PathVariable Long id, @RequestBody MedicalRecord recordDetails){
		Patient patient = patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient with ID not found: " + id));
		
        recordDetails.patient(patient);
		patient.addRecord(recordDetails);
        medicalRecordRepository.save(recordDetails);
        		
		Patient updatedPatient = patientRepository.save(patient);
		return ResponseEntity.ok(updatedPatient);
	}

    // Remove Medical Record from Patient's View
    @PutMapping("/patients/{id}/remove-record")
	public ResponseEntity<Patient> removeRecord(@PathVariable Long id, @RequestBody MedicalRecord recordDetails){
		Patient patient = patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient with ID not found: " + id));
		
		patient.removeRecord(recordDetails);
        medicalRecordRepository.delete(recordDetails);
        		
		Patient updatedPatient = patientRepository.save(patient);
		return ResponseEntity.ok(updatedPatient);
	}

    // Delete Patient
	@DeleteMapping("/patients/{id}")
	public ResponseEntity<Map<String, Boolean>> deletePatient(@PathVariable Long id){
		Patient patient = patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient with ID not found: " + id));
		
		patientRepository.delete(patient);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
