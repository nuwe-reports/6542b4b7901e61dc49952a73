# Appointment Controller Documentation

This controller handles operations related to medical appointments in the application.

## Endpoints

### Get All Appointments

```http
GET /api/appointments
```

Gets all appointments stored in the system.

#### Responses

- 200 OK: The request was successful. Returns a list of appointments.
- 204 No Content: No appointments are available.

### Get Appointment by ID

```http
GET /api/appointments/{id}
```

Gets a specific appointment by its ID.

#### URL Parameters

- `id` (long): ID of the appointment to be retrieved.

#### Responses

- 200 OK: The request was successful. Returns the details of the appointment.
- 404 Not Found: No appointment found with the provided ID.

### Create a New Appointment

```http
POST /api/appointment
```

Creates a new appointment in the system.

#### Request Body Parameters

- `patient` (String): Patient's name.
- `doctor` (String): Doctor's name.
- `room` (String): Room number.
- `startsAt` (DateTime): Date and time of the appointment start.
- `finishesAt` (DateTime): Date and time of the appointment end.

#### Responses

- 200 OK: Appointment created successfully. Returns the updated list of all appointments.
- 400 Bad Request: The request is invalid (e.g., incomplete appointment data).
- 406 Not Acceptable: Appointment conflict (overlap with an existing appointment).

### Delete Appointment by ID

```http
DELETE /api/appointments/{id}
```

Deletes a specific appointment by its ID.

#### URL Parameters

- `id` (long): ID of the appointment to be deleted.

#### Responses

- 200 OK: Appointment deleted successfully.
- 404 Not Found: No appointment found with the provided ID.

### Delete All Appointments

```http
DELETE /api/appointments
```

Deletes all appointments stored in the system.

#### Responses

- 200 OK: All appointments deleted successfully.



# Doctor Controller Documentation

This controller handles operations related to doctors in the application.

## Endpoints

### Get All Doctors

```http
GET /api/doctors
```

Gets all doctors stored in the system.

#### Responses

- 200 OK: The request was successful. Returns a list of doctors.
- 204 No Content: No doctors are available.

### Get Doctor by ID

```http
GET /api/doctors/{id}
```

Gets a specific doctor by its ID.

#### URL Parameters

- `id` (long): ID of the doctor to be retrieved.

#### Responses

- 200 OK: The request was successful. Returns the details of the doctor.
- 404 Not Found: No doctor found with the provided ID.

### Create a New Doctor

```http
POST /api/doctor
```

Creates a new doctor in the system.

#### Request Body Parameters

- `firstName` (String): Doctor's first name.
- `lastName` (String): Doctor's last name.
- `age` (int): Doctor's age.
- `email` (String): Doctor's email.

#### Responses

- 201 Created: Doctor created successfully. Returns the details of the created doctor.

### Delete Doctor by ID

```http
DELETE /api/doctors/{id}
```

Deletes a specific doctor by its ID.

#### URL Parameters

- `id` (long): ID of the doctor to be deleted.

#### Responses

- 200 OK: Doctor deleted successfully.
- 404 Not Found: No doctor found with the provided ID.

### Delete All Doctors

```http
DELETE /api/doctors
```

Deletes all doctors stored in the system.

#### Responses

- 200 OK: All doctors deleted successfully.



# Patient Controller Documentation

This controller handles operations related to patients in the application.

## Endpoints

### Get All Patients

```http
GET /api/patients
```

Gets all patients stored in the system.

#### Responses

- 200 OK: The request was successful. Returns a list of patients.
- 204 No Content: No patients are available.

### Get Patient by ID

```http
GET /api/patients/{id}
```

Gets a specific patient by its ID.

#### URL Parameters

- `id` (long): ID of the patient to be retrieved.

#### Responses

- 200 OK: The request was successful. Returns the details of the patient.
- 404 Not Found: No patient found with the provided ID.

### Create a New Patient

```http
POST /api/patient
```

Creates a new patient in the system.

#### Request Body Parameters

- `firstName` (String): Patient's first name.
- `lastName` (String): Patient's last name.
- `age` (int): Patient's age.
- `email` (String): Patient's email.

#### Responses

- 201 Created: Patient created successfully. Returns the details of the created patient.

### Delete Patient by ID

```http
DELETE /api/patients/{id}
```

Deletes a specific patient by its ID.

#### URL Parameters

- `id` (long): ID of the patient to be deleted.

#### Responses

- 200 OK: Patient deleted successfully.
- 404 Not Found: No patient found with the provided ID.

### Delete All Patients

```http
DELETE /api/patients
```

Deletes all patients stored in the system.

#### Responses

- 200 OK: All patients deleted successfully.



# Room Controller Documentation

This controller handles operations related to rooms in the application.

## Endpoints

### Get All Rooms

```http
GET /api/rooms
```

Gets all rooms stored in the system.

#### Responses

- 200 OK: The request was successful. Returns a list of rooms.
- 204 No Content: No rooms are available.

### Get Room by Room Name

```http
GET /api/rooms/{roomName}
```

Gets a specific room by its name.

#### URL Parameters

- `roomName` (String): Name of the room to be retrieved.

#### Responses

- 200 OK: The request was successful. Returns the details of the room.
- 404 Not Found: No room found with the provided name.

### Create a New Room

```http
POST /api/room
```

Creates a new room in the system.

#### Request Body Parameters

- `roomName` (String): Name of the room.

#### Responses

- 201 Created: Room created successfully. Returns the details of the created room.

### Delete Room by Room Name

```http
DELETE /api/rooms/{roomName}
```

Deletes a specific room by its name.

#### URL Parameters

- `roomName` (String): Name of the room to be deleted.

#### Responses

- 200 OK: Room deleted successfully.
- 404 Not Found: No room found with the provided name.

### Delete All Rooms

```http
DELETE /api/rooms
```

Deletes all rooms stored in the system.

#### Responses

- 200 OK: All rooms deleted successfully.


## Extra - UML Diagram
![UML Accenture.svg](docs%2FUML%20Accenture.svg)