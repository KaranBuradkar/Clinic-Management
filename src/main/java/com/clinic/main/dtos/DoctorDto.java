package com.clinic.main.dtos;

import com.clinic.main.entity.Appointment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

public class DoctorDto {

    private Long id;
    private String name;
    private String specialization;
    private Integer experience;
    private Long appointmentHandled;
    private List<AppointmentDto> appointmentDtos = new ArrayList<>();

    public DoctorDto() {
    }

    public DoctorDto(Long id, String name, String specialization, Integer experience, List<AppointmentDto> appointmentDtos) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.experience = experience;
        this.appointmentDtos = appointmentDtos;
    }

    public DoctorDto(Long id, String name, String specialization, Integer experience, Long appointmentHandled) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.appointmentHandled = appointmentHandled;
        this.experience = experience;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public List<AppointmentDto> getAppointmentDtos() {
        return appointmentDtos;
    }

    public void setAppointmentDtos(List<AppointmentDto> appointmentDtos) {
        this.appointmentDtos = appointmentDtos;
    }

    public Long getAppointmentHandled() {
        return appointmentHandled;
    }

    public void setAppointmentHandled(Long appointmentHandled) {
        this.appointmentHandled = appointmentHandled;
    }

    @Override
    public String toString() {
        return "DoctorDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", specialization='" + specialization + '\'' +
                ", experience=" + experience +
                '}';
    }
}
