package com.project.PatientTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.PatientTracker.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
}
