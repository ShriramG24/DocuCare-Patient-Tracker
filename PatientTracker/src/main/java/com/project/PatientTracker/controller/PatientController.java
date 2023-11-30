package com.project.PatientTracker.controller;

import com.project.PatientTracker.model.Appointment;
import com.project.PatientTracker.model.Patient;
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
        Patient patient = patientRepository.getReferenceById(id);
        return ResponseEntity.ok(patient);
    }

    // Get All Appointments by Patient
    @GetMapping("/patients/{id}/appointments")
    public ResponseEntity<Set<Appointment>> getAppointmentsByDoctor(@PathVariable Long id) {
        Set<Appointment> appointments = patientRepository.getReferenceById(id).getAppointments();
        return ResponseEntity.ok(appointments);
    }

    // Create Patient
    @PostMapping("/patients")
	public Patient createPatient(@RequestBody Patient patient) {
		return patientRepository.save(patient);
	}

    // Update Patient Profile
    @PutMapping("/patients/{id}")
	public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patientDetails){
		Patient patient = patientRepository.getReferenceById(id);
		
		patient.setFirstName(patientDetails.getFirstName())
            .setLastName(patientDetails.getLastName())
            .setDateOfBirth(patientDetails.getDateOfBirth())
            .setAge(patientDetails.getAge())
            .setEmail(patientDetails.getEmail())
            .setPhone(patientDetails.getPhone());

        patient.setAddress(patientDetails.getAddress())
            .setDoctor(patientDetails.getDoctor());        
		
		Patient updatedPatient = patientRepository.save(patient);
		return ResponseEntity.ok(updatedPatient);
	}

    // Delete Patient
	@DeleteMapping("/patients/{id}")
	public ResponseEntity<Map<String, Boolean>> deletePatient(@PathVariable Long id){
		Patient patient = patientRepository.getReferenceById(id);
		
		patientRepository.delete(patient);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
