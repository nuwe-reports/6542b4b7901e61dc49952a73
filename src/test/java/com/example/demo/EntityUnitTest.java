package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.example.demo.entities.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    private Doctor d1;

    private Patient p1;

    private Room r1;

    private Appointment a1;
    private Appointment a2;
    private Appointment a3;

    @BeforeEach
    void setUp() {

        LocalDateTime startsAt1 = LocalDateTime.now();
        LocalDateTime finishesAt1 = startsAt1.plusHours(1);

        LocalDateTime startsAt2 = startsAt1.plusMinutes(30); // overlaps with the first appointment
        LocalDateTime finishesAt2 = startsAt2.plusHours(1);

        LocalDateTime startsAt3 = startsAt1.plusHours(2); // does not overlap
        LocalDateTime finishesAt3 = startsAt3.plusHours(1);

        p1 = new Patient();
        p1.setFirstName("John");
        p1.setLastName("Smith");
        p1.setAge(45);
        p1.setEmail("john.smith@hospital.usa");

        d1 = new Doctor();
        d1.setFirstName("Dr. Jennifer");
        d1.setLastName("Williams");
        d1.setAge(38);
        d1.setEmail("dr.j.williams@hospital.usa");

        r1 = new Room("Room 101");

        a1 = new Appointment(p1, d1, r1, startsAt1, finishesAt1);
        a2 = new Appointment(p1, d1, r1, startsAt2, finishesAt2);
        a3 = new Appointment(p1, d1, r1, startsAt3, finishesAt3);
    }

    @Nested
    class PatientEntityTest {
        @Test
        void shouldCreatePatientInstanceTest() {
            entityManager.persistAndFlush(p1);
            Assertions.assertTrue(p1.getId() > 0);
        }

        @Test
        void shouldSetValuesInPatientTest() {
            entityManager.persistAndFlush(p1);
            Patient found = entityManager.find(Patient.class, p1.getId());
            assertThat(found.getFirstName()).isEqualTo(p1.getFirstName());
            assertThat(found.getLastName()).isEqualTo(p1.getLastName());
            assertThat(found.getAge()).isEqualTo(p1.getAge());
            assertThat(found.getEmail()).isEqualTo(p1.getEmail());
        }

        @Test
        void shouldRetrievePatientFromDatabaseTest() {
            entityManager.persistAndFlush(p1);
            Patient found = entityManager.find(Patient.class, p1.getId());
            assertThat(found).isEqualTo(p1);
        }

        @Test
        void patientFieldsNotEmptyOrNullTest() {

            Assertions.assertNotNull(p1.getFirstName());
            Assertions.assertNotNull(p1.getLastName());
            Assertions.assertTrue(p1.getAge() > 0);
            Assertions.assertNotNull(p1.getEmail());

            Assertions.assertNotEquals("", p1.getFirstName());
            Assertions.assertNotEquals("", p1.getLastName());
            Assertions.assertTrue(p1.getAge() > 0);
            Assertions.assertNotEquals("", p1.getEmail());

        }
    }

    @Nested
    class DoctorEntityTest {

        @Test
        void shouldCreateConstructorTest() {
            entityManager.persistAndFlush(d1);
            Assertions.assertTrue(d1.getId() > 0);
        }

        @Test
        void shouldSetValuesInDoctorTest() {
            entityManager.persistAndFlush(d1);
            Doctor found = entityManager.find(Doctor.class, d1.getId());
            assertThat(found.getFirstName()).isEqualTo(d1.getFirstName());
            assertThat(found.getLastName()).isEqualTo(d1.getLastName());
            assertThat(found.getAge()).isEqualTo(d1.getAge());
            assertThat(found.getEmail()).isEqualTo(d1.getEmail());
        }

        @Test
        void UniqueIdsTest() {
            Doctor doctor1 = new Doctor("Jennifer", "Williams", 35, "jennifer.w@example.com");
            Doctor doctor2 = new Doctor("John", "Snow", 40, "john.snow@example.com");


            entityManager.persistAndFlush(doctor1);
            entityManager.persistAndFlush(doctor2);

            Assertions.assertNotEquals(doctor1.getId(), doctor2.getId());
        }

        @Test
        void doctorFieldsNotEmptyOrNullTest() {

            Assertions.assertNotNull(d1.getFirstName());
            Assertions.assertNotNull(d1.getLastName());
            Assertions.assertTrue(d1.getAge() > 0);
            Assertions.assertNotNull(d1.getEmail());

            Assertions.assertNotEquals("", d1.getFirstName());
            Assertions.assertNotEquals("", d1.getLastName());
            Assertions.assertTrue(d1.getAge() > 0);
            Assertions.assertNotEquals("", d1.getEmail());

        }
    }

    @Nested
    class RoomEntityTest {
        @Test
        void GetRoomNameTest() {
            Assertions.assertEquals("Room 101", r1.getRoomName());
        }

        @Test
        void roomCreationTest() {
            entityManager.persistAndFlush(r1);
            Assertions.assertNotNull(r1.getRoomName());
            Room retrievedRoom = entityManager.find(Room.class, r1.getRoomName());
            Assertions.assertNotNull(retrievedRoom);
        }

        @Test
        void DefaultConstructorTest() {
            Room room = new Room();
            Assertions.assertNotNull(room);
        }

        @Test
        void parameterizedConstructorTest() {
            Assertions.assertNotNull(r1.getRoomName());
        }
    }

    @Nested
    class AppointmentEntityTest {

        @Test
        void IdGenerationTest() {
            entityManager.persistAndFlush(a1);
            Assertions.assertTrue(a1.getId() > 0);
        }

        @Test
        void GetersAndSettersTest() {
            Assertions.assertNotNull(a1.getPatient());
            Assertions.assertNotNull(a1.getDoctor());
            Assertions.assertNotNull(a1.getRoom());
            Assertions.assertNotNull(a1.getStartsAt());
            Assertions.assertNotNull(a1.getFinishesAt());

            Assertions.assertEquals(p1, a1.getPatient());
            Assertions.assertEquals(d1, a1.getDoctor());
            Assertions.assertEquals(r1, a1.getRoom());
        }

        @Test
        void overlapsAppointmentsTest() {
            Assertions.assertTrue(a1.overlaps(a2));
            Assertions.assertFalse(a1.overlaps(a3));
        }
    }
}
