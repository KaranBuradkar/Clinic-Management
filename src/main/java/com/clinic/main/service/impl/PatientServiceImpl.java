package com.clinic.main.service.impl;

import com.clinic.main.customeExceptions.PatientNotFoundException;
import com.clinic.main.dtos.PatientDto;
import com.clinic.main.entity.Patient;
import com.clinic.main.entityMapper.PatientMapper;
import com.clinic.main.repository.PatientRepository;
import com.clinic.main.service.PatientService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public PatientDto getPatientDtoById(Long patientId) {
//        return PatientMapper.mapToDto(getPatientByIdAsEntity(patientId));
        return patientRepository.findPatientDtoById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient Not Found With ID: "+patientId));
    }
    @Transactional
    Patient getPatientByIdAsEntity(Long patientId) {
        return patientRepository.findPatientById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient Not Found With ID: "+patientId));
    }

    @Override
    public List<PatientDto> getAllPatientDto() {
        return PatientMapper.mapToDto(getAllPatientsAsEntity());
    }
    List<Patient> getAllPatientsAsEntity() {
//        List<Patient> patients = patientRepository.findAll(Sort.by("id").ascending());
//        for (Patient patient : patients) {
//            // Initialize appointments for each patient in the page.
//            Hibernate.initialize(patient.getAppointments());
//        }
        return patientRepository.getAllPatient();
    }

    @Override
    public List<PatientDto> getPatientDtoBetweenAge(Integer lowerAge, Integer upperAge) {
        return PatientMapper.mapToDto(getPatientBetweenAgeAsEntity(lowerAge, upperAge));
    }
    List<Patient> getPatientBetweenAgeAsEntity(Integer lowerAge, Integer upperAge) {
        return patientRepository.findPatientBetweenAge(lowerAge, upperAge)
                .orElseThrow(() -> new PatientNotFoundException("Patient Not Found With Id: Age"));
    }

    @Override
    public List<PatientDto> getPatientDtoPage(Integer pageNumber, Integer pageSize, String sortBy) {
        return PatientMapper.mapToDto(getPatientDtoPageAsEntities(pageNumber, pageSize, sortBy));
    }
    List<Patient> getPatientDtoPageAsEntities(Integer pageNumber, Integer pageSize, String sortBy) {
        Page<Patient> pageOfPatients = patientRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortBy)));
        return pageOfPatients.toList();
    }

    @Override
    public String deletePatientById(Long patientId) {
        if (!patientRepository.existsById(patientId)) return "Patient Detail Doesn't Exist!";
        patientRepository.deleteById(patientId);
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
        return PatientMapper.mapToDto(savedPatient);
    }
}
