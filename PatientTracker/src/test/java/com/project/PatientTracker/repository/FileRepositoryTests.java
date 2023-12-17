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
import com.project.PatientTracker.model.File;
import com.project.PatientTracker.utils.TestUtils;

@SpringBootTest(classes = PatientTrackerApplication.class)
@TestPropertySources({
        @TestPropertySource(locations = "classpath:application-test.properties"),
        @TestPropertySource(properties = "spring.config.name=application-test")
})
@DataJpaTest
public class FileRepositoryTests extends TestUtils {
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testFindById() {
        // Arrange
        File file = getTestFile(insertTestPatient(), insertTestDoctor());
        entityManager.persistAndFlush(file);

        // Act
        File foundFile = fileRepository.findById(file.getId()).orElse(null);

        // Assert
        Assert.assertNotNull(file);
        Assert.assertEquals(foundFile.getName(), file.getName());
        Assert.assertEquals(foundFile.getLocation(), file.getLocation());
    }

    @Test
    public void testSaveFile() {
        // Arrange
        File file = getTestFile(insertTestPatient(), insertTestDoctor());

        // Act
        File savedFile = fileRepository.save(file);

        // Assert
        Assert.assertNotNull(savedFile);
        Assert.assertNotNull(savedFile.getId());
        Assert.assertEquals(savedFile.getName(), file.getName());
        Assert.assertEquals(savedFile.getLocation(), file.getLocation());
    }
}
