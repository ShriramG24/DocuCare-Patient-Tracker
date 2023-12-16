package com.project.PatientTracker.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.PatientTracker.model.Doctor;
import com.project.PatientTracker.model.Patient;
import com.project.PatientTracker.repository.DoctorRepository;
import com.project.PatientTracker.repository.PatientRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  DoctorRepository doctorRepository;

  @Autowired
  PatientRepository patientRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    try {
      Doctor doctor = doctorRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("Doctor Not Found with username: " + email));
      return UserDetailsImpl.build(doctor);
    } catch (Exception e) {
      Patient patient = patientRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("Patient Not Found with username: " + email));
      return UserDetailsImpl.build(patient);
    }

  }

}
