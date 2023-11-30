package com.project.PatientTracker.payload.request;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class MedicalRecordRequest {

    @Getter
    private Long doctorId;

    @Getter
    private Long patientId;

    @Getter
    private Long fileId;

    @Getter
    private String name;
    
    @Getter
    private String data;

    @Getter
    private String type;
}
