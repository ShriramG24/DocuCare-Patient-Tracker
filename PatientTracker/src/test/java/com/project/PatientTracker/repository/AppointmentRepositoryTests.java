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
import com.project.PatientTracker.model.Appointment;
import com.project.PatientTracker.utils.TestUtils;

@SpringBootTest(classes = PatientTrackerApplication.class)
@TestPropertySources({
        @TestPropertySource(locations = "classpath:application-test.properties"),
        @TestPropertySource(properties = "spring.config.name=application-test")
})
@DataJpaTest
public class AppointmentRepositoryTests extends TestUtils {
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testFindAppointmentById() {
        // Arrange
        Appointment appointment = getTestAppointment(insertTestPatient(), insertTestDoctor());
        entityManager.persistAndFlush(appointment);

        // Act
        Appointment foundAppointment = appointmentRepository.findById(appointment.getId()).orElse(null);

        // Assert
        Assert.assertNotNull(appointment);
        Assert.assertEquals(foundAppointment.getPurpose(), appointment.getPurpose());
        Assert.assertEquals(foundAppointment.getStatus(), appointment.getStatus());
        Assert.assertEquals(foundAppointment.getTime(), appointment.getTime());
    }

    @Test
    public void testSaveAppointment() {
        // Arrange
        Appointment appointment = getTestAppointment(insertTestPatient(), insertTestDoctor());

        // Act
        Appointment savedAppointment = appointmentRepository.save(appointment);

        // Assert
        Assert.assertNotNull(savedAppointment);
        Assert.assertNotNull(savedAppointment.getId());
        Assert.assertEquals(savedAppointment.getPurpose(), appointment.getPurpose());
        Assert.assertEquals(savedAppointment.getStatus(), appointment.getStatus());
        Assert.assertEquals(savedAppointment.getTime(), appointment.getTime());
    }
}
