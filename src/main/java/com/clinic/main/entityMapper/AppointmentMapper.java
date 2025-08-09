package com.clinic.main.entityMapper;

import com.clinic.main.dtos.AppointmentDto;
import com.clinic.main.entity.Appointment;
import com.clinic.main.entity.Doctor;
import com.clinic.main.entity.Patient;

import java.util.List;

public class AppointmentMapper {
    public static Appointment mapToAppointment(AppointmentDto dto, Doctor doctor, Patient patient) {
        Appointment appointment = new Appointment();
        if(dto.getId() != null) appointment.setId(dto.getId());
        appointment.setReason(dto.getReason());
        appointment.setDate(dto.getDate());
        appointment.setTime(dto.getTime());
        appointment.setCreatedAt(dto.getCreatedAt());

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        return appointment;
    }

    public static AppointmentDto mapToDto(Appointment appointment) {
        AppointmentDto appointmentDto = new AppointmentDto();

        appointmentDto.setId(appointment.getId());
        appointmentDto.setReason(appointment.getReason());
        appointmentDto.setDate(appointment.getDate());
        appointmentDto.setTime(appointment.getTime());
        appointmentDto.setCreatedAt(appointment.getCreatedAt());

        appointmentDto.setDoctorId(appointment.getDoctor().getId());
        appointmentDto.setPatientId(appointment.getPatient().getId());
        return appointmentDto;
    }

    public static List<AppointmentDto> mapToDto(List<Appointment> appointments) {
        return appointments.stream().map(AppointmentMapper::mapToDto).toList();
    }
}
