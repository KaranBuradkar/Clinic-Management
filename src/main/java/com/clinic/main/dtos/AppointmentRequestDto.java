package com.clinic.main.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AppointmentRequestDto {

    private Long id;
    private String reason;
    private LocalDate date;
    private LocalTime time;
    private LocalDateTime createdAt;
    private Long doctorId;
    private Long patientId;

    public AppointmentRequestDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    @Override
    public String toString() {
        return "AppointmentRequestDto{" +
                "id=" + id +
                ", reason='" + reason + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", createdAt=" + createdAt +
                ", doctorId=" + doctorId +
                ", patientId=" + patientId +
                '}';
    }
}
