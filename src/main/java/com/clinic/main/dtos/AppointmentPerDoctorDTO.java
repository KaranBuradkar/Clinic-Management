package com.clinic.main.dtos;

public class AppointmentPerDoctorDTO {
    private Long id;
    private String name;
    private String specialization;
    private Integer experience;
    private Long appointmentCount;

    public AppointmentPerDoctorDTO(Long id, String name, String specialization, Integer experience, Long appointmentCount) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.experience = experience;
        this.appointmentCount = appointmentCount;
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

    public Long getAppointmentCount() {
        return appointmentCount;
    }

    public void setAppointmentCount(Long appointmentCount) {
        this.appointmentCount = appointmentCount;
    }

    @Override
    public String toString() {
        return "AppointmentPerDoctorDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", specialization='" + specialization + '\'' +
                ", experience=" + experience +
                ", appointmentCount=" + appointmentCount +
                '}';
    }
}
