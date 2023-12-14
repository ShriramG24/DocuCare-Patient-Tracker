package com.project.PatientTracker;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.PatientTracker.model.*;
import com.project.PatientTracker.repository.*;

public class TestUtils {
    
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public List<Patient> getPatients() {
        return patientRepository.findAll();
    }

    public List<Doctor> getDoctors() {
        return doctorRepository.findAll();
    }

    public List<Appointment> getAppointments() {
        return appointmentRepository.findAll();
    }

    public List<File> getFiles() {
        return fileRepository.findAll();
    }

    public List<Prescription> getPrescriptions() {
        return prescriptionRepository.findAll();
    }

    public Patient getTestPatient() {
        return (Patient) new Patient()
            .setAddress("123 Main St")
            .setAllergies("{\"allergies\": []}")
            .setDiagnoses("{\"diagnoses\": []}")
            .setMedications("{\"medications\": []}")
            .setFirstName("John")
            .setLastName("Doe")
            .setAge(30)
            .setGender("male")
            .setEmail("johndoe@gmail.com")
            .setPhone("1234567890");
    }

    public Doctor getTestDoctor() {
        return (Doctor) new Doctor()
            .setSpecialty("Nutritionist")
            .setDegree("MBBS")
            .setRating(5.0)
            .setExperience(10)
            .setFirstName("Alice")
            .setLastName("Barber")
            .setAge(40)
            .setGender("Female")
            .setEmail("alicebarber@gmail.com")
            .setPhone("1234567890");
    }

    public Appointment getTestAppointment(Patient patient, Doctor doctor) {
        return new Appointment()
            .setDoctor(doctor)
            .setPatient(patient)
            .setTime(new Date())
            .setPurpose("Checkup")
            .setStatus("Scheduled")
            .setNotes("Patient is doing well.");
    }

    public File getTestFile(Patient patient, Doctor doctor) {
        return new File()
            .setDoctor(doctor)
            .setPatient(patient)
            .setType("medical-record")
            .setName((patient == null ? doctor : patient).getFirstName() + "_Medical_Record")
            .setLocation(UUID.randomUUID().toString())
            .setLastUpdated(new Date());
    }

    public Prescription getTestPrescription(Patient patient, Doctor doctor) {
        return new Prescription()
            .setDoctor(doctor)
            .setPatient(patient)
            .setMedicines("Tylenol")
            .setReports("Blood Test")
            .setInstructions("Take 1 tablet every 4 hours");
    }

    public Patient insertTestPatient() {
        return patientRepository.save(getTestPatient());
    }

    public Doctor insertTestDoctor() {
        return doctorRepository.save(getTestDoctor());
    }

    public Appointment insertTestAppointment(Patient patient, Doctor doctor) {
        return appointmentRepository.save(getTestAppointment(patient, doctor));
    }

    public File insertTestFile(Patient patient, Doctor doctor) {
        return fileRepository.save(getTestFile(patient, doctor));
    }

    public Prescription insertTestPrescription(Patient patient, Doctor doctor) {
        return prescriptionRepository.save(getTestPrescription(patient, doctor));
    }

    public void clearAll() {
        patientRepository.deleteAll();
        doctorRepository.deleteAll();
        appointmentRepository.deleteAll();
        fileRepository.deleteAll();
    }
}
