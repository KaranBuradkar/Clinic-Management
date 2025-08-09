package com.clinic.main.service.impl;

import com.clinic.main.dtos.PatientDto;
import com.clinic.main.entity.Appointment;
import com.clinic.main.entity.Patient;
import com.clinic.main.entityMapper.PatientMapper;
import com.clinic.main.repository.PatientRepository;
import com.clinic.main.service.PatientAppointmentFacade;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PatientAppointmentFacadeImpl implements PatientAppointmentFacade {

    private final PatientRepository patientRepository;
    private final AppointmentServiceImpl appointmentServiceImpl;

    public PatientAppointmentFacadeImpl(PatientRepository patientRepository, AppointmentServiceImpl appointmentServiceImpl) {
        this.patientRepository = patientRepository;
        this.appointmentServiceImpl = appointmentServiceImpl;
    }

    public PatientDto addPatient(PatientDto patientDto) {
        if (patientDto.getId() != null) throw  new IllegalArgumentException("Patient Id must null!!");
        Patient patient = dtoToPatient(patientDto);
        return PatientMapper.mapToDto(addPatient(patient));
    }
    @Transactional
    Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public PatientDto updatePatient(PatientDto patientDto) {
        Patient patient = dtoToPatient(patientDto);
        return PatientMapper.mapToDto(updatePatient(patient));
    }
    @Modifying
    @Transactional
    Patient updatePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public String deletePatient(PatientDto patientDto) {
        Patient patient = dtoToPatient(patientDto);
        return deletePatient(patient);
    }
    @Modifying
    @Transactional
    String deletePatient(Patient patient) {
        if (patientRepository.existsById(patient.getId())) patientRepository.delete(patient);
        return "Patient Successfully Removed From Data!";
    }

    @Transactional
    private Patient dtoToPatient(PatientDto patientDto) {
        List<Appointment> appointments = new ArrayList<>();
        if(patientDto.getId() != null)
            appointments = appointmentServiceImpl.getAppointmentByPatientIdAsEntity(patientDto.getId());
        return PatientMapper.mapToPatient(patientDto, appointments);
    }
}
