package com.clinic.main.entityMapper;

import com.clinic.main.dtos.DoctorDto;
import com.clinic.main.entity.Appointment;
import com.clinic.main.entity.Doctor;

import java.util.List;

public class DoctorMapper {

    public static Doctor mapToDoctor(DoctorDto doctorDto, List<Appointment> appointments) {
        Doctor doctor = new Doctor();
        doctor.setId(doctorDto.getId());
        doctor.setName(doctorDto.getName());
        doctor.setExperience(doctorDto.getExperience());
        doctor.setSpecialization(doctorDto.getSpecialization());
        doctor.setAppointments(appointments);
        return doctor;
    }

    public static DoctorDto mapToDto(Doctor doctor) {
        DoctorDto doctorDto = new DoctorDto();
        doctorDto.setId(doctor.getId());
        doctorDto.setName(doctor.getName());
        doctorDto.setExperience(doctor.getExperience());
        doctorDto.setSpecialization(doctor.getSpecialization());
        return doctorDto;
    }

    public static List<DoctorDto> mapToDto(List<Doctor> doctors) {
        return doctors.stream()
                .map(DoctorMapper::mapToDto)
                .toList();
    }
}
