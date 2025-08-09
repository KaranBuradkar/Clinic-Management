package com.clinic.main;

import com.clinic.main.dtos.AppointmentDto;
import com.clinic.main.dtos.AppointmentPerDoctorDTO;
import com.clinic.main.entity.Appointment;
import com.clinic.main.repository.AppointmentRepository;
import com.clinic.main.service.AppointmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest
public class AppointmentTests {

    @Autowired
    private AppointmentService appointmentService;

//    @Autowired
//    private AppointmentRepository appointmentRepository;

    @Test
    public void testAppointmentService() {

        AppointmentDto newAppointmentDto = new AppointmentDto();
        newAppointmentDto.setReason("Head Pain.");
        newAppointmentDto.setDoctorId(3L);
        newAppointmentDto.setPatientId(5L);
        newAppointmentDto.setTime(LocalTime.of(3, 35));
        newAppointmentDto.setDate(LocalDate.of(2025, 8, 18));
        newAppointmentDto.setCreatedAt(LocalDateTime.now());
        // Schedule New Appointment
        AppointmentDto savedAppointmentDto = appointmentService.scheduleAppointment(newAppointmentDto);
        System.out.println("Scheduled Appointment: "+savedAppointmentDto);

        // Update Appointment
//        savedAppointmentDto.setTime(LocalTime.of(4, 30));
//        AppointmentDto updatedAppointmentDto = appointmentService.updateAppointment(savedAppointmentDto);
//        System.out.println("Updated Appointment: "+updatedAppointmentDto);
//        // Delete Appointment
//        String message = appointmentService.cancelAppointment(updatedAppointmentDto);
//        System.out.println(message);

    }

    @Test
    public void testRepositoryGetMethods() {

        // View all Appointments
        List<AppointmentDto> appointmentDtos = appointmentService.getAllAppointmentDtos();
        System.out.println("View all Appointments: "+appointmentDtos);

        // View Appointment By ID
        AppointmentDto appointmentDto = appointmentService.getAppointmentDtoById(3L);
        System.out.println("View Appointment By ID: "+appointmentDto);

        // View Appointments by doctorId
        appointmentDtos = appointmentService.getAppointmentDtosOfDoctorId(5L);
        System.out.println("View Appointments by doctorId: "+appointmentDtos);

        // View Appointments by patientId
        appointmentDtos = appointmentService.getAppointmentDtosOfPatientId(1L);
        System.out.println("View Appointments by patientId: "+appointmentDtos);

        // View Appointments by specific date
        appointmentDtos = appointmentService.getAppointmentDtosByDate(LocalDate.of(2025, 8, 13));

        // View Upcoming Appointments
        appointmentDtos = appointmentService.getUpcomingAppointmentDtos();
        System.out.println("View Upcoming Appointments: "+appointmentDtos);

        // View Appointments Per Doctor
        List<AppointmentPerDoctorDTO> doctorAppointmentCount = appointmentService.getCountAppointmentsPerDoctor();
        System.out.println("View Appointments Per Doctor: "+doctorAppointmentCount);

        // View Appointment in order of date and time
        appointmentDtos = appointmentService.getOrderedAppointmentDtosByDateAndTime();
        System.out.println("View Appointment in order of date and time: "+appointmentDtos);

        // View Appointments in Page
        appointmentDtos = appointmentService.getAppointmentDtoPage(0, 4, "id");
        System.out.println("View Appointments in Page: "+appointmentDtos);
    }

    @Test
    public void testRepositoryDoctorDetailModification() {
        AppointmentDto newAppointmentDto = new AppointmentDto();
        newAppointmentDto.setReason("Back Pain.");
        newAppointmentDto.setDoctorId(4L);
        newAppointmentDto.setPatientId(5L);
        newAppointmentDto.setTime(LocalTime.of(6, 35));
        newAppointmentDto.setDate(LocalDate.of(2025, 8, 18));
        newAppointmentDto.setCreatedAt(LocalDateTime.now());

        // ADD NEW APPOINTMENT
        System.out.println("New Appointment Added: ");
        AppointmentDto addedAppointmentDto = appointmentService.scheduleAppointment(newAppointmentDto);
        System.out.println(addedAppointmentDto);

//        System.out.println(appointmentRepository.findById(addedAppointmentDto.getId()));

        // Update Appointment
        System.out.println("Update Appointment: ");
        addedAppointmentDto.setTime(LocalTime.of(7, 35));
        AppointmentDto updatedAppointment = appointmentService.updateAppointment(addedAppointmentDto);
        System.out.println(updatedAppointment);

        // Cancel Appointment
        System.out.println("Cancel Appointment: ");
        System.out.println(appointmentService.cancelAppointment(addedAppointmentDto));

//        String cancelAppointmentById(Long appointmentId);
    }
}
