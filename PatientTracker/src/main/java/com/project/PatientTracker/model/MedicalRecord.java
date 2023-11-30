package com.project.PatientTracker.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "MedicalRecords")
@Accessors(chain = true)
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Getter @Setter
    @JoinColumn(name = "doctorId")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patientId")
    @Getter @Setter
    private Patient patient;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "fileId")
    @Getter @Setter
    private File file;

    @Column(name="name", nullable=false)
    @Getter @Setter
    private String name;
    
    @Column(name="data", nullable=false)
    @Getter @Setter
    private String data;

    @Column(name="type", nullable=false)
    @Getter @Setter
    private String type;
}
