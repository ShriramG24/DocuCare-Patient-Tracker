package com.project.PatientTracker.controller;

import com.project.PatientTracker.exception.ResourceNotFoundException;
import com.project.PatientTracker.model.Appointment;
import com.project.PatientTracker.repository.AppointmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/")
@PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_PATIENT')")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    // Get Appointment by ID
    /**
     * @param id Appointment ID
     * @return Appointment object
     */
    @GetMapping("/appointments/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Appointment with ID not found: " + id));

        return ResponseEntity.ok(appointment);
    }

    // Create Appointment
    /**
     * @param appointmentRequest Appointment object
     * @return Created Appointment object
     */
    @PostMapping("/appointments")
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointmentRequest) {
        Appointment appointment = new Appointment().setTime(appointmentRequest.getTime())
            .setPurpose(appointmentRequest.getPurpose())
            .setStatus(appointmentRequest.getStatus())
            .setNotes(appointmentRequest.getNotes())
            .setDoctor(appointmentRequest.getDoctor())
            .setPatient(appointmentRequest.getPatient());

        return ResponseEntity.ok(appointmentRepository.save(appointment));
    }

    // Update Appointment
    /**
     * @param id Appointment ID
     * @param appointmentRequest Appointment object
     * @return Updated Appointment object
     */
    @PutMapping("/appointments/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id, @RequestBody Appointment appointmentRequest) {
        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Appointment with ID not found: " + id));
        
        appointment.setTime(appointmentRequest.getTime())
            .setPurpose(appointmentRequest.getPurpose())
            .setStatus(appointmentRequest.getStatus())
            .setNotes(appointmentRequest.getNotes())
            .setDoctor(appointmentRequest.getDoctor())
            .setPatient(appointmentRequest.getPatient());

        return ResponseEntity.ok(appointmentRepository.save(appointment));
    }

    // Delete Appointment
    /**
     * @param id Appointment ID
     * @return Deleted Appointment object
     */
    @DeleteMapping("/appointments/{id}")
    public ResponseEntity<Appointment> deleteAppointment(@PathVariable Long id) {
        Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Appointment with ID not found: " + id));
		
		appointmentRepository.delete(appointment);
		return ResponseEntity.ok(appointment);
    }
}
