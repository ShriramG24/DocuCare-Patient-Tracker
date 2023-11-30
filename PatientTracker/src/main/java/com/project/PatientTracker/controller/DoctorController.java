package com.project.PatientTracker.controller;

import com.project.PatientTracker.exception.ResourceNotFoundException;
import com.project.PatientTracker.model.Appointment;
import com.project.PatientTracker.model.Doctor;
import com.project.PatientTracker.model.File;
import com.project.PatientTracker.model.Patient;
import com.project.PatientTracker.payload.request.DoctorRequest;
import com.project.PatientTracker.payload.response.AppointmentResponse;
import com.project.PatientTracker.payload.response.DoctorResponse;
import com.project.PatientTracker.payload.response.FileResponse;
import com.project.PatientTracker.payload.response.PatientResponse;
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
    public ResponseEntity<List<DoctorResponse>> getDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();

        List<DoctorResponse> response = doctors.stream().map(d -> {
            return new DoctorResponse().build(d);
        }).toList();
        return ResponseEntity.ok(response);
    }

    // Get Doctor by ID
    @GetMapping("/doctors/{id}")
    public ResponseEntity<DoctorResponse> getDoctorById(@PathVariable Long id) {
        Doctor doctor = doctorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID not found: " + id));

        DoctorResponse response = new DoctorResponse().build(doctor);
        return ResponseEntity.ok(response);
    }

    // Get All Patients by Doctor
    @GetMapping("/doctors/{id}/patients")
    public ResponseEntity<List<PatientResponse>> getPatientsByDoctor(@PathVariable Long id) {
        Set<Patient> patients = doctorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID not found: " + id))
            .getPatients();

        List<PatientResponse> response = patients.stream().map(p -> {
            return new PatientResponse().build(p);
        }).toList();
        return ResponseEntity.ok(response);
    }

    // Get All Appointments by Doctor
    @GetMapping("/doctors/{id}/appointments")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsByDoctor(@PathVariable Long id) {
        Set<Appointment> appointments = doctorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID not found: " + id))
            .getAppointments();

        List<AppointmentResponse> response = appointments.stream().map(a -> {
            return new AppointmentResponse().build(a);
        }).toList();
        return ResponseEntity.ok(response);
    }

    // Get All Files by Doctor
    @GetMapping("/doctors/{id}/files")
    public ResponseEntity<List<FileResponse>> getFilesByDoctor(@PathVariable Long id) {
        Set<File> files = doctorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID not found: " + id))
            .getFiles();

        List<FileResponse> response = files.stream().map(f -> {
            return new FileResponse().build(f);
        }).toList();
        return ResponseEntity.ok(response);
    }

    // Create Doctor
    @PostMapping("/doctors")
	public ResponseEntity<DoctorResponse> createDoctor(@RequestBody DoctorRequest doctorRequest) {
        Doctor doctor = (Doctor) new Doctor()
            .setSpecialty(doctorRequest.getSpecialty())
            .setFirstName(doctorRequest.getFirstName())
            .setLastName(doctorRequest.getLastName())
            .setDateOfBirth(doctorRequest.getDateOfBirth())
            .setAge(doctorRequest.getAge())
            .setEmail(doctorRequest.getEmail())
            .setPhone(doctorRequest.getPhone());

        DoctorResponse response = new DoctorResponse().build(doctorRepository.save(doctor));
        return ResponseEntity.ok(response);
	}

    // Update Doctor Profile
    @PutMapping("/doctors/{id}")
	public ResponseEntity<DoctorResponse> updateDoctor(@PathVariable Long id, @RequestBody DoctorRequest doctorRequest){
		Doctor doctor = doctorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID not found: " + id));
		
		doctor.setSpecialty(doctorRequest.getSpecialty())
            .setFirstName(doctorRequest.getFirstName())
            .setLastName(doctorRequest.getLastName())
            .setDateOfBirth(doctorRequest.getDateOfBirth())
            .setAge(doctorRequest.getAge())
            .setEmail(doctorRequest.getEmail())
            .setPhone(doctorRequest.getPhone());

		DoctorResponse response = new DoctorResponse().build(doctorRepository.save(doctor));
		return ResponseEntity.ok(response);
	}

    // Assign Patient to Doctor
    @PutMapping("/doctors/{doctorId}/assign-patient/{patientId}")
	public ResponseEntity<DoctorResponse> assignPatient(@PathVariable Long doctorId, @PathVariable Long patientId){
		Doctor doctor = doctorRepository.findById(doctorId)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID not found: " + doctorId));
            
        Patient patient = patientRepository.findById(patientId)
            .orElseThrow(() -> new ResourceNotFoundException("Patient with ID not found: " + patientId));

        patient.setDoctor(doctor);
        		
		DoctorResponse response = new DoctorResponse().build(doctorRepository.save(doctor));
		return ResponseEntity.ok(response);
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
