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
import com.project.PatientTracker.model.Doctor;
import com.project.PatientTracker.utils.TestUtils;

@SpringBootTest(classes = PatientTrackerApplication.class)
@TestPropertySources({
        @TestPropertySource(locations = "classpath:application-test.properties"),
        @TestPropertySource(properties = "spring.config.name=application-test")
})
@DataJpaTest
public class DoctorRepositoryTests extends TestUtils {
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testFindDoctorById() {
        // Arrange
        Doctor doctor = getTestDoctor();
        entityManager.persistAndFlush(doctor);

        // Act
        Doctor foundDoctor = doctorRepository.findById(doctor.getId()).orElse(null);

        // Assert
        Assert.assertNotNull(doctor);
        Assert.assertEquals(foundDoctor.getEmail(), doctor.getEmail());
    }

    @Test
    public void testSaveDoctor() {
        // Arrange
        Doctor doctor = getTestDoctor();

        // Act
        Doctor savedDoctor = doctorRepository.save(doctor);

        // Assert
        Assert.assertNotNull(savedDoctor);
        Assert.assertNotNull(savedDoctor.getId());
        Assert.assertEquals(savedDoctor.getEmail(), doctor.getEmail());
    }
}
