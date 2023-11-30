package com.project.PatientTracker.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "Patients")
@Accessors(fluent = true)
public class Patient extends User {
    @Column(name="address", nullable=false, unique=true)
    @Getter @Setter
    private String address;

    @ManyToOne
    @JoinColumn(name = "doctorId")
    @Getter @Setter
    private Doctor doctor;

    @JsonIgnore
    @OneToMany(mappedBy = "patient")
    @Getter
    private Set<Appointment> appointments;

    @JsonIgnore
    @OneToMany(mappedBy = "patient")
    @Getter
    private Set<MedicalRecord> records;

    public Patient addAppointment(Appointment appointment) { this.appointments.add(appointment); return this; }
    public Patient removeAppointment(Appointment appointment) { this.appointments.remove(appointment); return this; }

    public Patient addRecord(MedicalRecord record) { this.records.add(record); return this; }
    public Patient removeRecord(MedicalRecord record) { this.records.remove(record); return this; }
}

