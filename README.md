# DOCuCARE: Patient Tracking System (CS 520)

### Overview
Given the sheer scale of the current healthcare ecosystem, it is crucial that the process of tracking patient data is automated. Manual tasks such as doing paperwork, registering new patients, and mailing reports are not at all cost-effective or valuable uses of employees’ work hours. Challenges in this regard include, but are not limited to:

- Data Security: Physical records are obviously not secure, given that they can easily get lost, misplaced, or stolen. Storing them digitally is almost always better off, as they can be encrypted and stored in a secure manner.

- Limited Accessibility: It is difficult to store and retrieve information when everything is stored in physical documents; using an online system makes reports much more accessible to both doctors and patients.

- Errors and Inefficiency: Manually keeping track of patient records is prone to data entry mistakes and other human errors. Further, filling out physical paperwork or mailing reports is much more inefficient compared to having patients fill out forms online or uploading reports to a web portal.

Once developed, this patient-tracking application aims to alleviate these issues almost entirely, allowing for easy booking of appointments and handling of patient data. It is intended to be used by primarily doctors and patients, with each having a separate interface. Doctors will be able to view their patients’ data, update records, and notify patients of changes in prescriptions. Patients will be able to schedule appointments, view their records, and provide feedback. 

### Tech Stack

- Java SpringBoot (Backend)
- React (Frontend)
- Postgres RDMS (Data Storage)
- Amazon S3 / Firebase (Cloud File Storage)

### Running the Server

Switch into the `PatientTracker` directory and run the following command:

```
./mvnw spring-boot:run
```

This will start up the server on `http://localhost:8080/`.
