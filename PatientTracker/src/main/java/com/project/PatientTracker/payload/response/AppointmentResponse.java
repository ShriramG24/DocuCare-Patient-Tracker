package com.project.PatientTracker.payload.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.PatientTracker.model.Appointment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class AppointmentResponse {

    @NotBlank
    @Getter @Setter
    private Long doctorId;

    @NotBlank
    @Getter @Setter
    private Long patientId;

    @NotBlank
    @Getter @Setter
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date time;

    @NotBlank
    @Getter @Setter
    private String purpose;

    @NotBlank
    @Getter @Setter
    private String status;
    
    @Getter @Setter
    private String notes;

    public AppointmentResponse build(Appointment appointment) {
        return this.setTime(appointment.getTime())
            .setPurpose(appointment.getPurpose())
            .setStatus(appointment.getStatus())
            .setNotes(appointment.getNotes())
            .setDoctorId(appointment.getDoctor().getId())
            .setPatientId(appointment.getPatient().getId());
    }
}
