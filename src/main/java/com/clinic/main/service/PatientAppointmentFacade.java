package com.clinic.main.service;

import com.clinic.main.dtos.PatientDto;
import org.springframework.stereotype.Service;

@Service
public interface PatientAppointmentFacade {

    // ADD Patient
    PatientDto addPatient(PatientDto patientDto);
    // Patient Updates
    PatientDto updatePatient(PatientDto patientDto);
    // Delete Patient
    String deletePatient(PatientDto patientDto);

}
