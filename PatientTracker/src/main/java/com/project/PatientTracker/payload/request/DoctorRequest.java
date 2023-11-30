package com.project.PatientTracker.payload.request;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class DoctorRequest {

    @NotBlank
    @Getter
    private String firstName;

    @NotBlank
    @Getter
    private String lastName;

    @NotBlank
    @Getter
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateOfBirth;

    @NotBlank
    @Getter
    private int age;

    @NotBlank
    @Getter
    private String email;

    @NotBlank
    @Getter
    private String phone;

    @NotBlank
    @Getter
    private String address;

    @Getter
    private String specialty;
}
