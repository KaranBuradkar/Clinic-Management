package com.clinic.main.controllers;

import com.clinic.main.dtos.DoctorDto;
import com.clinic.main.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // Create doctor
    @PostMapping
    public ResponseEntity<DoctorDto> createDoctor(@RequestBody DoctorDto doctorDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(doctorService.addDoctor(doctorDto));
    }

    // Get all doctors
    @GetMapping
    public ResponseEntity<List<DoctorDto>> getAllDoctors(
            @RequestParam(required = false) String specialization,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        if (specialization != null) {
            return ResponseEntity.ok(doctorService.getDoctorDtosBySpecialization(specialization));
        }
        return ResponseEntity.ok(doctorService.getAPageOfDoctorDto(page, size, sortBy));
    }

    // Get doctor by ID
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getDoctorDtoById(id));
    }

    // Update doctor by ID
    @PutMapping("/{id}")
    public ResponseEntity<DoctorDto> updateDoctor(@PathVariable Long id, @RequestBody DoctorDto doctorDto) {
        return ResponseEntity.ok(doctorService.updateDoctor(id, doctorDto));
    }

    // Delete doctor by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctorById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.deleteById(id));
    }
}
