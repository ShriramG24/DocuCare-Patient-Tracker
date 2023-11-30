package com.project.PatientTracker.controller;

import com.project.PatientTracker.exception.ResourceNotFoundException;
import com.project.PatientTracker.model.Appointment;
import com.project.PatientTracker.model.Doctor;
import com.project.PatientTracker.model.File;
import com.project.PatientTracker.model.Patient;
import com.project.PatientTracker.payload.request.DoctorRequest;
import com.project.PatientTracker.repository.DoctorRepository;
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
public class DoctorController {
    
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    // Get All Doctors
    @GetMapping("/doctors")
    public ResponseEntity<List<Doctor>> getDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        return ResponseEntity.ok(doctors);
    }

    // Get Doctor by ID
    @GetMapping("/doctors/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        Doctor doctor = doctorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID not found: " + id));
        return ResponseEntity.ok(doctor);
    }

    // Get All Patients by Doctor
    @GetMapping("/doctors/{id}/patients")
    public ResponseEntity<Set<Patient>> getPatientsByDoctor(@PathVariable Long id) {
        Set<Patient> patients = doctorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID not found: " + id))
            .getPatients();
        return ResponseEntity.ok(patients);
    }

    // Get All Appointments by Doctor
    @GetMapping("/doctors/{id}/appointments")
    public ResponseEntity<Set<Appointment>> getAppointmentsByDoctor(@PathVariable Long id) {
        Set<Appointment> appointments = doctorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID not found: " + id))
            .getAppointments();
        return ResponseEntity.ok(appointments);
    }

    // Get All Files by Doctor
    @GetMapping("/doctors/{id}/files")
    public ResponseEntity<Set<File>> getFilesByDoctor(@PathVariable Long id) {
        Set<File> files = doctorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID not found: " + id))
            .getFiles();
        return ResponseEntity.ok(files);
    }

    // Create Doctor
    @PostMapping("/doctors")
	public ResponseEntity<Doctor> createDoctor(@RequestBody DoctorRequest doctorRequest) {
        Doctor doctor = (Doctor) new Doctor()
            .setSpecialty(doctorRequest.getSpecialty())
            .setFirstName(doctorRequest.getFirstName())
            .setLastName(doctorRequest.getLastName())
            .setDateOfBirth(doctorRequest.getDateOfBirth())
            .setAge(doctorRequest.getAge())
            .setEmail(doctorRequest.getEmail())
            .setPhone(doctorRequest.getPhone());

        return ResponseEntity.ok(doctorRepository.save(doctor));
	}

    // Update Doctor Profile
    @PutMapping("/doctors/{id}")
	public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody DoctorRequest doctorRequest){
		Doctor doctor = doctorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID not found: " + id));
		
		doctor.setSpecialty(doctorRequest.getSpecialty())
            .setFirstName(doctorRequest.getFirstName())
            .setLastName(doctorRequest.getLastName())
            .setDateOfBirth(doctorRequest.getDateOfBirth())
            .setAge(doctorRequest.getAge())
            .setEmail(doctorRequest.getEmail())
            .setPhone(doctorRequest.getPhone());

		Doctor updatedDoctor = doctorRepository.save(doctor);
		return ResponseEntity.ok(updatedDoctor);
	}

    // Assign Patient to Doctor
    @PutMapping("/doctors/{doctorId}/assign-patient/{patientId}")
	public ResponseEntity<Doctor> assignPatient(@PathVariable Long doctorId, @PathVariable Long patientId){
		Doctor doctor = doctorRepository.findById(doctorId)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID not found: " + doctorId));
            
        Patient patient = patientRepository.findById(patientId)
            .orElseThrow(() -> new ResourceNotFoundException("Patient with ID not found: " + patientId));

        patient.setDoctor(doctor);
        		
		Doctor updatedDoctor = doctorRepository.save(doctor);
		return ResponseEntity.ok(updatedDoctor);
	}

    // Delete Doctor
	@DeleteMapping("/doctors/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteDoctor(@PathVariable Long id){
		Doctor doctor = doctorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID not found: " + id));
		
		doctorRepository.delete(doctor);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
