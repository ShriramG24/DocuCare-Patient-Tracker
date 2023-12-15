package com.project.PatientTracker.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.PatientTracker.exception.FileEmptyException;
import com.project.PatientTracker.exception.ResourceNotFoundException;
import com.project.PatientTracker.model.Doctor;
import com.project.PatientTracker.model.File;
import com.project.PatientTracker.model.Patient;
import com.project.PatientTracker.repository.DoctorRepository;
import com.project.PatientTracker.repository.FileRepository;
import com.project.PatientTracker.repository.PatientRepository;
import com.project.PatientTracker.service.FileServiceImpl;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/")
public class FileController {

    private FileServiceImpl fileService;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    public FileController(FileServiceImpl fileService) {
        this.fileService = fileService;
    }

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
    public ResponseEntity<File> saveFile(@PathVariable Long userId, @RequestParam("file") MultipartFile multipartFile) throws Exception {
        File file = new File();

        if (multipartFile.isEmpty()) {
            throw new FileEmptyException("File is empty. Cannot save an empty file");
        }
        boolean isValidFile = isValidFile(multipartFile);
        List<String> allowedFileExtensions = new ArrayList<>(Arrays.asList("pdf", "txt", "png", "jpg", "jpeg"));
        
        if (isValidFile && allowedFileExtensions.contains(FilenameUtils.getExtension(multipartFile.getOriginalFilename()))) {
            String fileName = fileService.uploadFile(multipartFile);
            file.setName(fileName)
                .setType(FilenameUtils.getExtension(multipartFile.getOriginalFilename()))
                .setLocation(fileService.getFileLocation(fileName))
                .setLastUpdated(new Date());
        }

        if (doctorRepository.findById(userId).isEmpty()) {
            Patient patient = patientRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User with ID not found: " + userId));
            file.setDoctor(null).setPatient(patient);
        } else {
            Doctor doctor = doctorRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User with ID not found: " + userId));
            file.setDoctor(doctor).setPatient(null);
        }

        return ResponseEntity.ok(fileRepository.save(file));
    }

    // Remove File from Database
    @DeleteMapping("/files/{id}")
    public ResponseEntity<File> removeFile(@PathVariable Long id) throws Exception {
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File with ID not found: " + id));

        boolean isDeleted = fileService.delete(file.getName());
        if (!isDeleted) {
            throw new FileEmptyException("File doesn't exist. Cannot delete a non-existent file.");
        }
        fileRepository.delete(file);
        return ResponseEntity.ok(file);
    }

    private boolean isValidFile(MultipartFile multipartFile) {
        if (Objects.isNull(multipartFile.getOriginalFilename())) {
            return false;
        }
        return !multipartFile.getOriginalFilename().trim().equals("");
    }
}
