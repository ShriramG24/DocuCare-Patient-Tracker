-- Create User Table
CREATE TABLE User (
    id SERIAL PRIMARY KEY,
    firstName VARCHAR(30) NOT NULL,
    lastName VARCHAR(30) NOT NULL,
    dateOfBirth DATE NOT NULL,
    age INT NOT NULL,
    email VARCHAR(30) UNIQUE NOT NULL,
    phone VARCHAR(10) UNIQUE,
    role VARCHAR(10)
);

-- Create Doctor Table
CREATE TABLE Doctor (
    id SERIAL PRIMARY KEY,
    userId FOREIGN KEY REFERENCES User(id),
    specialty VARCHAR(30)
);

-- Create Patient Table
CREATE TABLE Patient (
    id SERIAL PRIMARY KEY,
    userId FOREIGN KEY REFERENCES User(id),
    doctorId FOREIGN KEY REFERENCES Doctor(id),
    address VARCHAR(50) NOT NULL
);

-- Add Foreign Key Reference to Patients in Doctor Table
ALTER TABLE Doctor
ADD FOREIGN KEY patientId REFERENCES Patient(id); 

-- Create Appointment Table
CREATE TABLE Appointment (
    id SERIAL PRIMARY KEY,
    patientId FOREIGN KEY REFERENCES Patient(id),
    doctorId FOREIGN KEY REFERENCES Doctor(id),
    time DATETIME NOT NULL,
    status VARCHAR(20) NOT NULL,
    purpose TEXT NOT NULL,
    notes TEXT
);

-- Create File Table
CREATE TABLE File (
    id SERIAL PRIMARY KEY,
    patientId FOREIGN KEY REFERENCES Patient(id),
    doctorId FOREIGN KEY REFERENCES Doctor(id),
    name TEXT NOT NULL,
    location TEXT NOT NULL,
    lastUpdated DATETIME NOT NULL
);

-- Create MedicalRecord Table
CREATE TABLE MedicalRecord (
    id SERIAL PRIMARY KEY,
    patientId FOREIGN KEY REFERENCES Patient(id),
    doctorId FOREIGN KEY REFERENCES Doctor(id),
    fileId FOREIGN KEY REFERENCES File(id)
    data TEXT NOT NULL,
    type VARCHAR(20) NOT NULL
);