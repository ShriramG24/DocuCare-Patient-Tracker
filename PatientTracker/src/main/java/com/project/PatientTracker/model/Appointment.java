package com.project.PatientTracker.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "Appointments")
@Accessors(chain = true)
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
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
