package com.project.PatientTracker.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "Files")
@Accessors(chain = true)
public class File {
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

    @Column(name="type", nullable=false)
    @Getter @Setter
    private String type;
    
    @Column(name="name", nullable=false)
    @Getter @Setter
    private String name;

    @Column(name="location", nullable=false, unique=true)
    @Getter @Setter
    private String location;

    @Column(name="lastUpdated", nullable=false)
    @Getter @Setter
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdated;
}
