package com.project.PatientTracker.controller;

import com.project.PatientTracker.exception.ResourceNotFoundException;
import com.project.PatientTracker.model.Appointment;
import com.project.PatientTracker.payload.request.AppointmentRequest;
import com.project.PatientTracker.payload.response.AppointmentResponse;
import com.project.PatientTracker.repository.AppointmentRepository;
import com.project.PatientTracker.repository.DoctorRepository;
import com.project.PatientTracker.repository.PatientRepository;

import java.util.HashMap;
import java.util.Map;

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
@RequestMapping("/api/")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    // Get Appointment by ID
    @GetMapping("/appointments/{id}")
    public ResponseEntity<AppointmentResponse> getAppointmentById(@PathVariable Long id) {
        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Appointment with ID not found: " + id));

        AppointmentResponse response = new AppointmentResponse().build(appointment);
        return ResponseEntity.ok(response);
    }

    // Create Appointment
    @PostMapping("/appointments")
    public ResponseEntity<AppointmentResponse> createAppointment(@RequestBody AppointmentRequest appointmentRequest) {
        Appointment appointment = new Appointment().setTime(appointmentRequest.getTime())
            .setPurpose(appointmentRequest.getPurpose())
            .setStatus(appointmentRequest.getStatus())
            .setNotes(appointmentRequest.getNotes())
            .setDoctor(doctorRepository.findById(appointmentRequest.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID not found: " + appointmentRequest.getDoctorId())))
            .setPatient(patientRepository.findById(appointmentRequest.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient with ID not found: " + appointmentRequest.getPatientId())));

        AppointmentResponse response = new AppointmentResponse().build(appointmentRepository.save(appointment));
        return ResponseEntity.ok(response);
    }

    // Update Appointment
    @PutMapping("/appointments/{id}")
    public ResponseEntity<AppointmentResponse> updateAppointment(@PathVariable Long id, @RequestBody AppointmentRequest appointmentRequest) {
        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Appointment with ID not found: " + id));
        
        appointment.setTime(appointmentRequest.getTime())
            .setPurpose(appointmentRequest.getPurpose())
            .setStatus(appointmentRequest.getStatus())
            .setNotes(appointmentRequest.getNotes())
            .setDoctor(doctorRepository.findById(appointmentRequest.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID not found: " + appointmentRequest.getDoctorId())))
            .setPatient(patientRepository.findById(appointmentRequest.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient with ID not found: " + appointmentRequest.getPatientId())));

        AppointmentResponse response = new AppointmentResponse().build(appointmentRepository.save(appointment));
        return ResponseEntity.ok(response);
    }

    // Delete Appointment
    @DeleteMapping("/appointments/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteAppointment(@PathVariable Long id) {
        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Appointment with ID not found: " + id));
		
		appointmentRepository.delete(appointment);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
    }
}
