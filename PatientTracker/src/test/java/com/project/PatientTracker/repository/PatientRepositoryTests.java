package com.project.PatientTracker.repository;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.TestPropertySources;

import com.project.PatientTracker.PatientTrackerApplication;
import com.project.PatientTracker.model.Patient;
import com.project.PatientTracker.utils.TestUtils;

@SpringBootTest(classes = PatientTrackerApplication.class)
@TestPropertySources({
        @TestPropertySource(locations = "classpath:application-test.properties"),
        @TestPropertySource(properties = "spring.config.name=application-test")
})
@DataJpaTest
public class PatientRepositoryTests extends TestUtils {
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testFindPatientById() {
        // Arrange
        Patient patient = getTestPatient();
        entityManager.persistAndFlush(patient);

        // Act
        Patient foundPatient = patientRepository.findById(patient.getId()).orElse(null);

        // Assert
        Assert.assertNotNull(foundPatient);
        Assert.assertEquals(foundPatient.getEmail(), patient.getEmail());
    }

    @Test
    public void testSavePatient() {
        // Arrange
        Patient patient = getTestPatient();

        // Act
        Patient savedPatient = patientRepository.save(patient);

        // Assert
        Assert.assertNotNull(savedPatient);
        Assert.assertNotNull(savedPatient.getId());
        Assert.assertEquals(savedPatient.getEmail(), patient.getEmail());
    }
}