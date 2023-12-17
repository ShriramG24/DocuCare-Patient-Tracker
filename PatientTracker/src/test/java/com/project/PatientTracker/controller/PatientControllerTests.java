package com.project.PatientTracker.controller;

import org.junit.jupiter.api.AfterEach;
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
import com.project.PatientTracker.model.Patient;
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
public class PatientControllerTests extends TestUtils {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    public void clearDatabase() {
        jdbcTemplate.execute("TRUNCATE TABLE files RESTART IDENTITY CASCADE;");
        jdbcTemplate.execute("TRUNCATE TABLE appointments RESTART IDENTITY CASCADE;");
        jdbcTemplate.execute("TRUNCATE TABLE patients RESTART IDENTITY CASCADE;");
        jdbcTemplate.execute("TRUNCATE TABLE doctors RESTART IDENTITY CASCADE;");
    }

    @Test
    @WithMockUser(username = "testUser", authorities = "ROLE_PATIENT")
    public void testGetPatients() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/patients"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));

        insertTestPatient();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/patients"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("johndoe@gmail.com"));
    }

    @Test
    @WithMockUser(username = "testUser", authorities = "ROLE_PATIENT")
    public void testGetPatientById() throws Exception {
        Patient patient = insertTestPatient();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/patients/{id}", patient.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(patient.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("johndoe@gmail.com"));
    }

    @Test
    @WithMockUser(username = "testUser", authorities = "ROLE_PATIENT")
    public void testPostPatient() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        
        mockMvc.perform(MockMvcRequestBuilders.post("/api/patients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(getTestPatient()))
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertEquals("johndoe@gmail.com", getPatients().get(0).getEmail());
    }

    @Test
    @WithMockUser(username = "testUser", authorities = "ROLE_PATIENT")
    public void testPutPatient() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        
        Patient patient = insertTestPatient();
        Assert.assertEquals("johndoe@gmail.com", patient.getEmail());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/patients/{id}", patient.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString((Patient) getTestPatient().setEmail("johndoe42@gmail.com")))
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Assert.assertEquals("johndoe42@gmail.com", getPatients().get(0).getEmail());
    }

    @Test
    @WithMockUser(username = "testUser", authorities = "ROLE_PATIENT")
    public void testDeletePatient() throws Exception {
        Patient patient = insertTestPatient();
        Assert.assertEquals(getPatients().size(), 1);
        
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/patients/{id}", patient.getId()))
            .andExpect(MockMvcResultMatchers.status().isOk());
        Assert.assertTrue(getPatients().isEmpty());
    }
}
