package com.project.PatientTracker.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.project.PatientTracker.payload.request.FileRequest;
import com.project.PatientTracker.payload.response.FileResponse;
import com.project.PatientTracker.repository.DoctorRepository;
import com.project.PatientTracker.repository.FileRepository;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/")
public class FileController {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    // Get All Files by Doctor ID
    @GetMapping("/files/{doctorId}")
    public ResponseEntity<List<FileResponse>> getFiles(@PathVariable Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID not found: " + doctorId));

        List<File> files = fileRepository.findByOwner(doctor);

        List<FileResponse> response = files.stream().map(f -> {
            return new FileResponse().build(f);
        }).toList();
        return ResponseEntity.ok(response);
    }

    // Save File to Database
    @PostMapping("/files/{doctorId}")
	public ResponseEntity<FileResponse> saveFile(@PathVariable Long doctorId, @RequestBody FileRequest fileRequest) {
        Doctor doctor = doctorRepository.findById(doctorId)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID not found: " + doctorId));

        File file = new File().setName(fileRequest.getName())
            .setLocation(fileRequest.getLocation())
            .setOwner(doctor)
            .setLastUpdated(fileRequest.getLastUpdated());

        FileResponse response = new FileResponse().build(fileRepository.save(file));
        return ResponseEntity.ok(response);
	}

    // Remove File from Database
	@DeleteMapping("/files/{id}")
	public ResponseEntity<Map<String, Boolean>> removeFile(@PathVariable Long id){
		File file = fileRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("File with ID not found: " + id));
		
		fileRepository.delete(file);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
