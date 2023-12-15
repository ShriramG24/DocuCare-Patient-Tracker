package com.project.PatientTracker.controller;

import org.junit.jupiter.api.AfterEach;
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
import com.project.PatientTracker.model.Doctor;
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
public class DoctorControllerTests extends TestUtils {
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
    public void testGetDoctors() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/doctors"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));

        insertTestDoctor();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/doctors"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("alicebarber@gmail.com"));
    }

    @Test
    public void testGetDoctorById() throws Exception {
        Doctor doctor = insertTestDoctor();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/doctors/{id}", doctor.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(doctor.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("alicebarber@gmail.com"));
    }

    @Test
    public void testGetDoctorsBySpecialty() throws Exception {
        Doctor doctor = insertTestDoctor();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/doctors/specialty/{specialty}", "Nutritionist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(doctor.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("alicebarber@gmail.com"));
    }

    @Test
    public void testGetDoctorsByRating() throws Exception {
        Doctor doctor = insertTestDoctor();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/doctors/rating/{rating}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(doctor.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("alicebarber@gmail.com"));
    }

    @Test
    public void testPostDoctor() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/doctors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getTestDoctor()))
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());

        Assert.assertEquals("alicebarber@gmail.com", getDoctors().get(0).getEmail());
    }

    @Test
    public void testPutDoctor() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Doctor doctor = insertTestDoctor();
        Assert.assertEquals("alicebarber@gmail.com", doctor.getEmail());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/doctors/{id}", doctor.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString((Doctor) getTestDoctor().setEmail("alicebarber42@gmail.com")))
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());

        Assert.assertEquals("alicebarber42@gmail.com", getDoctors().get(0).getEmail());
    }

    @Test
    public void testPutAssignPatientToDoctor() throws Exception {
        Doctor doctor = insertTestDoctor();
        Patient patient = insertTestPatient();

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/doctors/{doctorId}/assign-patient/{patientId}", doctor.getId(), patient.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assert.assertFalse(getDoctors().get(0).getPatients().isEmpty());
        Assert.assertTrue(getPatients().get(0).getDoctors().contains(doctor));
    }

    @Test
    public void testDeleteDoctor() throws Exception {
        Doctor doctor = insertTestDoctor();
        Assert.assertEquals(getDoctors().size(), 1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/doctors/{id}", doctor.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Assert.assertTrue(getDoctors().isEmpty());
    }
}
