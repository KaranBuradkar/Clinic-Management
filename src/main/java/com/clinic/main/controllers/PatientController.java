package com.clinic.main.controllers;

import com.clinic.main.dtos.PatientDto;
import com.clinic.main.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/patients") // Optional prefix for versioning or grouping
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // Create a new patient
    @PostMapping // POST /patients
    public ResponseEntity<PatientDto> createPatient(@RequestBody PatientDto patientDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(patientService.addPatient(patientDto));
    }

    // Get all patients
    @GetMapping
    public ResponseEntity<List<PatientDto>> patients() {
        return ResponseEntity.ok(patientService.getAllPatientDto());
    }

    // Get patient by ID
    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(patientService.getPatientDtoById(id));
    }

    // Update entire patient record
    @PutMapping("/{id}")
    public ResponseEntity<PatientDto> updatePatient(@PathVariable Long id, @RequestBody PatientDto patientDto) {
        patientDto.setId(id); // Ensure ID consistency
        return ResponseEntity.ok(patientService.updatePatient(id, patientDto));
    }

    // Delete patient by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatientById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.deletePatientById(id));
    }

    // Filter patients by age range
    @GetMapping("/filter")
    public ResponseEntity<List<PatientDto>> filterByAgeRange(
            @RequestParam Integer ageMin,
            @RequestParam Integer ageMax
    ) {
        return ResponseEntity.ok(patientService.getPatientDtoBetweenAge(ageMin, ageMax));
    }

    // Get patients with pagination and sorting
    @GetMapping("/page")
    public ResponseEntity<List<PatientDto>> getPaginatedPatients(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String dir
    ) {
        return ResponseEntity.ok(patientService.getPatientDtoPage(page, size, sortBy, dir));
    }

    // Partially update patient (e.g., name or other fields)
    @PatchMapping("/{id}")
    public ResponseEntity<PatientDto> updatePatientFields(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates
    ) {
        return ResponseEntity.ok(patientService.updateField(id, updates));
    }
}
