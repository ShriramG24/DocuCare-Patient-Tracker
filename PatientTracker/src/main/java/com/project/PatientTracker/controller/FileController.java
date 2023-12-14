package com.project.PatientTracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.PatientTracker.exception.ResourceNotFoundException;
import com.project.PatientTracker.model.Doctor;
import com.project.PatientTracker.model.File;
import com.project.PatientTracker.model.Patient;
import com.project.PatientTracker.repository.DoctorRepository;
import com.project.PatientTracker.repository.FileRepository;
import com.project.PatientTracker.repository.PatientRepository;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/")
public class FileController {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    // Get All Files by User ID
    @GetMapping("/files/{userId}")
    public ResponseEntity<List<File>> getFiles(@PathVariable Long userId) {
        List<File> files;
        if (doctorRepository.findById(userId).isEmpty()) {
            Patient patient = patientRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID not found: " + userId));
            files = fileRepository.findByPatient(patient);
        } else {
            Doctor doctor = doctorRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID not found: " + userId));
            files = fileRepository.findByDoctor(doctor);
        }

        return ResponseEntity.ok(files);
    }

    // Save File to Database
    @PostMapping("/files/{userId}")
	public ResponseEntity<File> saveFile(@PathVariable Long userId, @RequestBody File fileRequest) {
        File file = new File();
        if (doctorRepository.findById(userId).isEmpty()) {
            Patient patient = patientRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID not found: " + userId));
            file.setDoctor(null).setPatient(patient);
        } else {
            Doctor doctor = doctorRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID not found: " + userId));
            file.setDoctor(doctor).setPatient(null);
        }

        file.setName(fileRequest.getName())
            .setType(fileRequest.getType())
            .setLocation(fileRequest.getLocation())
            .setLastUpdated(fileRequest.getLastUpdated());

        return ResponseEntity.ok(fileRepository.save(file));
	}

    // Remove File from Database
	@DeleteMapping("/files/{id}")
	public ResponseEntity<File> removeFile(@PathVariable Long id){
		File file = fileRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("File with ID not found: " + id));
		
        fileRepository.delete(file);
		return ResponseEntity.ok(file);
	}
}
