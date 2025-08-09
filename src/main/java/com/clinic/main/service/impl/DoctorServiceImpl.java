package com.clinic.main.service.impl;

import com.clinic.main.customeExceptions.DoctorNotFoundException;
import com.clinic.main.dtos.DoctorDto;
import com.clinic.main.entity.Doctor;
import com.clinic.main.entityMapper.DoctorMapper;
import com.clinic.main.repository.DoctorRepository;
import com.clinic.main.service.DoctorService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<DoctorDto> getAllDoctorDtos() {
        return DoctorMapper.mapToDto(getAllDoctorsAsEntities());
    }
    List<Doctor> getAllDoctorsAsEntities() {
        return doctorRepository.getAllDoctor("id")
                .orElseThrow(() -> new DoctorNotFoundException("Doctor Not Found Query."));
    }

    @Override
    public DoctorDto getDoctorDtoById(Long doctorId) {
        return doctorRepository.findDoctorDtoById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor Not Found with Id: "+doctorId));
    }
    @Transactional
    Doctor getDoctorByIdAsEntity(Long doctorId) {
        return doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor Not Found with Id: "+doctorId));
    }

    @Override
    public List<DoctorDto> getAPageOfDoctorDto(Integer pageNumber, Integer pageSize, String sortBy) {
        return DoctorMapper.mapToDto(getAPageOfDoctorAsEntities(pageNumber, pageSize, sortBy));
    }
    List<Doctor> getAPageOfDoctorAsEntities(Integer pageNumber, Integer pageSize, String sortBy) {
        Page<Doctor> doctors = doctorRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortBy)));
        return doctors.toList();
    }

    @Override
    public List<DoctorDto> getDoctorDtosBySpecialization(String specialization) {
        return DoctorMapper.mapToDto(getDoctorBySpecializationAsEntities(specialization));
    }
    List<Doctor> getDoctorBySpecializationAsEntities(String specialization) {
        return doctorRepository.findBySpecialization(specialization);
    }

    @Override
    public List<DoctorDto> getDoctorDtosSortedBy(String name) {
        return DoctorMapper.mapToDto(getSortDoctorBy(name));
    }

    List<Doctor> getSortDoctorBy(String name) {
        return doctorRepository.findAll(Sort.by(Sort.Direction.ASC, name));
    }

    @Modifying
    @Transactional
    public String deleteById(Long doctorId) {
        doctorRepository.deleteById(doctorId);
        return "Doctor removed Successfully From DataBase!";
    }
}
