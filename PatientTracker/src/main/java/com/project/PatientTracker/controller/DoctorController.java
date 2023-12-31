package com.project.PatientTracker.controller;

import com.project.PatientTracker.exception.ResourceNotFoundException;
import com.project.PatientTracker.model.*;
import com.project.PatientTracker.repository.*;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/")
@PreAuthorize("hasRole('ROLE_DOCTOR')")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    // Get All Doctors
    /**
     * @return List of doctors
     */
    @GetMapping("/doctors")
    public ResponseEntity<List<Doctor>> getDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();

        return ResponseEntity.ok(doctors);
    }

    // Get Doctor by ID
    /**
     * @param id Doctor ID
     * @return Doctor object
     */
    @GetMapping("/doctors/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID not found: " + id));

        return ResponseEntity.ok(doctor);
    }

    // Get All Specialties
    /**
     * @return List of specialties
     */
    @GetMapping("/doctors/specialties")
    public ResponseEntity<List<String>> getSpecialties() {
        return ResponseEntity.ok(Arrays.asList(
            "Cardiologist", "Dermatologist", "Endocrinologist", "Gastroenterologist",
            "Gynecologist", "Hematologist", "Neurologist", "Oncologist", "Ophthalmologist", "Pediatrician",
            "Psychiatrist", "Pulmonologist", "Radiologist", "Rheumatologist", "Urologist"
        ));
    }

    // Get All Doctors by Specialty
    /**
     * @param specialty Doctor specialty
     * @return
     */
    @GetMapping("/doctors/specialty/{specialty}")
    public ResponseEntity<List<Doctor>> getDoctorBySpecialty(@PathVariable String specialty) {
        List<Doctor> doctors = doctorRepository.findBySpecialty(specialty);

        return ResponseEntity.ok(doctors);
    }

    // Get All Doctors by Rating
    /**
     * @param rating Doctor rating
     * @return List of doctors with rating greater than or equal to the given rating
     */
    @GetMapping("/doctors/rating/{rating}")
    public ResponseEntity<List<Doctor>> getDoctorBySpecialty(@PathVariable double rating) {
        List<Doctor> doctors = doctorRepository.findByRatingGreaterThanEqual(rating);

        return ResponseEntity.ok(doctors);
    }

    // Get All Patients by Doctor
    /**
     * @param id Doctor ID
     * @return List of patients
     */
    @GetMapping("/doctors/{id}/patients")
    public ResponseEntity<List<Patient>> getPatientsByDoctor(@PathVariable Long id) {
        Set<Patient> patients = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID not found: " + id))
                .getPatients();

        return ResponseEntity.ok(new ArrayList<Patient>(patients));
    }

    // Get All Appointments by Doctor
    /**
     * @param id Doctor ID
     * @return List of appointments for the given doctor
     */
    @GetMapping("/doctors/{id}/appointments")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctor(@PathVariable Long id) {
        Set<Appointment> appointments = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID not found: " + id))
                .getAppointments();

        return ResponseEntity.ok(new ArrayList<Appointment>(appointments));
    }

    // Get All Files by Doctor
    /**
     * @param id Doctor ID
     * @return List of files for the given doctor
     */
    @GetMapping("/doctors/{id}/files")
    public ResponseEntity<List<File>> getFilesByDoctor(@PathVariable Long id) {
        Set<File> files = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID not found: " + id))
                .getFiles();

        return ResponseEntity.ok(new ArrayList<File>(files));
    }

    // Create Doctor
    /**
     * @param doctorRequest Doctor object
     * @return Doctor object with ID assigned
     */
    @PostMapping("/doctors")
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctorRequest) {
        Doctor doctor = (Doctor) new Doctor()
                .setSpecialty(doctorRequest.getSpecialty())
                .setDegree(doctorRequest.getDegree())
                .setRating(0.0)
                .setExperience(doctorRequest.getExperience())
                .setClinicAddr(doctorRequest.getClinicAddr())
                .setFirstName(doctorRequest.getFirstName())
                .setLastName(doctorRequest.getLastName())
                .setAge(doctorRequest.getAge())
                .setGender(doctorRequest.getGender())
                .setEmail(doctorRequest.getEmail())
                .setPhone(doctorRequest.getPhone())
                .setPassword(doctorRequest.getPassword());

        return ResponseEntity.ok(doctorRepository.save(doctor));
    }

    // Update Doctor Profile
    /**
     * @param id Doctor ID
     * @param doctorRequest Doctor object with updated fields
     * @return Updated doctor object
     */
    @PutMapping("/doctors/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctorRequest) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID not found: " + id));

        doctor.setSpecialty(doctorRequest.getSpecialty())
            .setDegree(doctorRequest.getDegree())
            .setRating(doctorRequest.getRating())
            .setExperience(doctorRequest.getExperience())
            .setClinicAddr(doctorRequest.getClinicAddr())
            .setFirstName(doctorRequest.getFirstName())
            .setLastName(doctorRequest.getLastName())
            .setAge(doctorRequest.getAge())
            .setGender(doctorRequest.getGender())
            .setEmail(doctorRequest.getEmail())
            .setPhone(doctorRequest.getPhone())
            .setPassword(doctorRequest.getPassword());

        return ResponseEntity.ok(doctorRepository.save(doctor));
    }

    // Assign Patient to Doctor
    /**
     * @param doctorId Doctor ID to assign patient to
     * @param patientId Patient ID to assign to doctor
     * @return Updated doctor object
     */
    @PutMapping("/doctors/{doctorId}/assign-patient/{patientId}")
    public ResponseEntity<Doctor> assignPatient(@PathVariable Long doctorId, @PathVariable Long patientId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID not found: " + doctorId));

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient with ID not found: " + patientId));

        patient.addDoctor(doctor);
        doctor.addPatient(patient);

        return ResponseEntity.ok(doctorRepository.save(doctor));
    }

    // Delete Doctor
    /**
     * @param id Doctor ID
     * @return Deleted doctor object
     */
    @DeleteMapping("/doctors/{id}")
    public ResponseEntity<Doctor> deleteDoctor(@PathVariable Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID not found: " + id));

        doctorRepository.delete(doctor);
        return ResponseEntity.ok(doctor);
    }
}
