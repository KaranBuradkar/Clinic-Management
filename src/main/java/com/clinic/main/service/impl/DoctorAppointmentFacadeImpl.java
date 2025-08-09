package com.clinic.main.service.impl;

import com.clinic.main.dtos.DoctorDto;
import com.clinic.main.entity.Appointment;
import com.clinic.main.entity.Doctor;
import com.clinic.main.entityMapper.DoctorMapper;
import com.clinic.main.repository.DoctorRepository;
import com.clinic.main.service.DoctorAppointmentFacade;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorAppointmentFacadeImpl implements DoctorAppointmentFacade {

    private final DoctorRepository doctorRepository;
    private final AppointmentServiceImpl appointmentServiceImpl;

    public DoctorAppointmentFacadeImpl(DoctorRepository doctorRepository, AppointmentServiceImpl appointmentServiceImpl) {
        this.doctorRepository = doctorRepository;
        this.appointmentServiceImpl = appointmentServiceImpl;
    }

    @Override
    public DoctorDto addDoctor(DoctorDto doctorDto) {
        if (doctorDto.getId() != null) throw new IllegalArgumentException("Doctor Id Must be null!");
        Doctor doctor = dtoToDoctor(doctorDto);
        return DoctorMapper.mapToDto(addDoctor(doctor));
    }
    @Transactional
    Doctor addDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public DoctorDto updateDoctor(DoctorDto doctorDto, Long doctorId) {
        Doctor doctor = dtoToDoctor(doctorDto);
        doctor.setId(doctorId);
        return DoctorMapper.mapToDto(updateDoctor(doctor));
    }
    @Modifying
    @Transactional
    Doctor updateDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public String deleteDoctor(DoctorDto doctorDto) {
        Doctor doctor = dtoToDoctor(doctorDto);
        return deleteDoctor(doctor);
    }
    @Modifying
    @Transactional
    String deleteDoctor(Doctor doctor) {
        doctorRepository.delete(doctor);
        return "Doctor removed Successfully From DataBase!";
    }

    @Transactional
    private Doctor dtoToDoctor(DoctorDto doctorDto) {
        List<Appointment> appointments = new ArrayList<>();
        if (doctorDto.getId() != null) appointments = appointmentServiceImpl.getAppointmentByDoctorIdAsEntity(doctorDto.getId());
        return DoctorMapper.mapToDoctor(doctorDto, appointments);
    }
}
