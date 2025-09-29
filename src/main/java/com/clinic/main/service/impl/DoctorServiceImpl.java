package com.clinic.main.service.impl;

import com.clinic.main.customeExceptions.DoctorNotFoundException;
import com.clinic.main.dtos.DoctorDto;
import com.clinic.main.entity.Doctor;
import com.clinic.main.entityMapper.DoctorMapper;
import com.clinic.main.repository.DoctorRepository;
import com.clinic.main.service.DoctorService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(DoctorServiceImpl.class);
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository, DoctorMapper doctorMapper) {
        this.doctorRepository = doctorRepository;
        this.doctorMapper = doctorMapper;
    }

    @Override
    public DoctorDto getDoctorById(Long doctorId) {
        return doctorRepository.findDoctorDtoById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor Not Found with Id: "+doctorId));
    }

    @Override
    public List<DoctorDto> getAPageOfDoctorDto(Integer pageNumber, Integer pageSize, String sortBy, String dir) {
        Sort.Direction direction = "asc".equalsIgnoreCase(dir) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Page<Doctor> doctorsPage = doctorRepository.findAll(
                PageRequest.of(
                        pageNumber,
                        pageSize,
                        direction,
                        sortBy
                )
        );
        List<Doctor> doctors =  doctorsPage.toList();
        return doctors.stream()
                .map(doctorMapper::toDto)
                .toList();
    }

    @Override
    public List<DoctorDto> getDoctorsBySpecialization(String specialization) {
        List<Doctor> doctors = doctorRepository.findBySpecialization(specialization);
        return doctors.stream()
                .map(doctorMapper::toDto)
                .toList();
    }

    @Override
    public List<DoctorDto> getDoctorsSortBy(String name) {
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
    @Transactional
    @Modifying
    public DoctorDto addDoctor(DoctorDto doctorDto) {
        if (doctorDto.getId() != null) {
            log.error("doesn't required doctorId-{}, Database automatically generate it! ",doctorDto.getId());
            throw new IllegalArgumentException("Doctor Id Must be null!");
        }

        Doctor doctor = doctorMapper.toEntity(doctorDto);

        Doctor addedDoctor = doctorRepository.save(doctor);

        log.info("New Doctor entry created Id-{}", addedDoctor.getId());

        return doctorMapper.toDto(addedDoctor);
    }

    @Transactional
    @Modifying
    @Override
    public DoctorDto updateDoctor(Long doctorId, DoctorDto doctorDto) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> {
            log.error("invalid doctorId-{} provided", doctorId);
            return new DoctorNotFoundException("invalid doctorId-" + doctorId + " provided");
        });

        Doctor newDoctor = doctorMapper.toEntity(doctorDto);

        doctor.getAppointments().addAll(newDoctor.getAppointments());
        Doctor updatedDoctor = doctorRepository.save(doctor);
        log.info("Doctor detail updated for Id-{}", updatedDoctor.getId());

        return doctorMapper.toDto(updatedDoctor);
    }
}
