package com.project.PatientTracker.model;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "Files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ownerId")
    private Doctor owner;

    @OneToOne
    @PrimaryKeyJoinColumn(name="recordId")
    private MedicalRecord medicalRecord;
    
    @Column(name="name", nullable=false)
    private String name;

    @Column(name="location", nullable=false, unique=true)
    private String location;

    @Column(name="lastUpdated", nullable=false)
    private Date lastUpdated;

    public User getDoctor() { return this.owner; }
    public File setDoctor(Doctor owner) { this.owner = owner; return this; }

    public String getName() { return this.name; }
    public File setName(String name) { this.name = name; return this; }

    public String getLocation() { return this.location; }
    public File setLocation(String location) { this.location = location; return this; }

    public Date getLastUpdated() { return this.lastUpdated; }
    public File setLastUpdated(Date lastUpdated) { this.lastUpdated = lastUpdated; return this; }
}
