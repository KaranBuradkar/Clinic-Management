package com.clinic.main.service;

import com.clinic.main.dtos.AppointmentBasicDto;
import com.clinic.main.dtos.AppointmentDto;
import com.clinic.main.dtos.AppointmentPerDoctorDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface AppointmentService {

    // Schedule appointment
    AppointmentDto scheduleAppointment(AppointmentDto appointmentDto);

    AppointmentDto scheduleAppointment(AppointmentDto appointmentDto, Long patientId, Long doctorId);
    // View all Appointments
    List<AppointmentDto> getAllAppointmentDtos(String sortBy);
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
    // Cancel Appointment by appointmentId
    String cancelAppointmentById(Long appointmentId);
    // Update Appointment By ID
    AppointmentDto updateAppointment(Long appointmentId, AppointmentDto appointmentDto);
    // View Appointments Per Doctor
    List<AppointmentPerDoctorDTO> getCountAppointmentsPerDoctor();
    // View Appointment in order of date and time
    List<AppointmentDto> getOrderedAppointmentDtosByDateAndTime();
    // View Appointments in Page
    List<AppointmentDto> getAppointmentDtoPage(Integer pageNumber, Integer pageSize, String sortBy);
    // Get All Pagination, FilterBy Field and Sorted.
    List<AppointmentDto> getAppointmentsFiltered(Long doctorId, Long patientId, LocalDate date, int page, int size, String sortBy);
}
