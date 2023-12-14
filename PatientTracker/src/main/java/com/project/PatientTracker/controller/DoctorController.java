package com.project.PatientTracker.controller;

import com.project.PatientTracker.exception.ResourceNotFoundException;
import com.project.PatientTracker.model.*;
import com.project.PatientTracker.repository.*;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/")
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
    public ResponseEntity<List<Patient>> getPatientsByDoctor(@PathVariable Long id) {
        Set<Patient> patients = doctorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID not found: " + id))
            .getPatients();

        return ResponseEntity.ok(new ArrayList<Patient>(patients));
    }

    // Get All Appointments by Doctor
    @GetMapping("/doctors/{id}/appointments")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctor(@PathVariable Long id) {
        Set<Appointment> appointments = doctorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID not found: " + id))
            .getAppointments();

        return ResponseEntity.ok(new ArrayList<Appointment>(appointments));
    }

    // Get All Files by Doctor
    @GetMapping("/doctors/{id}/files")
    public ResponseEntity<List<File>> getFilesByDoctor(@PathVariable Long id) {
        Set<File> files = doctorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID not found: " + id))
            .getFiles();

        return ResponseEntity.ok(new ArrayList<File>(files));
    }

    // Create Doctor
    @PostMapping("/doctors")
	public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctorRequest) {
        Doctor doctor = (Doctor) new Doctor()
            .setSpecialty(doctorRequest.getSpecialty())
            .setFirstName(doctorRequest.getFirstName())
            .setLastName(doctorRequest.getLastName())
            .setAge(doctorRequest.getAge())
            .setGender(doctorRequest.getGender())
            .setEmail(doctorRequest.getEmail())
            .setPhone(doctorRequest.getPhone());

        return ResponseEntity.ok(doctorRepository.save(doctor));
	}

    // Update Doctor Profile
    @PutMapping("/doctors/{id}")
	public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctorRequest){
		Doctor doctor = doctorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID not found: " + id));
		
		doctor.setSpecialty(doctorRequest.getSpecialty())
            .setFirstName(doctorRequest.getFirstName())
            .setLastName(doctorRequest.getLastName())
            .setAge(doctorRequest.getAge())
            .setGender(doctorRequest.getGender())
            .setEmail(doctorRequest.getEmail())
            .setPhone(doctorRequest.getPhone());

		return ResponseEntity.ok(doctorRepository.save(doctor));
	}

    // Assign Patient to Doctor
    @PutMapping("/doctors/{doctorId}/assign-patient/{patientId}")
	public ResponseEntity<Doctor> assignPatient(@PathVariable Long doctorId, @PathVariable Long patientId){
		Doctor doctor = doctorRepository.findById(doctorId)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID not found: " + doctorId));
            
        Patient patient = patientRepository.findById(patientId)
            .orElseThrow(() -> new ResourceNotFoundException("Patient with ID not found: " + patientId));

        patient.setDoctor(doctor);
        doctor.addPatient(patient);
        		
		return ResponseEntity.ok(doctorRepository.save(doctor));
	}

    // Delete Doctor
	@DeleteMapping("/doctors/{id}")
	public ResponseEntity<Doctor> deleteDoctor(@PathVariable Long id){
		Doctor doctor = doctorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID not found: " + id));
		
		doctorRepository.delete(doctor);
		return ResponseEntity.ok(doctor);
	}
}
