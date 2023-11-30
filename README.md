# CS 520 - Patient Tracker Application

### Prerequisites

Make sure you have [Java](https://www.java.com/download/ie_manual.jsp) and [Postgres](https://www.postgresql.org/download/) installed on your machine. Clone the repository by running this command:

```
git clone https://github.com/ShriramG24/patient-tracker-app-520.git
```

Before running the application, ensure that your Postgres installation has a database named `postgres` (should exist by default; if not, create it). Also check that you have a user named `postgres` (with access to the `postgres` database) and set the user's password to `postgres`. This must be done in order for Spring Boot to create the necessary tables on application startup.

### Running the Server

Switch into the `PatientTracker` directory and run the following command:

```
./mvnw spring-boot:run
```

This will start up the server on `http://localhost:8080/`.
