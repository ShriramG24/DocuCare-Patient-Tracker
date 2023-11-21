package com.project.PatientTracker.model;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "Users")
@Inheritance(strategy=InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="firstName", nullable=false)
    private String firstName;

    @Column(name="lastName", nullable=false)
    private String lastName;

    @Column(name="dateOfBirth", nullable=false)
    private Date dateOfBirth;

    @Column(name="age", nullable=false)
    private int age;

    @Column(name="email", nullable=false, unique=true)
    private String email;

    @Column(name="phone", nullable=true, unique=true)
    private String phone;

    @Column(name="role", nullable=false)
    private String role;

    @OneToMany(mappedBy = "owner")
    private Set<File> files;

    public Long getId() { return this.id; }
    public User setId(Long id) { this.id = id; return this; }

    public String getFirstName() { return this.firstName; }
    public User setFirstName(String firstName) { this.firstName = firstName; return this; }

    public String getLastName() { return this.lastName; }
    public User setLastName(String lastName) { this.lastName = lastName; return this; }

    public Date getDateOfBirth() { return this.dateOfBirth; }
    public User setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; return this; }

    public int getAge() { return this.age; }
    public User setAge(int age) { this.age = age; return this; }

    public String getEmail() { return this.email; }
    public User setEmail(String email) { this.email = email; return this; }

    public String getPhone() { return this.phone; }
    public User setPhone(String phone) { this.phone = phone; return this; }

    public String getRole() { return this.role; }
    public User setRole(String role) { this.role = role; return this; }

    public Set<File> getFiles() { return this.files; }
    public User addFile(File file) { this.files.add(file); return this; }
    public User removeFile(File file) { this.files.remove(file); return this; }
}
