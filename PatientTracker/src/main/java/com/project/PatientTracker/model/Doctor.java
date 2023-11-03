package com.project.PatientTracker.model;

import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "Doctor")
public class Doctor extends User {
    @Column(name="specialty", nullable=true)
    private String specialty;

    @OneToMany(mappedBy = "doctor")
    private Set<Patient> patients;

    @OneToMany(mappedBy = "doctor")
    private Set<Appointment> appointments;

    public String getSpecialty() { return this.specialty; }
    public Doctor setSpecialty(String specialty) { this.specialty = specialty; return this; }

    public Set<Patient> getPatients() { return this.patients; }
    public Doctor addPatient(Patient patient) { this.patients.add(patient); return this; }
    public Doctor removePatient(Patient patient) { this.patients.remove(patient); return this; }

    public Set<Appointment> getAppointments() { return this.appointments; }
    public Doctor addAppointment(Appointment appointment) { this.appointments.add(appointment); return this; }
    public Doctor removeAppointment(Appointment appointment) { this.appointments.remove(appointment); return this; }
}

