package com.project.PatientTracker.repository;

import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.data.jpa.repository.JpaRepository;
// import com.project.PatientTracker.model.User;

import com.project.PatientTracker.model.Myuser;


public interface UserRepository extends JpaRepository<Myuser, Integer> {

    Optional <Myuser> findByEmail(String email); 
    
}
