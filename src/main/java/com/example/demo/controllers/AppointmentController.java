package com.example.demo.controllers;

import com.example.demo.repositories.*;
import com.example.demo.entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class AppointmentController {
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentController(AppointmentRepository appointmentRepository){
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> getAllAppointments(){
        List<Appointment> appointments = new ArrayList<>();

        appointmentRepository.findAll().forEach(appointments::add);

        if (appointments.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/appointments/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable("id") long id){
        Optional<Appointment> appointment = appointmentRepository.findById(id);

        if (appointment.isPresent()){
            return new ResponseEntity<>(appointment.get(),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/appointment")
    public ResponseEntity<List<Appointment>> createAppointment(@RequestBody Appointment appointment) {

        Appointment newAppointment = new Appointment(
                appointment.getPatient(),
                appointment.getDoctor(),
                appointment.getRoom(),
                appointment.getStartsAt(),
                appointment.getFinishesAt()
        );


        if (isInvalidAppointment(newAppointment)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (isAppointmentConflict(newAppointment)) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        appointmentRepository.save(newAppointment);

        List<Appointment> allAppointments = appointmentRepository.findAll();
        return new ResponseEntity<>(allAppointments, HttpStatus.OK);
    }


    @DeleteMapping("/appointments/{id}")
    public ResponseEntity<HttpStatus> deleteAppointment(@PathVariable("id") long id){

        Optional<Appointment> appointment = appointmentRepository.findById(id);

        if (!appointment.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        appointmentRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/appointments")
    public ResponseEntity<HttpStatus> deleteAllAppointments(){
        appointmentRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // region: private methods
    private boolean isInvalidAppointment(Appointment appointment) {
        return appointment == null ||
                appointment.getStartsAt() == null ||
                appointment.getFinishesAt() == null ||
                appointment.getStartsAt().isEqual(appointment.getFinishesAt());
    }

    private boolean isAppointmentConflict(Appointment newAppointment) {
        List<Appointment> existingAppointments = appointmentRepository.findAll();

        return existingAppointments.stream()
                .anyMatch(newAppointment::overlaps);
    }
    // endregion



}