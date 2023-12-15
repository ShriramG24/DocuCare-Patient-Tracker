 package com.project.PatientTracker.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "Doctors")
@Accessors(chain = true)
public class Doctor extends User {
    Doctor(Integer id, String firstName, String lastName, String gender, int age, String email, String phone,
            String password, Role role) {
        super(id, firstName, lastName, gender, age, email, phone, password, role);
        //TODO Auto-generated constructor stub
    }
    @Column(name="degree", nullable=true)
    @Getter @Setter
    private String degree;

    @Column(name="specialty", nullable=true)
    @Getter @Setter
    private String specialty;

    @Column(name="experience", nullable=true)
    @Getter @Setter
    private Integer experience;

    @Column(name="clinic_addr", nullable=true)
    @Getter @Setter
    private String clinicAddr;

    @Column(name="rating", nullable=true)
    @Getter @Setter
    private Double rating;

    @JsonIgnore
    @ManyToMany(mappedBy = "doctors")
    @Getter
    private Set<Patient> patients = new HashSet<Patient>();

    @JsonIgnore
    @OneToMany(mappedBy = "doctor")
    @Getter
    private Set<Appointment> appointments = new HashSet<Appointment>();
    
    @JsonIgnore
    @OneToMany(mappedBy = "doctor")
    @Getter
    private Set<File> files = new HashSet<File>();

    public Doctor addPatient(Patient patient) { this.patients.add(patient); return this; }
    public Doctor removePatient(Patient patient) { this.patients.remove(patient); return this; }

    public Doctor addAppointment(Appointment appointment) { this.appointments.add(appointment); return this; }
    public Doctor removeAppointment(Appointment appointment) { this.appointments.remove(appointment); return this; }

    public User addFile(File file) { this.files.add(file); return this; }
    public User removeFile(File file) { this.files.remove(file); return this; }
}