package com.project.PatientTracker.model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "Appointments")
@Accessors(fluent = true)
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctorId")
    @Getter @Setter
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patientId")
    @Getter @Setter
    private Patient patient;
    
    @Column(name="time", nullable=false)
    @Getter @Setter
    private Date time;

    @Column(name="status", nullable=false)
    @Getter @Setter
    private String status;

    @Column(name="purpose", nullable=false)
    @Getter @Setter
    private String purpose;
    
    @Column(name="notes", nullable=true)
    @Getter @Setter
    private String notes;
}
