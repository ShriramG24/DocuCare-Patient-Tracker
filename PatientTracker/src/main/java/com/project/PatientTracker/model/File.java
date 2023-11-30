package com.project.PatientTracker.model;

import java.util.Date;

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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ownerId")
    @Getter @Setter
    private Doctor owner;

    @OneToOne
    @PrimaryKeyJoinColumn(name="recordId")
    @Getter @Setter
    private MedicalRecord medicalRecord;
    
    @Column(name="name", nullable=false)
    @Getter @Setter
    private String name;

    @Column(name="location", nullable=false, unique=true)
    @Getter @Setter
    private String location;

    @Column(name="lastUpdated", nullable=false)
    @Getter @Setter
    private Date lastUpdated;
}
