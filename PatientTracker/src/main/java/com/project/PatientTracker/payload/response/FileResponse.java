package com.project.PatientTracker.payload.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.PatientTracker.model.File;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class FileResponse {
    @NotBlank
    @Getter @Setter
    private Long id;

    @NotBlank
    @Getter @Setter
    private Long ownerId;

    @Getter @Setter
    private Long recordId;
    
    @NotBlank
    @Getter @Setter
    private String name;

    @NotBlank
    @Getter @Setter
    private String location;

    @NotBlank
    @Getter @Setter
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdated;

    public FileResponse build(File file) {
        return this.setId(file.getId())
            .setLocation(file.getLocation())
            .setName(file.getName())
            .setOwnerId(file.getOwner().getId())
            .setRecordId(file.getMedicalRecord().getId())
            .setLastUpdated(file.getLastUpdated());
    }
}
