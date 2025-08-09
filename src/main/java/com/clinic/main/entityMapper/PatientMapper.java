package com.clinic.main.entityMapper;

import com.clinic.main.dtos.PatientDto;
import com.clinic.main.entity.Appointment;
import com.clinic.main.entity.Patient;

import java.util.ArrayList;
import java.util.List;

public class PatientMapper {

    public static Patient mapToPatient(PatientDto patientDTO, List<Appointment> appointments) {
        Patient patient = new Patient();
        patient.setId(patientDTO.getId());
        patient.setName(patientDTO.getName());
        patient.setEmail(patientDTO.getEmail());
        patient.setPhoneNo(patientDTO.getPhoneNo());
        patient.setGender(patientDTO.getGender());
        patient.setBirthDate(patientDTO.getBirthDate());
        patient.setAppointments(appointments);
        return patient;
    }

    public static List<PatientDto> mapToDto(List<Patient> patients) {
        return patients.stream().map(PatientMapper::mapToDto).toList();
    }

    public static PatientDto mapToDto(Patient patient) {
        PatientDto dto = new PatientDto();
        dto.setId(patient.getId());
        dto.setName(patient.getName());
        dto.setEmail(patient.getEmail());
        dto.setPhoneNo(patient.getPhoneNo());
        dto.setBirthDate(patient.getBirthDate());
        dto.setGender(patient.getGender());

        return dto;
    }
}
