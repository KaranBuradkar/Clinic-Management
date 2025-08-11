package com.clinic.main.entityMapper;

import com.clinic.main.dtos.AppointmentBasicDto;
import com.clinic.main.dtos.AppointmentDto;
import com.clinic.main.dtos.AppointmentRequestDto;
import com.clinic.main.entity.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

//    @Mapping(target = "doctor", ignore = true)
//    @Mapping(target = "patient", ignore = true)
    Appointment toEntity(AppointmentDto appointmentDto);

//    @Mapping(target = "doctor", ignore = true)
//    @Mapping(target = "patient", ignore = true)
    AppointmentDto toDto(Appointment appointment);

//    @Mapping(target = "doctor", ignore = true)
//    @Mapping(target = "patient", ignore = true)
    AppointmentDto toDto(AppointmentRequestDto appointmentRequestDto);
//
//    @Mapping(target = "doctor", ignore = true)
//    @Mapping(target = "patient", ignore = true)
//    AppointmentBasicDto toBasicDto(Appointment appointment);
}
