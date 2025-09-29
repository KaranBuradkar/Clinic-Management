package com.clinic.main.service;

import com.clinic.main.dtos.DoctorDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DoctorService {

    // View Doctor By ID
    DoctorDto getDoctorById(Long doctorId);
    // View Doctor Page
    List<DoctorDto> getAPageOfDoctorDto(Integer pageNumber, Integer pageSize, String sortBy, String dir);
    // View Doctors By Specialization
    List<DoctorDto> getDoctorsBySpecialization(String specialization);
    // View Sorted Doctor By name
    List<DoctorDto> getDoctorsSortBy(String field);

    // MODIFICATIONS
    // ADD Doctor
    DoctorDto addDoctor(DoctorDto doctorDto);
    // Update Doctor
    DoctorDto updateDoctor(Long doctorId, DoctorDto doctorDto);
    // Delete Doctor By ID
    String deleteById(Long doctorId);
}
