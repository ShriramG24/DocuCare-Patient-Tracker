package com.project.PatientTracker.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "Prescriptions")
@Accessors(chain = true)
public class Prescription {
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

    @Column(name="medicines", nullable=true)
    @Getter @Setter
    private String medicines;

    @Column(name="reports", nullable=true)
    @Getter @Setter
    private String reports;

    @Column(name="instructions", nullable=true)
    @Getter @Setter
    private String instructions;
}
