package com.project.PatientTracker.controller;

import com.project.PatientTracker.exception.ResourceNotFoundException;
import com.project.PatientTracker.model.Appointment;
import com.project.PatientTracker.model.MedicalRecord;
import com.project.PatientTracker.model.Patient;
import com.project.PatientTracker.payload.request.PatientRequest;
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
            .getAppointments();
        return ResponseEntity.ok(appointments);
    }

    // Get All Medical Records by Patient
    @GetMapping("/patients/{id}/records")
    public ResponseEntity<Set<MedicalRecord>> getRecordsByDoctor(@PathVariable Long id) {
        Set<MedicalRecord> records = patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient with ID not found: " + id))
            .getRecords();
        return ResponseEntity.ok(records);
    }

    // Create Patient
    @PostMapping("/patients")
	public ResponseEntity<Patient> createPatient(@RequestBody PatientRequest patientRequest) {
		Patient patient = (Patient) new Patient().setAddress(patientRequest.getAddress())
            .setFirstName(patientRequest.getFirstName())
            .setLastName(patientRequest.getLastName())
            .setDateOfBirth(patientRequest.getDateOfBirth())
            .setAge(patientRequest.getAge())
            .setEmail(patientRequest.getEmail())
            .setPhone(patientRequest.getPhone());
            
        return ResponseEntity.ok(patientRepository.save(patient));
	}

    // Update Patient Profile
    @PutMapping("/patients/{id}")
	public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody PatientRequest patientRequest){
		Patient patient = patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient with ID not found: " + id));
		
		patient.setAddress(patientRequest.getAddress())
            .setFirstName(patientRequest.getFirstName())
            .setLastName(patientRequest.getLastName())
            .setDateOfBirth(patientRequest.getDateOfBirth())
            .setAge(patientRequest.getAge())
            .setEmail(patientRequest.getEmail())
            .setPhone(patientRequest.getPhone());
		
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
