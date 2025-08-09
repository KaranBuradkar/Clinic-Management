package com.clinic.main.controllers;

import com.clinic.main.dtos.AppointmentDto;
import com.clinic.main.dtos.AppointmentPerDoctorDTO;
import com.clinic.main.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // Schedule appointment
    @PostMapping("/appointment")
    public AppointmentDto scheduleAllAppointments(@RequestBody AppointmentDto appointmentDto) {
        return appointmentService.scheduleAppointment(appointmentDto);
    }

    // View all appointments
    @GetMapping("/appointments")
    public List<AppointmentDto> allAppointments() {
        return appointmentService.getAllAppointmentDtos();
    }

    // View appointment by ID
    @GetMapping("/appointments/{appointmentId}")
    public AppointmentDto appointmentById(@PathVariable("appointmentId") Long appointmentId) {
        return appointmentService.getAppointmentDtoById(appointmentId);
    }

    // View appointments by doctor ID
    @GetMapping("/appointments/byDoctor/{doctorId}")
    public List<AppointmentDto> appointmentByDoctorId(@PathVariable("doctorId") Long doctorId) {
        return appointmentService.getAppointmentDtosOfDoctorId(doctorId);
    }

    // View appointments by patient ID
    @GetMapping("/appointments/byPatient/{patientId}")
    public List<AppointmentDto> appointmentByPatientId(@PathVariable("patientId") Long patientId) {
        return appointmentService.getAppointmentDtosOfPatientId(patientId);
    }

    // View appointments by date
    @GetMapping("/appointments/byDate/{date}")
    public List<AppointmentDto> appointmentByDate(@PathVariable("date") LocalDate date) {
        return appointmentService.getAppointmentDtosByDate(date);
    }

    // View upcoming appointments
    @GetMapping("/appointments/upcoming")
    public List<AppointmentDto> upcomingAppointments() {
        return appointmentService.getUpcomingAppointmentDtos();
    }

    @GetMapping("/appointments/order-by/date/time")
    public List<AppointmentDto> appointmentsOrderByDateAndTime() {
        return appointmentService.getOrderedAppointmentDtosByDateAndTime();
    }

    @GetMapping("/appointments/page/{number}/{size}/{sort_by}")
    public List<AppointmentDto> appointmentPage(@PathVariable("number") Integer pageNumber,
                                                @PathVariable("size") Integer pageSize,
                                                @PathVariable("sort_by") String sortBy) {
        return appointmentService.getAppointmentDtoPage(pageNumber, pageSize, sortBy);
    }

    // Count appointments per doctor
    @GetMapping("/appointment/per/doctor")
    public List<AppointmentPerDoctorDTO> appointmentPerDoctor() {
        return appointmentService.getCountAppointmentsPerDoctor();
    }

    // Cancel appointment
    @DeleteMapping("/appointment/byId")
    public String cancelAppointment(@RequestBody AppointmentDto appointmentDto) {
        return appointmentService.cancelAppointment(appointmentDto);
    }
    @DeleteMapping("/appointment/byId/{id}")
    public String cancelAppointmentById(@PathVariable("id") Long appointmentId) {
        return appointmentService.cancelAppointmentById(appointmentId);
    }

    // Update appointment
    @PutMapping("/appointment")
    public AppointmentDto updateAppointment(@RequestBody AppointmentDto appointmentDto) {
        return appointmentService.updateAppointment(appointmentDto);
    }

    // Count daily or weekly appointments
//    @GetMapping("/appointment/per/day")
//    public List<WeeklyAppointmentCountDTO> appointmentCountPerDay() {
//        return appointmentService.getAppointmentCountPerDay();
//    }

//    @GetMapping("/appointment/per/last7days")
//    public List<WeeklyAppointmentCountDTO> appointmentCountLast7Days() {
//        return appointmentService.getAppointmentCountLast7Days();
//    }

}
