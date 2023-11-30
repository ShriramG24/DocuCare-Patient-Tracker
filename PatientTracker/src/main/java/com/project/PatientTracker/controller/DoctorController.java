package com.project.PatientTracker.controller;

import com.project.PatientTracker.model.Appointment;
import com.project.PatientTracker.model.Doctor;
import com.project.PatientTracker.model.File;
import com.project.PatientTracker.model.Patient;
import com.project.PatientTracker.repository.DoctorRepository;

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

    // Get All Doctors
    @GetMapping("/doctors")
    public ResponseEntity<List<Doctor>> getDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        return ResponseEntity.ok(doctors);
    }

    // Get Doctor by ID
    @GetMapping("/doctors/{id}")
    public ResponseEntity<Doctor> getUserById(@PathVariable Long id) {
        Doctor doctor = doctorRepository.getReferenceById(id);
        return ResponseEntity.ok(doctor);
    }

    // Get All Patients by Doctor
    @GetMapping("/doctors/{id}/patients")
    public ResponseEntity<Set<Patient>> getPatientsByDoctor(@PathVariable Long id) {
        Set<Patient> patients = doctorRepository.getReferenceById(id).getPatients();
        return ResponseEntity.ok(patients);
    }

    // Get All Appointments by Doctor
    @GetMapping("/doctors/{id}/appointments")
    public ResponseEntity<Set<Appointment>> getAppointmentsByDoctor(@PathVariable Long id) {
        Set<Appointment> appointments = doctorRepository.getReferenceById(id).getAppointments();
        return ResponseEntity.ok(appointments);
    }

    // Create Doctor
    @PostMapping("/doctors")
	public Doctor createDoctor(@RequestBody Doctor doctor) {
		return doctorRepository.save(doctor);
	}

    // Update Doctor Profile
    @PutMapping("/doctors/{id}")
	public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctorDetails){
		Doctor doctor = doctorRepository.getReferenceById(id);
		
		doctor.setFirstName(doctorDetails.getFirstName())
            .setLastName(doctorDetails.getLastName())
            .setDateOfBirth(doctorDetails.getDateOfBirth())
            .setAge(doctorDetails.getAge())
            .setEmail(doctorDetails.getEmail())
            .setPhone(doctorDetails.getPhone());

        doctor.setSpecialty(doctorDetails.getSpecialty());

		Doctor updatedDoctor = doctorRepository.save(doctor);
		return ResponseEntity.ok(updatedDoctor);
	}

    // Assign Patient to Doctor
    @PutMapping("/doctors/{id}/assign-patient")
	public ResponseEntity<Doctor> assignPatient(@PathVariable Long id, @RequestBody Patient patientDetails){
		Doctor doctor = doctorRepository.getReferenceById(id);
		
		doctor.addPatient(patientDetails);
        		
		Doctor updatedDoctor = doctorRepository.save(doctor);
		return ResponseEntity.ok(updatedDoctor);
	}
    
    // Add File to Doctor's View
    @PutMapping("/doctors/{id}/add-file")
	public ResponseEntity<Doctor> addFile(@PathVariable Long id, @RequestBody File fileDetails){
		Doctor doctor = doctorRepository.getReferenceById(id);
		
		doctor.addFile(fileDetails);
        		
		Doctor updatedDoctor = doctorRepository.save(doctor);
		return ResponseEntity.ok(updatedDoctor);
	}

    // Delete Doctor
	@DeleteMapping("/doctors/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteDoctor(@PathVariable Long id){
		Doctor doctor = doctorRepository.getReferenceById(id);
		
		doctorRepository.delete(doctor);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
