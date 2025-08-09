package com.clinic.main.service;

import com.clinic.main.dtos.DoctorDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DoctorService {

    // View All Doctors
    List<DoctorDto> getAllDoctorDtos();
    // View Doctor By ID
    DoctorDto getDoctorDtoById(Long doctorId);
    // View Doctor Page
    List<DoctorDto> getAPageOfDoctorDto(Integer pageNumber, Integer pageSize, String sortBy);
    // View Doctors By Specialization
    List<DoctorDto> getDoctorDtosBySpecialization(String specialization);
    // View Sorted Doctor By name
    List<DoctorDto> getDoctorDtosSortedBy(String field);
    // Delete Doctor By ID
    String deleteById(Long doctorId);
}
