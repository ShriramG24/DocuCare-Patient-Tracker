package com.project.PatientTracker.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class LoginRequest {
  @NotBlank
  @Email
  @Getter
  private String email;

  @NotBlank
  @Getter
  private String password;
}