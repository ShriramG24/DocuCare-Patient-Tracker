package com.project.PatientTracker.controller;
import com.project.PatientTracker.config.JwtService;
import com.project.PatientTracker.model.Role;
import com.project.PatientTracker.model.User;
import com.project.PatientTracker.model.Myuser;
import com.project.PatientTracker.repository.UserRepository;
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
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse  register(RegisterRequest request) {

    var user = Myuser.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(request.getRole())
        .build();
    
    var savedUser = repository.save(user);
    var jwtToken = jwtService.generateToken(savedUser);

    return AuthenticationResponse.builder().token(jwtToken).user(user).build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    System.out.println(request.getEmail());
    System.out.println(request.getPassword());

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    var user = repository.findByEmail(request.getEmail())
        .orElseThrow();
    System.out.println("user");
    System.out.println(user);
    var jwtToken = jwtService.generateToken(user);
    System.out.println(jwtToken);
    var refreshToken = jwtService.generateRefreshToken(user);
   
    return AuthenticationResponse.builder().token(jwtToken).user(user).build();
  }

 

}