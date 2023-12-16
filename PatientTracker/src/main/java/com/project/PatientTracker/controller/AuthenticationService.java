package com.project.PatientTracker.controller;

import com.project.PatientTracker.config.JwtService;
import com.project.PatientTracker.model.Role;
import com.project.PatientTracker.model.User;
import com.project.PatientTracker.model.Patient;
import com.project.PatientTracker.model.Doctor;
import com.project.PatientTracker.model.Myuser;
import com.project.PatientTracker.repository.UserRepository;
import com.project.PatientTracker.repository.PatientRepository;
import com.project.PatientTracker.repository.DoctorRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final PatientRepository patientRepository;
  private final DoctorRepository doctorRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request) {

    var type = request.getType();
    var user = Myuser.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(request.getRole())
        .build();

    var savedUser = repository.save(user);

    if (type.equals("Doctor")) {

      Doctor doctor = (Doctor) new Doctor().setSpecialty(request.getSpecialty())
          .setDegree(request.getDegree())
          .setRating(0.0)
          .setExperience(request.getExperience())
          .setClinicAddr(request.getClinicAddr())
          .setFirstName(request.getFirstname())
          .setLastName(request.getLastname())

          .setGender(request.getGender())
          .setEmail(request.getEmail())
          .setPhone(request.getPhone())
          .setPassword(request.getPassword());

      var saveddoctorUser = doctorRepository.save(doctor);
      var jwtToken = jwtService.generateToken(user);
      return AuthenticationResponse.builder().token(jwtToken).userId(saveddoctorUser.getId()).type("doctor").build();
    } else {

      // Patient patient = (Patient) new Patient().setAddress(request.getAddress())
      // .setDiagnoses(request.getDiagnoses())
      // .setMedications(request.getMedications())
      // .setAllergies(request.getAllergies())
      // .setFirstName(request.getFirstname())
      // .setLastName(request.getLastname())
      // .setAge(request.getAge())
      // .setGender(request.getGender())
      // .setEmail(request.getEmail())
      // .setPhone(request.getPhone());

      Patient patient = (Patient) new Patient()
          .setFirstName(request.getFirstname())
          .setLastName(request.getLastname())
          .setGender(request.getGender())
          .setEmail(request.getEmail())
          .setPhone(request.getPhone())
          .setPassword(request.getPassword());

      var savedpatientUser = patientRepository.save(patient);

      var jwtToken = jwtService.generateToken(user);
      return AuthenticationResponse.builder().token(jwtToken).userId(savedpatientUser.getId()).type("patient").build();

    }
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    System.out.println(request.getEmail());
    System.out.println(request.getPassword());

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()));
    var user = repository.findByEmail(request.getEmail())
        .orElseThrow();
    System.out.println("user");
    System.out.println(user);
    var jwtToken = jwtService.generateToken(user);
    System.out.println(jwtToken);
    var refreshToken = jwtService.generateRefreshToken(user);

    return AuthenticationResponse.builder().token(jwtToken).userId(user.getId()).build();
  }

}