package com.project.PatientTracker.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.TestPropertySources;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.project.PatientTracker.PatientTrackerApplication;
import com.project.PatientTracker.model.*;
import com.project.PatientTracker.service.FileService;
import com.project.PatientTracker.utils.TestUtils;

import jakarta.servlet.ServletException;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
public class FileControllerTests extends TestUtils {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Mock
    private FileService fileService;

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
    @WithMockUser(username = "testUser", authorities = "ROLE_DOCTOR")
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
    @WithMockUser(username = "testUser", authorities = "ROLE_DOCTOR")
    public void testPostFileById() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Test content".getBytes());

        // Mocking fileService.uploadFile method
        when(fileService.uploadFile(any())).thenReturn("uploadedFileName.txt");
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/files/{userId}", doctor.getId())
                .file(file)).andExpect(MockMvcResultMatchers.status().isOk());

        Assert.assertTrue(getFiles().get(0).getName().contains("test.txt"));
    }

    @Test
    @WithMockUser(username = "testUser", authorities = "ROLE_DOCTOR")
    public void testDeleteFile() throws Exception {
        File file = insertTestFile(null, doctor);
        Assert.assertEquals(getFiles().size(), 1);

        assertThrows(ServletException.class,
                () -> mockMvc.perform(MockMvcRequestBuilders.delete("/api/files/{id}", file.getId())));
    }
}