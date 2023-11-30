package com.project.PatientTracker.payload.request;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class PatientRequest {

    @NotBlank
    @Getter
    private String firstName;

    @NotBlank
    @Getter
    private String lastName;

    @NotBlank
    @Getter
    private Date dateOfBirth;

    @NotBlank
    @Getter
    private int age;

    @NotBlank
    @Getter
    private String email;

    @Getter
    private String phone;

    @NotBlank
    @Getter
    private String address;

    @Getter
    private Long doctorId;
}
