package com.project.PatientTracker.controller;

import com.project.PatientTracker.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private Role role;
  private String type; 
  private String specialty; 
  private String degree; 
  private Integer experience;
  private String clinicAddr;  
  private Integer age; 
  private String gender; 
  private String phone; 
  private String address;
  private String diagnoses;  
  private String medications;  
  private String allergies;  
  




}