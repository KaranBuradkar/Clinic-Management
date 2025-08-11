package com.clinic.main.controllers;

import com.clinic.main.dtos.AppointmentDto;
import com.clinic.main.dtos.AppointmentPerDoctorDTO;
import com.clinic.main.dtos.AppointmentRequestDto;
import com.clinic.main.entityMapper.AppointmentMapper;
import com.clinic.main.service.AppointmentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final AppointmentMapper appointmentMapper;

    public AppointmentController(AppointmentService appointmentService, AppointmentMapper appointmentMapper) {
        this.appointmentService = appointmentService;
        this.appointmentMapper = appointmentMapper;
    }

    @PostMapping
    public ResponseEntity<AppointmentDto> scheduleAppointment(@RequestBody AppointmentRequestDto request) {
        AppointmentDto dto = appointmentMapper.toDto(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(appointmentService.scheduleAppointment(dto, request.getPatientId(), request.getDoctorId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDto> getAppointmentById(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAppointmentDtoById(id));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<AppointmentDto>> getUpcomingAppointments() {
        return ResponseEntity.ok(appointmentService.getUpcomingAppointmentDtos());
    }

    @GetMapping("/statistics/by-doctor")
    public ResponseEntity<List<AppointmentPerDoctorDTO>> getAppointmentCountPerDoctor() {
        return ResponseEntity.ok(appointmentService.getCountAppointmentsPerDoctor());
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDto>> getAppointments(
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy
    ) {
//        if (doctorId == null && patientId == null && date == null) {
//            return ResponseEntity.ok(appointmentService.getAllAppointmentDtos(sortBy));
//        }
        return ResponseEntity.ok(appointmentService.getAppointmentsFiltered(doctorId, patientId, date, page, size, sortBy));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDto> updateAppointment(@PathVariable Long id, @RequestBody AppointmentDto dto) {
        return ResponseEntity.ok(appointmentService.updateAppointment(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelAppointment(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.cancelAppointmentById(id));
    }
}