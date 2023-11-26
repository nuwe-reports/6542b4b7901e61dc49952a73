
package com.example.demo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import java.time.LocalDateTime;
import java.time.format.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.demo.controllers.*;
import com.example.demo.repositories.*;
import com.example.demo.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(DoctorController.class)
class DoctorControllerUnitTest {

    @MockBean
    private DoctorRepository doctorRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateDoctorTest() throws Exception {
        Doctor doctor = new Doctor("John", "Doe", 30, "john.doe@example.com");
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);

        mockMvc.perform(post("/api/doctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    void shouldDeleteDoctorByIdTest() throws Exception {
        Doctor doctor = new Doctor("John", "Doe", 30, "john.doe@example.com");
        when(doctorRepository.findById(12L)).thenReturn(Optional.of(doctor));
        doNothing().when(doctorRepository).deleteById(12L);

        mockMvc.perform(delete("/api/doctors/12"))
                .andExpect(status().isOk());
    }


    @Test
    void shouldGetAllDoctorsTest() throws Exception {
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor("John", "Smith", 35, "john.smith@example.com"));
        doctors.add(new Doctor("Jane", "Doe", 42, "jane.doe@example.com"));
        doctors.add(new Doctor("Michael", "Johnson", 40, "michael.johnson@example.com"));

        when(doctorRepository.findAll()).thenReturn(doctors);

        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].firstName").value("Jane"))
                .andExpect(jsonPath("$[2].firstName").value("Michael"))

                .andExpect(jsonPath("$[0].lastName").value("Smith"))
                .andExpect(jsonPath("$[1].lastName").value("Doe"))
                .andExpect(jsonPath("$[2].lastName").value("Johnson"))

                .andExpect(jsonPath("$[0].age").value(35))
                .andExpect(jsonPath("$[1].age").value(42))
                .andExpect(jsonPath("$[2].age").value(40))

                .andExpect(jsonPath("$[0].email").value("john.smith@example.com"))
                .andExpect(jsonPath("$[1].email").value("jane.doe@example.com"))
                .andExpect(jsonPath("$[2].email").value("michael.johnson@example.com"));
    }

    @Test
    void shouldGetDoctorByIdTest() throws Exception {
        Doctor doctor = new Doctor("John", "Doe", 30, "john.doe@example.com");
        when(doctorRepository.findById(12L)).thenReturn(Optional.of(doctor));

        mockMvc.perform(get("/api/doctors/12"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

    }

    @Test
    void shouldDeleteAllDoctorsTest() throws Exception {
        doNothing().when(doctorRepository).deleteAll();

        mockMvc.perform(delete("/api/doctors"))
                .andExpect(status().isOk());
    }
}


@Nested
@WebMvcTest(PatientController.class)
class PatientControllerUnitTest {

    @MockBean
    private PatientRepository patientRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreatePatientTest() throws Exception {
        Patient patient = new Patient("John", "Doe", 30, "john.doe@example.com");
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        mockMvc.perform(post("/api/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

    }

    @Test
    void shouldDeletePatientByIdTest() throws Exception {
        Patient patient = new Patient("John", "Doe", 30, "john.doe@example.com");
        when(patientRepository.findById(12L)).thenReturn(Optional.of(patient));
        doNothing().when(patientRepository).deleteById(12L);

        mockMvc.perform(delete("/api/patients/12"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetAllPatientsTest() throws Exception {
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient("Jane", "Doe", 42, "jane.doe@example.com"));
        patients.add(new Patient("Michael", "Johnson", 40, "michael.johnson@example.com"));
        patients.add(new Patient("John", "Smith", 35, "john.smith@example.com"));

        when(patientRepository.findAll()).thenReturn(patients);

        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Jane"))
                .andExpect(jsonPath("$[1].firstName").value("Michael"))
                .andExpect(jsonPath("$[2].firstName").value("John"))

                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[1].lastName").value("Johnson"))
                .andExpect(jsonPath("$[2].lastName").value("Smith"))

                .andExpect(jsonPath("$[0].age").value(42))
                .andExpect(jsonPath("$[1].age").value(40))
                .andExpect(jsonPath("$[2].age").value(35))

                .andExpect(jsonPath("$[0].email").value("jane.doe@example.com"))
                .andExpect(jsonPath("$[1].email").value("michael.johnson@example.com"))
                .andExpect(jsonPath("$[2].email").value("john.smith@example.com"));
    }

    @Test
    void shouldGetPatientByIdTest() throws Exception {
        Patient patient = new Patient("John", "Doe", 30, "john.doe@example.com");
        when(patientRepository.findById(12L)).thenReturn(Optional.of(patient));

        mockMvc.perform(get("/api/patients/12"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    void shouldDeleteAllPatientsTest() throws Exception {
        doNothing().when(patientRepository).deleteAll();

        mockMvc.perform(delete("/api/patients"))
                .andExpect(status().isOk());
    }
}

    @Nested
    @WebMvcTest(RoomController.class)
    class RoomControllerUnitTest {

        @MockBean
        private RoomRepository roomRepository;

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Test
        void shouldCreateRoomTest() throws Exception {
            Room room = new Room("Room 1");
            when(roomRepository.save(any(Room.class))).thenReturn(room);

            mockMvc.perform(post("/api/room")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(room)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.roomName").value("Room 1"));
        }

        @Test
        void shouldDeleteRoomByNameTest() throws Exception {
            Room room = new Room("Room 1");
            when(roomRepository.findByRoomName("Room 1")).thenReturn(Optional.of(room));
            doNothing().when(roomRepository).deleteByRoomName("Room 1");

            mockMvc.perform(delete("/api/rooms/Room 1"))
                    .andExpect(status().isOk());
        }

        @Test
        void shouldGetAllRoomsTest() throws Exception {
            List<Room> rooms = new ArrayList<>();
            rooms.add(new Room("Room 1"));
            rooms.add(new Room("Room 2"));
            rooms.add(new Room("Room 3"));

            when(roomRepository.findAll()).thenReturn(rooms);

            mockMvc.perform(get("/api/rooms"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].roomName").value("Room 1"))
                    .andExpect(jsonPath("$[1].roomName").value("Room 2"))
                    .andExpect(jsonPath("$[2].roomName").value("Room 3"));
        }

        @Test
        void shouldGetRoomByNameTest() throws Exception {
            Room room = new Room("Room 1");
            when(roomRepository.findByRoomName("Room 1")).thenReturn(Optional.of(room));

            mockMvc.perform(get("/api/rooms/Room 1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.roomName").value("Room 1"));
        }

        @Test
        void shouldDeleteAllRoomsTest() throws Exception {
            doNothing().when(roomRepository).deleteAll();

            mockMvc.perform(delete("/api/rooms"))
                    .andExpect(status().isOk());
        }
    }
