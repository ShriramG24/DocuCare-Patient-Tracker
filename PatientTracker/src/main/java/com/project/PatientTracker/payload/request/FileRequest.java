package com.project.PatientTracker.payload.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.PatientTracker.model.MedicalRecord;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class FileRequest {

    @Getter
    private Long ownerId;

    @Getter
    private MedicalRecord medicalRecord;
    
    @Getter
    private String name;

    @Getter
    private String location;

    @Getter
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdated;
}
