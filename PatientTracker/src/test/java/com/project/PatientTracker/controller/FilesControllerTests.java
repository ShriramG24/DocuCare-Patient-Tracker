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
import com.project.PatientTracker.TestUtils;
import com.project.PatientTracker.model.*;

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
public class FilesControllerTests extends TestUtils {
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
    public void testGetFilesByUserId() throws Exception {
        insertTestFile(null, doctor);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/files/{userId}", doctor.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].doctor.email").value("alicebarber@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].patient").doesNotExist());

        insertTestFile(patient, null);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/files/{userId}", patient.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].patient.email").value("johndoe@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].doctor").doesNotExist());
    }

    @Test
    public void testPostFileById() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/files/{userId}", patient.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(getTestFile(patient, null)))
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Assert.assertEquals("John_Medical_Record", getFiles().get(0).getName());
    }

    @Test
    public void testDeletePatient() throws Exception {
        File file = insertTestFile(null, doctor);
        Assert.assertEquals(getFiles().size(), 1);
        
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/files/{id}", file.getId()))
            .andExpect(MockMvcResultMatchers.status().isOk());
        Assert.assertTrue(getFiles().isEmpty());
    }
}
