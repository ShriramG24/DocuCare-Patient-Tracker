package com.project.PatientTracker.model;

import jakarta.persistence.*;

@Entity
@Table(name = "MedicalRecord")
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctorId")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patientId")
    private Patient patient;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "fileId")
    private File file;

    @Column(name="name", nullable=false)
    private String name;
    
    @Column(name="data", nullable=false)
    private String data;

    @Column(name="type", nullable=false)
    private String type;

    public Doctor getDoctor() { return this.doctor; }
    public MedicalRecord setDoctor(Doctor doctor) { this.doctor = doctor; return this; }

    public Patient getPatient() { return this.patient; }
    public MedicalRecord setPatient(Patient patient) { this.patient = patient; return this; }

    public File getFile() { return this.file; }
    public MedicalRecord setFile(File file) { this.file = file; return this; }

    public String getName() { return this.name; }
    public MedicalRecord setName(String name) { this.name = name; return this; }

    public String getData() { return this.data; }
    public MedicalRecord setData(String data) { this.data = data; return this; }

    public String getType() { return this.type; }
    public MedicalRecord setType(String type) { this.type = type; return this; }
}
