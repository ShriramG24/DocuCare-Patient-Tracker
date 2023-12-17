package com.project.PatientTracker.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.TestPropertySources;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.PatientTracker.PatientTrackerApplication;
import com.project.PatientTracker.model.*;
import com.project.PatientTracker.utils.TestUtils;

import org.junit.Assert;

@Transactional
@Rollback
@AutoConfigureMockMvc
@SpringBootTest(classes = PatientTrackerApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySources({
        @TestPropertySource(locations = "classpath:application-test.properties"),
        @TestPropertySource(properties = "spring.config.name=application-test")
})
public class AppointmentControllerTests extends TestUtils {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Patient patient;
    private Doctor doctor;

    @BeforeEach
    public void insertPatientAndDoctor() {
        patient = insertTestPatient();
        doctor = insertTestDoctor();
    }

    @AfterEach
    public void clearDatabase() {
        jdbcTemplate.execute("TRUNCATE TABLE files RESTART IDENTITY CASCADE;");
        jdbcTemplate.execute("TRUNCATE TABLE appointments RESTART IDENTITY CASCADE;");
        jdbcTemplate.execute("TRUNCATE TABLE patients RESTART IDENTITY CASCADE;");
        jdbcTemplate.execute("TRUNCATE TABLE doctors RESTART IDENTITY CASCADE;");
    }

    @Test
    @WithMockUser(username = "testUser", authorities = "ROLE_PATIENT")
    public void testGetAppointmentById() throws Exception {
        Appointment appointment = insertTestAppointment(patient, doctor);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/appointments/{id}", appointment.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(appointment.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.doctor.email").value("alicebarber@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.patient.email").value("johndoe@gmail.com"));
    }

    @Test
    @WithMockUser(username = "testUser", authorities = "ROLE_PATIENT")
    public void testPostAppointment() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        
        mockMvc.perform(MockMvcRequestBuilders.post("/api/appointments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(getTestAppointment(patient, doctor)))
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertEquals("Scheduled", getAppointments().get(0).getStatus());
    }

    @Test
    @WithMockUser(username = "testUser", authorities = "ROLE_PATIENT")
    public void testPutAppointment() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        
        Appointment appointment = insertTestAppointment(patient, doctor);
        Assert.assertEquals("Scheduled", appointment.getStatus());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/appointments/{id}", appointment.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(appointment.setStatus("Completed")))
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Assert.assertEquals("Completed", getAppointments().get(0).getStatus());
    }

    @Test
    @WithMockUser(username = "testUser", authorities = "ROLE_PATIENT")
    public void testDeleteAppointment() throws Exception {
        Appointment appointment = insertTestAppointment(patient, doctor);
        Assert.assertEquals(getAppointments().size(), 1);
        
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/appointments/{id}", appointment.getId()))
            .andExpect(MockMvcResultMatchers.status().isOk());
        Assert.assertTrue(getAppointments().isEmpty());
    }
}
