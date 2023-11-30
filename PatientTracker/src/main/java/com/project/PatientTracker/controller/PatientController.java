package com.project.PatientTracker.controller;

import com.project.PatientTracker.exception.ResourceNotFoundException;
import com.project.PatientTracker.model.Appointment;
import com.project.PatientTracker.model.MedicalRecord;
import com.project.PatientTracker.model.Patient;
import com.project.PatientTracker.payload.request.PatientRequest;
import com.project.PatientTracker.payload.response.AppointmentResponse;
import com.project.PatientTracker.payload.response.MedicalRecordResponse;
import com.project.PatientTracker.payload.response.PatientResponse;
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
    public ResponseEntity<List<PatientResponse>> getPatients() {
        List<Patient> patients = patientRepository.findAll();

        List<PatientResponse> response = patients.stream().map(p -> {
            return new PatientResponse().build(p);
        }).toList();
        return ResponseEntity.ok(response);
    }

    // Get Patient by ID
    @GetMapping("/patients/{id}")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable Long id) {
        Patient patient = patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient with ID not found: " + id));

        PatientResponse response = new PatientResponse().build(patient);
        return ResponseEntity.ok(response);
    }

    // Get All Appointments by Patient
    @GetMapping("/patients/{id}/appointments")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsByDoctor(@PathVariable Long id) {
        Set<Appointment> appointments = patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient with ID not found: " + id))
            .getAppointments();

        List<AppointmentResponse> response = appointments.stream().map(a -> {
            return new AppointmentResponse().build(a);
        }).toList();
        return ResponseEntity.ok(response);
    }

    // Get All Medical Records by Patient
    @GetMapping("/patients/{id}/records")
    public ResponseEntity<List<MedicalRecordResponse>> getRecordsByDoctor(@PathVariable Long id) {
        Set<MedicalRecord> records = patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient with ID not found: " + id))
            .getRecords();

        List<MedicalRecordResponse> response = records.stream().map(r -> {
            return new MedicalRecordResponse().build(r);
        }).toList();
        return ResponseEntity.ok(response);
    }

    // Create Patient
    @PostMapping("/patients")
	public ResponseEntity<PatientResponse> createPatient(@RequestBody PatientRequest patientRequest) {
		Patient patient = (Patient) new Patient().setAddress(patientRequest.getAddress())
            .setFirstName(patientRequest.getFirstName())
            .setLastName(patientRequest.getLastName())
            .setDateOfBirth(patientRequest.getDateOfBirth())
            .setAge(patientRequest.getAge())
            .setEmail(patientRequest.getEmail())
            .setPhone(patientRequest.getPhone());
        
        PatientResponse response = new PatientResponse().build(patientRepository.save(patient));
        return ResponseEntity.ok(response);
	}

    // Update Patient Profile
    @PutMapping("/patients/{id}")
	public ResponseEntity<PatientResponse> updatePatient(@PathVariable Long id, @RequestBody PatientRequest patientRequest){
		Patient patient = patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient with ID not found: " + id));
		
		patient.setAddress(patientRequest.getAddress())
            .setFirstName(patientRequest.getFirstName())
            .setLastName(patientRequest.getLastName())
            .setDateOfBirth(patientRequest.getDateOfBirth())
            .setAge(patientRequest.getAge())
            .setEmail(patientRequest.getEmail())
            .setPhone(patientRequest.getPhone());
		
		PatientResponse response = new PatientResponse().build(patientRepository.save(patient));
        return ResponseEntity.ok(response);
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
