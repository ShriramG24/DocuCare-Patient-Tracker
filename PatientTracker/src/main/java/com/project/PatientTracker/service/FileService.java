package com.project.PatientTracker.service;

import com.project.PatientTracker.exception.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String uploadFile(MultipartFile multipartFile) throws FileUploadException, IOException;

    boolean delete(String fileName);
}