package com.clinic.main.service;

import com.clinic.main.dtos.PatientDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface PatientService {

    // VIEW patient by ID
    PatientDto getPatientDtoById(Long patientId);
    // View All Patients
    List<PatientDto> getAllPatientDto();
    // View Patients Who is Age between lowerAge and upperAge
    List<PatientDto> getPatientDtoBetweenAge(Integer lowerAge, Integer upperAge);
    // Page Of Patient
    List<PatientDto> getPatientDtoPage(Integer pageNumber, Integer pageSize, String sortBy, String dir);

    // MODIFICATIONS
    // ADD Patient
    PatientDto addPatient(PatientDto patientDto);
    // Update fields by ID
    PatientDto updateField(Long id, Map<String, Object> updates);
    // Patient Updates
    PatientDto updatePatient(Long patientId, PatientDto patientDto);
    // Delete Patient By ID
    String deletePatientById(Long patientId);
}
