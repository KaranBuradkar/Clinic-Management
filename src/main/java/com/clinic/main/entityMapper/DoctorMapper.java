package com.clinic.main.entityMapper;

import com.clinic.main.dtos.DoctorDto;
import com.clinic.main.entity.Doctor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    Doctor toEntity(DoctorDto doctorDto);
    DoctorDto toDto(Doctor doctor);

}
