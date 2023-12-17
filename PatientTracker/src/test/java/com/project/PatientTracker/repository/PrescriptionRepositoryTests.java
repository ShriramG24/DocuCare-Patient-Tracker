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
import com.project.PatientTracker.model.Prescription;
import com.project.PatientTracker.utils.TestUtils;

@SpringBootTest(classes = PatientTrackerApplication.class)
@TestPropertySources({
        @TestPropertySource(locations = "classpath:application-test.properties"),
        @TestPropertySource(properties = "spring.config.name=application-test")
})
@DataJpaTest
public class PrescriptionRepositoryTests extends TestUtils {
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testFindPrescriptionById() {
        // Arrange
        Prescription prescription = getTestPrescription(insertTestPatient(), insertTestDoctor());
        entityManager.persistAndFlush(prescription);

        // Act
        Prescription foundPrescription = prescriptionRepository.findById(prescription.getId()).orElse(null);

        // Assert
        Assert.assertNotNull(prescription);
        Assert.assertEquals(foundPrescription.getMedicines(), prescription.getMedicines());
        Assert.assertEquals(foundPrescription.getReports(), prescription.getReports());
        Assert.assertEquals(foundPrescription.getInstructions(), prescription.getInstructions());
    }

    @Test
    public void testSavePrescription() {
        // Arrange
        Prescription prescription = getTestPrescription(insertTestPatient(), insertTestDoctor());

        // Act
        Prescription savedPrescription = prescriptionRepository.save(prescription);

        // Assert
        Assert.assertNotNull(savedPrescription);
        Assert.assertNotNull(savedPrescription.getId());
        Assert.assertEquals(savedPrescription.getMedicines(), prescription.getMedicines());
        Assert.assertEquals(savedPrescription.getReports(), prescription.getReports());
        Assert.assertEquals(savedPrescription.getInstructions(), prescription.getInstructions());
    }
}