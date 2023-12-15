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
public class PrescriptionControllerTests extends TestUtils {
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
    public void testGetPrescriptionsByPatientId() throws Exception {
        insertTestPrescription(patient, doctor);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/prescriptions/patients/{patientId}", patient.getId()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].patient.id").value(patient.getId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].medicines").value("Tylenol"));
    }

    @Test
    public void testPostPrescription() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/prescriptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getTestPrescription(patient, doctor)))
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());

        Assert.assertEquals("Tylenol", getPrescriptions().get(0).getMedicines());
    }

    @Test
    public void testDeleteAppointment() throws Exception {
        insertTestPrescription(patient, doctor);
        Assert.assertEquals(getPrescriptions().size(), 1);
        
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/prescriptions/patients/{patientId}", patient.getId()))
            .andExpect(MockMvcResultMatchers.status().isOk());
        Assert.assertTrue(getAppointments().isEmpty());
    }
}
