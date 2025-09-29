package com.clinic.main.service.impl;

import com.clinic.main.customeExceptions.PatientNotFoundException;
import com.clinic.main.dtos.PatientDto;
import com.clinic.main.entity.Patient;
import com.clinic.main.entityMapper.PatientMapper;
import com.clinic.main.repository.PatientRepository;
import com.clinic.main.service.PatientService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    private static final Logger log = LoggerFactory.getLogger(PatientServiceImpl.class);
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    @Override
    public PatientDto getPatientDtoById(Long patientId) {
        return patientRepository.findPatientDtoById(patientId)
                .orElseThrow(() -> {
                    log.error("Invalid patientId-{} provided", patientId);
                    return new PatientNotFoundException("Patient Not Found With ID: "+patientId);
                });
    }

    @Override
    public List<PatientDto> getAllPatientDto() {
        List<Patient> patients = patientRepository.getAllPatient();

        return patients.stream()
                .map(patientMapper::toDto)
                .toList();
    }

    @Override
    public List<PatientDto> getPatientDtoBetweenAge(Integer lowerAge, Integer upperAge) {
        List<Patient> patients = patientRepository.findPatientBetweenAge(lowerAge, upperAge);
        return patients.stream()
                .map(patientMapper::toDto)
                .toList();
    }

    @Override
    public List<PatientDto> getPatientDtoPage(Integer pageNumber, Integer pageSize, String sortBy, String dir) {
        Sort.Direction direction = "asc".equalsIgnoreCase(dir) ? Sort.Direction.ASC : Sort.Direction.DESC;
        List<Patient> patients = patientRepository.findAll(
                        PageRequest.of(pageNumber,
                                pageSize,
                                direction,
                                sortBy
                        )
                ).toList();

        return patients.stream()
                .map(patientMapper::toDto)
                .toList();
    }

    @Override
    public String deletePatientById(Long patientId) {
        if (!patientRepository.existsById(patientId)) {
            log.error("Invalid patientId-{} provided for deletion", patientId);
            throw new PatientNotFoundException("Patient Detail Doesn't Exist!");
        }

        patientRepository.deleteById(patientId);
        log.error("Patient detail deleted From db for Id-{}", patientId);

        return "Patient Successfully Removed From Data!";
    }

    @Modifying
    @Transactional
    @Override
    public PatientDto updateField(Long patientId, Map<String, Object> updates) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient Not Found with ID: "+patientId));

        updates.forEach((field, value) -> {
            switch (field) {
                case "name": patient.setName((String) value); break;
                case "email": patient.setEmail((String) value); break;
                case "gender": patient.setGender((String) value); break;
                case "age": patient.setAge((Integer) value); break;
                case "phoneNo": patient.setPhoneNo((String) value); break;
                default: throw new IllegalArgumentException("Field is not supported");
            }
        });

        Patient savedPatient = patientRepository.save(patient);
        return patientMapper.toDto(savedPatient);
    }

    @Override
    public PatientDto addPatient(PatientDto patientDto) {
        if (patientDto.getId() != null) {
            log.error("doesn't required patientId-{}, Database automatically generate it! ",patientDto.getId());
            throw new IllegalArgumentException("Patient Id must null!!");
        }

        Patient patient = patientMapper.toEntity(patientDto);

        Patient addedPatient = patientRepository.save(patient);

        log.info("New Patient Entry created with Id-{}", addedPatient.getId());
        return patientMapper.toDto(addedPatient);
    }

    @Override
    public PatientDto updatePatient(Long patientId, PatientDto patientDto) {
        if (!patientRepository.existsById(patientId)) {
            log.error("invalid patientId-{} provided", patientId);
            throw new IllegalArgumentException("Patient doesn't exist for Id: "+patientId);
        }

        Patient patient = patientMapper.toEntity(patientDto);
        patient.setId(patientId);

        Patient updatedPatient = patientRepository.save(patient);
        log.info("Patient detail has been updated for Id-{}", updatedPatient.getId());

        return patientMapper.toDto(updatedPatient);
    }
}
