package com.clinic.main.service;

import com.clinic.main.dtos.AppointmentDto;
import com.clinic.main.dtos.AppointmentPerDoctorDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface AppointmentService {

    // Schedule appointment
    AppointmentDto scheduleAppointment(AppointmentDto appointmentDto);
    // View all Appointments
    List<AppointmentDto> getAllAppointmentDtos();
    // View Appointment By ID
    AppointmentDto getAppointmentDtoById(Long appointmentId);
    // View Appointments by doctorId
    List<AppointmentDto> getAppointmentDtosOfDoctorId(Long doctorId);
    // View Appointments by patientId
    List<AppointmentDto> getAppointmentDtosOfPatientId(Long patientId);
    // View Appointments by specific date
    List<AppointmentDto> getAppointmentDtosByDate(LocalDate date);
    // View Upcoming Appointments
    List<AppointmentDto> getUpcomingAppointmentDtos();
    // Cancel Appointment
    String cancelAppointment(AppointmentDto appointmentDto);
    // Cancel Appointment by appointmentId
    String cancelAppointmentById(Long appointmentId);
    // Update Appointment
    AppointmentDto updateAppointment(AppointmentDto appointmentDto);
    // View Appointments Per Doctor
    List<AppointmentPerDoctorDTO> getCountAppointmentsPerDoctor();
    // View Appointment in order of date and time
    List<AppointmentDto> getOrderedAppointmentDtosByDateAndTime();
    // View Appointments in Page
    List<AppointmentDto> getAppointmentDtoPage(Integer pageNumber, Integer pageSize, String sortBy);

}
