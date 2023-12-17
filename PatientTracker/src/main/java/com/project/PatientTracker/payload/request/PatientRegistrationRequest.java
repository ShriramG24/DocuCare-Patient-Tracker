package com.project.PatientTracker.payload.request;

import com.project.PatientTracker.model.Patient;

import jakarta.validation.constraints.*;
import lombok.Getter;

public class PatientRegistrationRequest {
  @NotBlank
  @Email
  @Getter
  private String email;

  @NotBlank
  @Getter
  private String password;

  @Getter
  private Patient patient;
}
