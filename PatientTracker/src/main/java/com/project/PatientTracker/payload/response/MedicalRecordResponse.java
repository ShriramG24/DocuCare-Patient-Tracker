package com.project.PatientTracker.payload.response;

import com.project.PatientTracker.model.MedicalRecord;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class MedicalRecordResponse {
    @NotBlank
    @Getter @Setter
    private Long id;

    @NotBlank
    @Getter @Setter
    private Long doctorId;
    
    @NotBlank
    @Getter @Setter
    private Long patientId;

    @NotBlank
    @Getter @Setter
    private Long fileId;

    @NotBlank
    @Getter @Setter
    private String name;
    
    @NotBlank
    @Getter @Setter
    private String data;

    @NotBlank
    @Getter @Setter
    private String type;

    public MedicalRecordResponse build(MedicalRecord record) {
        return this.setId(record.getId())
            .setData(record.getData())
            .setName(record.getName())
            .setType(record.getType())
            .setDoctorId(record.getDoctor().getId())
            .setPatientId(record.getPatient().getId())
            .setFileId(record.getFile().getId());
    }
}
