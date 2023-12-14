package com.project.PatientTracker.payload.response;

import java.util.List;

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
    private int age;

    @NotBlank
    @Getter @Setter
    private String gender;

    @NotBlank
    @Getter @Setter
    private String email;

    @NotBlank
    @Getter @Setter
    private String phone;

    @Getter @Setter
    private String address;

    @Getter @Setter
    private String allergies;

    @Getter @Setter    
    private String medications;

    @Getter @Setter
    private String diagnoses;

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
            .setAge(patient.getAge())
            .setGender(patient.getGender())
            .setEmail(patient.getEmail())
            .setPhone(patient.getPhone())
            .setAddress(patient.getAddress())
            .setDiagnoses(patient.getDiagnoses())
            .setMedications(patient.getMedications())
            .setAllergies(patient.getAllergies())
            .setDoctorId(patient.getDoctor() == null ? -1 : patient.getDoctor().getId())
            .setAppointmentIds(patient.getAppointments().stream().map(a -> a.getId()).toList())
            .setRecordIds(patient.getRecords().stream().map(r -> r.getId()).toList());
    }
}
