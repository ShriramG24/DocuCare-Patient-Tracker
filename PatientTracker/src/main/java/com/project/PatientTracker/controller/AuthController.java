package com.project.PatientTracker.controller;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.project.PatientTracker.model.Doctor;
import com.project.PatientTracker.model.ERole;
import com.project.PatientTracker.model.Patient;
import com.project.PatientTracker.model.Role;
import com.project.PatientTracker.payload.request.DoctorRegistrationRequest;
import com.project.PatientTracker.payload.request.LoginRequest;
import com.project.PatientTracker.payload.request.PatientRegistrationRequest;
import com.project.PatientTracker.payload.response.JwtResponse;
import com.project.PatientTracker.payload.response.MessageResponse;
import com.project.PatientTracker.repository.RoleRepository;
import com.project.PatientTracker.repository.PatientRepository;
import com.project.PatientTracker.repository.DoctorRepository;
import com.project.PatientTracker.security.jwt.JwtUtils;
import com.project.PatientTracker.security.services.UserDetailsImpl;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login/patient")
    public ResponseEntity<JwtResponse> authenticatePatient(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl patientDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = patientDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity
                .ok(new JwtResponse(jwt, patientDetails.getId(), patientDetails.getUsername(),
                        patientDetails.getEmail(), roles));
    }

    @PostMapping("/register/patient")
    public ResponseEntity<MessageResponse> registerPatient(@Valid @RequestBody PatientRegistrationRequest registrationRequest) { 
        if (patientRepository.existsByEmail(registrationRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        Patient patient = registrationRequest.getPatient();
        patient.setPassword(encoder.encode(registrationRequest.getPassword()));
        
        Role patientRole = roleRepository.findByName(ERole.ROLE_PATIENT)
                .orElseThrow(() -> new RuntimeException("Error: Role 'Patient' is not found."));
        patient.setRole(patientRole);

        patientRepository.save(patient);

        return ResponseEntity.ok(new MessageResponse("Patient registered successfully!"));
    }

    @PostMapping("/login/doctor")
    public ResponseEntity<JwtResponse> authenticateDoctor(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl doctorDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = doctorDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity
                .ok(new JwtResponse(jwt, doctorDetails.getId(), doctorDetails.getUsername(),
                        doctorDetails.getEmail(), roles));
    }

    @PostMapping("/register/doctor")
    public ResponseEntity<MessageResponse> registerDoctor(@Valid @RequestBody DoctorRegistrationRequest registrationRequest) { 
        if (patientRepository.existsByEmail(registrationRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        Doctor doctor = registrationRequest.getDoctor();
        doctor.setPassword(encoder.encode(registrationRequest.getPassword()));

        Role doctorRole = roleRepository.findByName(ERole.ROLE_DOCTOR)
                .orElseThrow(() -> new RuntimeException("Error: Role 'Doctor' is not found."));
        doctor.setRole(doctorRole);

        doctorRepository.save(doctor);

        return ResponseEntity.ok(new MessageResponse("Doctor registered successfully!"));
    }
}