package com.project.PatientTracker.payload.response;

import java.util.List;

import com.project.PatientTracker.model.Doctor;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class DoctorResponse {
    @NotBlank
    @Getter @Setter
    private Long id;

    @NotBlank
    @Getter @Setter
    private String firstName;

    @NotBlank
    @Getter @Setter
    private String lastName;

    @NotBlank
    @Getter @Setter
    private int age;

    @NotBlank
    @Getter @Setter
    private String gender;

    @Getter @NotBlank
    @Setter
    private String email;

    @NotBlank
    @Getter @Setter
    private String phone;

    @Getter @Setter
    private String specialty;

    @Getter @Setter
    private String degree;

    @Getter @Setter
    private List<Long> patientIds;

    @Getter @Setter
    private List<Long> appointmentIds;

    @Getter @Setter
    private List<Long> fileIds;

    public DoctorResponse build(Doctor doctor) {
        return this.setId(doctor.getId())
            .setFirstName(doctor.getFirstName())
            .setLastName(doctor.getLastName())
            .setAge(doctor.getAge())
            .setGender(doctor.getGender())
            .setEmail(doctor.getEmail())
            .setPhone(doctor.getPhone())
            .setDegree(doctor.getDegree())
            .setSpecialty(doctor.getSpecialty())
            .setPatientIds(doctor.getPatients().stream().map(p -> p.getId()).toList())
            .setAppointmentIds(doctor.getAppointments().stream().map(a -> a.getId()).toList())
            .setFileIds(doctor.getFiles().stream().map(f -> f.getId()).toList());
    }
}