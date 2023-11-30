package com.project.PatientTracker.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@MappedSuperclass
@Accessors(chain = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column(name="firstName", nullable=false)
    @Getter @Setter
    private String firstName;

    @Column(name="lastName", nullable=false)
    @Getter @Setter
    private String lastName;

    @Column(name="dateOfBirth", nullable=false)
    @Getter @Setter
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateOfBirth;

    @Column(name="age", nullable=false)
    @Getter @Setter
    private int age;

    @Column(name="email", nullable=false, unique=true)
    @Getter @Setter
    private String email;

    @Column(name="phone", nullable=false, unique=true)
    @Getter @Setter
    private String phone;
}
