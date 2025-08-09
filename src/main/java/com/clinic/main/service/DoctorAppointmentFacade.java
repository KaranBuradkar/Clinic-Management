package com.clinic.main.service;

import com.clinic.main.dtos.DoctorDto;
import org.springframework.stereotype.Service;

@Service
public interface DoctorAppointmentFacade {
    // ADD Doctor
    DoctorDto addDoctor(DoctorDto doctorDto);
    // Update Doctor
    DoctorDto updateDoctor(DoctorDto doctorDto, Long doctorId);
    // Delete Doctor
    String deleteDoctor(DoctorDto doctorDto);
}
