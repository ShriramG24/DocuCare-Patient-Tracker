package com.project.PatientTracker.model;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "Appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctorId")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patientId")
    private Patient patient;
    
    @Column(name="time", nullable=false)
    private Date time;

    @Column(name="status", nullable=false)
    private String status;

    @Column(name="purpose", nullable=false)
    private String purpose;
    
    @Column(name="firstName", nullable=true)
    private String notes;

    public Doctor getDoctor() { return this.doctor; }
    public Appointment setDoctor(Doctor doctor) { this.doctor = doctor; return this; }

    public Patient getPatient() { return this.patient; }
    public Appointment setPatient(Patient patient) { this.patient = patient; return this; }

    public Date getTime() { return this.time; }
    public Appointment getTime(Date time) { this.time = time; return this; }

    public String getStatus() { return this.status; }
    public Appointment setStatus(String status) { this.status = status; return this; }

    public String getPurpose() { return this.purpose; }
    public Appointment setPurpose(String purpose) { this.purpose = purpose; return this; }

    public String getNotes() { return this.notes; }
    public Appointment setNotes(String notes) { this.notes = notes; return this; }
}
