package com.clinic.main.controllers;

import com.clinic.main.dtos.PatientDto;
import com.clinic.main.service.PatientAppointmentFacade;
import com.clinic.main.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PatientController {

    @Autowired
    private PatientService patientService;
    @Autowired
    private PatientAppointmentFacade patientAppointmentFacade;

    // Add patient
    @PostMapping("/patient")
    public ResponseEntity<PatientDto> createPatient(@RequestBody PatientDto patientdto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(patientAppointmentFacade.addPatient(patientdto));
    }

    // View all patients
    @GetMapping("/patients")
    public ResponseEntity<List<PatientDto>> allPatient() {
//        return ResponseEntity.status(HttpStatus.OK).body(patientService.getAllPatient());
        return ResponseEntity.ok(patientService.getAllPatientDto());
    }

    // View patient by ID
    @GetMapping("/patients/{id}")
    public ResponseEntity<PatientDto> patientById(@PathVariable("id") Long patientId) {
        return new ResponseEntity<>(patientService.getPatientDtoById(patientId), HttpStatus.OK);
    }

    // Update patient
    @PutMapping("/patient")
    public PatientDto updatePatient(@RequestBody PatientDto patientDto) {
        return patientAppointmentFacade.updatePatient(patientDto);
    }

    // Delete patient
    @DeleteMapping("/patient")
    public String deletePatient(@RequestBody PatientDto patientDto) {
        return patientAppointmentFacade.deletePatient(patientDto);
    }

    // Delete patient By id
    @DeleteMapping("/patient/{id}")
    public String deletePatientById(@PathVariable Long patientId) {
        return patientService.deletePatientById(patientId);
    }

    // Filter patients by age range
    @GetMapping("/patients/filter/age/{lower}/{upper}")
    public List<PatientDto> filterPatientsByAgeRange(@PathVariable("lower") Integer lowerAge, @PathVariable("higher") Integer upperAge) {
        return patientService.getPatientDtoBetweenAge(lowerAge, upperAge);
    }

    @GetMapping("/patients/page/{number}/{size}/{sort_by}")
    public List<PatientDto> patientPage(@PathVariable("number") Integer pageNumber,
                                     @PathVariable("size") Integer pageSize,
                                     @PathVariable("sort_by") String sortBy) {
        return patientService.getPatientDtoPage(pageNumber, pageSize, sortBy);
    }

    @PatchMapping("/patient/{id}")
    public ResponseEntity<PatientDto> updateField(@PathVariable("id") Long id, @RequestBody Map<String, Object> updates) {
        return ResponseEntity.status(HttpStatus.OK).body(patientService.updateField(id, updates));
    }
}
