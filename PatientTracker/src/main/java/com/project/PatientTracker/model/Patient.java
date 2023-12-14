package com.project.PatientTracker.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "Patients")
@Accessors(chain = true)
public class Patient extends User {
    @Column(name="address", nullable=false, unique=true)
    @Getter @Setter
    private String address;

    @Column(name="allergies", nullable=true)
    @Getter @Setter
    private String allergies;

    @Column(name="medications", nullable=true)
    @Getter @Setter
    private String medications;

    @Column(name="diagnoses", nullable=true)
    @Getter @Setter
    private String diagnoses;

    @ManyToOne
    @JoinColumn(name = "doctorId")
    @Getter @Setter
    private Doctor doctor;

    @JsonIgnore
    @OneToMany(mappedBy = "patient")
    @Getter
    private Set<Appointment> appointments = new HashSet<Appointment>();

    @JsonIgnore
    @OneToMany(mappedBy = "patient")
    @Getter
    private Set<File> files = new HashSet<File>();

    public Patient addAppointment(Appointment appointment) { this.appointments.add(appointment); return this; }
    public Patient removeAppointment(Appointment appointment) { this.appointments.remove(appointment); return this; }

    public Patient addRecord(File file) { this.files.add(file); return this; }
    public Patient removeRecord(File file) { this.files.remove(file); return this; }
}

