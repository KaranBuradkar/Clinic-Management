package com.clinic.main.dtos;

public class WeeklyAppointmentCountDTO {

    private String period;
    private Long appointmentCount;

    public WeeklyAppointmentCountDTO(String period, Long appointmentCount) {
        this.period = period;
        this.appointmentCount = appointmentCount;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Long getAppointmentCount() {
        return appointmentCount;
    }

    public void setAppointmentCount(Long appointmentCount) {
        this.appointmentCount = appointmentCount;
    }

    @Override
    public String toString() {
        return "WeeklyAppointmentCountDTO{" +
                "date='" + period + '\'' +
                ", appointmentCount=" + appointmentCount +
                '}';
    }
}
