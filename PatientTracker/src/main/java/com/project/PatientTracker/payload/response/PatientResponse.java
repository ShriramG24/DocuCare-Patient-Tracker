package com.project.PatientTracker.payload.response;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.PatientTracker.model.Patient;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class PatientResponse {
    
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
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateOfBirth;

    @NotBlank
    @Getter @Setter
    private int age;

    @NotBlank
    @Getter @Setter
    private String email;

    @NotBlank
    @Getter @Setter
    private String phone;

    @Getter @Setter
    private String address;

    @Getter @Setter
    private Long doctorId;

    @Getter @Setter
    private List<Long> appointmentIds;

    @Getter @Setter
    private List<Long> recordIds;

    public PatientResponse build(Patient patient) {
        return this.setId(patient.getId())
            .setFirstName(patient.getFirstName())
            .setLastName(patient.getLastName())
            .setDateOfBirth(patient.getDateOfBirth())
            .setAge(patient.getAge())
            .setEmail(patient.getEmail())
            .setPhone(patient.getPhone())
            .setAddress(patient.getAddress())
            .setDoctorId(patient.getDoctor().getId())
            .setAppointmentIds(patient.getAppointments().stream().map(a -> a.getId()).toList())
            .setRecordIds(patient.getRecords().stream().map(r -> r.getId()).toList());
    }
}
