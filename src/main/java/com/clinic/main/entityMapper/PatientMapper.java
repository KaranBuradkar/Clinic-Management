package com.clinic.main.entityMapper;

import com.clinic.main.dtos.PatientDto;
import com.clinic.main.entity.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    // Map PatientDto To PatientEntity
    Patient toEntity(PatientDto patientDto);

    // Map PatientEntity To PatientDto
    PatientDto toDto(Patient patient);
}
