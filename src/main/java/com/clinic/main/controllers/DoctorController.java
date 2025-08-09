package com.clinic.main.controllers;

import com.clinic.main.dtos.DoctorDto;
import com.clinic.main.service.DoctorAppointmentFacade;
import com.clinic.main.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorAppointmentFacade doctorAppointmentFacade;

    @PostMapping("/doctor")
    public ResponseEntity<DoctorDto> createDoctor(@RequestBody DoctorDto doctorDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorAppointmentFacade.addDoctor(doctorDto));
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorDto>> allAppointments() {
        return ResponseEntity.ok(doctorService.getAllDoctorDtos());
    }

    @GetMapping("/doctors/{id}")
    public ResponseEntity<DoctorDto> doctorById(@PathVariable("id") Long doctorId) {
        return ResponseEntity.ok(doctorService.getDoctorDtoById(doctorId));
    }

    @GetMapping("/doctors/page/{pageNumber}")
    public List<DoctorDto> singlePageOfDoctors(@PathVariable("pageNumber") Integer pageNumber) {
        return doctorService.getAPageOfDoctorDto(pageNumber, 2, "id");
    }

    @GetMapping("/doctors/search/{specialization}")
    public List<DoctorDto> doctorsInSpecialize(@PathVariable("specialization") String specialization) {
        return doctorService.getDoctorDtosBySpecialization(specialization);
    }

    @GetMapping("/doctors/sort/name")
    public List<DoctorDto> sortsDoctorByName() {
        return doctorService.getDoctorDtosSortedBy("name");
    }

    @GetMapping("/doctors/sort/exp")
    public List<DoctorDto> sortsDoctorByExperience() {
        return doctorService.getDoctorDtosSortedBy("experience");
    }

    @PutMapping("/doctor/{id}")
    public DoctorDto updateDoctor(@RequestBody DoctorDto doctorDto, @PathVariable Long doctorId) {
        return doctorAppointmentFacade.updateDoctor(doctorDto, doctorId);
    }

    @PutMapping("/doctor")
    public DoctorDto updateDoctor(@RequestBody DoctorDto doctorDto) {
        return doctorAppointmentFacade.updateDoctor(doctorDto, doctorDto.getId());
    }

    @DeleteMapping("/doctor/{id}")
    public String deleteDoctorById(@PathVariable("id") Long doctorId) {
        return doctorService.deleteById(doctorId);
    }

    @DeleteMapping("/doctor")
    public String deleteDoctor(@RequestBody DoctorDto doctorDto) {
        return doctorAppointmentFacade.deleteDoctor(doctorDto);
    }
}
