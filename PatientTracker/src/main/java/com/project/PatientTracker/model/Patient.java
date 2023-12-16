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

    @ManyToMany
    @JoinTable(
        name = "doctors_patients", 
        joinColumns = { @JoinColumn(name = "doctorId") }, 
        inverseJoinColumns = { @JoinColumn(name = "patientId") }
    )
    @Getter
    private Set<Doctor> doctors = new HashSet<Doctor>();

    @JsonIgnore
    @OneToMany(mappedBy = "patient")
    @Getter
    private Set<Appointment> appointments = new HashSet<Appointment>();

    @JsonIgnore
    @OneToMany(mappedBy = "patient")
    @Getter
    private Set<File> files = new HashSet<File>();

    public Patient addDoctor(Doctor doctor) { this.doctors.add(doctor); return this; }
    public Patient removeDoctor(Doctor doctor) { this.doctors.remove(doctor); return this; }

    public Patient addAppointment(Appointment appointment) { this.appointments.add(appointment); return this; }
    public Patient removeAppointment(Appointment appointment) { this.appointments.remove(appointment); return this; }

    public Patient addFile(File file) { this.files.add(file); return this; }
    public Patient removeFile(File file) { this.files.remove(file); return this; }
}

