package com.project.PatientTracker.model;

import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "Patients")
public class Patient extends User {
    @Column(name="address", nullable=false, unique=true)
    private String address;

    @ManyToOne
    @JoinColumn(name = "doctorId")
    private Doctor doctor;

    @OneToMany(mappedBy = "patient")
    private Set<Appointment> appointments;

    @OneToMany(mappedBy = "patient")
    private Set<MedicalRecord> records;

    public Doctor getDoctor() { return this.doctor; }
    public Patient setDoctor(Doctor doctor) { this.doctor = doctor; return this; }

    public String getAddress() { return this.address; }
    public Patient setAddress(String address) { this.address = address; return this; }

    public Set<Appointment> getAppointments() { return this.appointments; }
    public Patient addAppointment(Appointment appointment) { this.appointments.add(appointment); return this; }
    public Patient removeAppointment(Appointment appointment) { this.appointments.remove(appointment); return this; }

    public Set<MedicalRecord> getRecords() { return this.records; }
    public Patient addRecord(MedicalRecord record) { this.records.add(record); return this; }
    public Patient removeRecord(MedicalRecord record) { this.records.remove(record); return this; }
}

