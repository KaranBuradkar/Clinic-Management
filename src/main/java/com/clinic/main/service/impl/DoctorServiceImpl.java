package com.clinic.main.service.impl;

import com.clinic.main.customeExceptions.DoctorNotFoundException;
import com.clinic.main.dtos.DoctorDto;
import com.clinic.main.entity.Doctor;
import com.clinic.main.entityMapper.DoctorMapper;
import com.clinic.main.repository.DoctorRepository;
import com.clinic.main.service.DoctorService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final DoctorMapper doctorMapper;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository, DoctorMapper doctorMapper) {
        this.doctorRepository = doctorRepository;
        this.doctorMapper = doctorMapper;
    }

    public List<DoctorDto> getAllDoctorDtos() {
        List<Doctor> doctors = getAllDoctorsAsEntities();
        return doctors.stream()
                .map(doctorMapper::toDto)
                .toList();
    }
    List<Doctor> getAllDoctorsAsEntities() {
        return doctorRepository.findAll(Sort.by("id").ascending());
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
        List<Doctor> doctors = getAPageOfDoctorAsEntities(pageNumber, pageSize, sortBy);
        return doctors.stream()
                .map(doctorMapper::toDto)
                .toList();
    }
    List<Doctor> getAPageOfDoctorAsEntities(Integer pageNumber, Integer pageSize, String sortBy) {
        Page<Doctor> doctors = doctorRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortBy)));
        return doctors.toList();
    }

    @Override
    public List<DoctorDto> getDoctorDtosBySpecialization(String specialization) {
        List<Doctor> doctors = getDoctorBySpecializationAsEntities(specialization);
        return doctors.stream()
                .map(doctorMapper::toDto)
                .toList();
    }
    List<Doctor> getDoctorBySpecializationAsEntities(String specialization) {
        return doctorRepository.findBySpecialization(specialization);
    }

    @Override
    public List<DoctorDto> getDoctorDtosSortedBy(String name) {
        List<Doctor> doctors = getSortDoctorBy(name);
        return doctors.stream()
                .map(doctorMapper::toDto)
                .toList();
    }

    List<Doctor> getSortDoctorBy(String name) {
        return doctorRepository.findAll(Sort.by(Sort.Direction.ASC, name));
    }

    @Modifying
    @Transactional
    public String deleteById(Long doctorId) {
        if (!doctorRepository.existsById(doctorId))
            throw new IllegalArgumentException("Doctor Doesn't exist For Id: "+doctorId);
        doctorRepository.deleteById(doctorId);
        return "Doctor removed Successfully From DataBase!";
    }

    @Override
    public DoctorDto addDoctor(DoctorDto doctorDto) {
        if (doctorDto.getId() != null)
            throw new IllegalArgumentException("Doctor Id Must be null!");
        Doctor doctor = doctorMapper.toEntity(doctorDto);
        return doctorMapper.toDto(addDoctor(doctor));
    }
    @Transactional
    Doctor addDoctor(Doctor doctor) {
        System.out.println(doctor);
        return doctorRepository.save(doctor);
    }

    @Override
    public DoctorDto updateDoctor(Long doctorId, DoctorDto doctorDto) {
        if (doctorRepository.existsById(doctorId))
            throw new IllegalArgumentException();
        Doctor doctor = doctorMapper.toEntity(doctorDto);
        doctor.setId(doctorId);
        return doctorMapper.toDto(updateDoctor(doctor));
    }
    @Modifying
    @Transactional
    Doctor updateDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }
}
