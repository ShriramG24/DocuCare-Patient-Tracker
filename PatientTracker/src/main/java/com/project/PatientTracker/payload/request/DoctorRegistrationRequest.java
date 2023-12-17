package com.project.PatientTracker.payload.request;

import com.project.PatientTracker.model.Doctor;

import jakarta.validation.constraints.*;
import lombok.Getter;

public class DoctorRegistrationRequest {
  @NotBlank
  @Email
  @Getter
  private String email;

  @NotBlank
  @Getter
  private String password;

  @Getter
  private Doctor doctor;
}
