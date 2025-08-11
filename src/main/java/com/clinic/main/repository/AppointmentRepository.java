package com.clinic.main.repository;

import com.clinic.main.dtos.AppointmentPerDoctorDTO;
import com.clinic.main.dtos.WeeklyAppointmentCountDTO;
import com.clinic.main.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Schedule appointment - default - .save(appointment);
    // View all appointments - default - .findAll();
    // View appointment by ID - default - .findById(appointmentId);
    // View appointments by date  - default - .findByDate(date);
    // View upcoming appointments - default - .findAll(Sort.by("date").descending());
    // Cancel appointment - default - .delete(appointment);
    // Update appointment - default - .save(appointment);
    // Sort appointments by date or time - default - .findAll(Sort.by("date", "time").ascending);
    // View appointments by doctor ID --> .findByDoctor_Id(Long doctorId);
    // View appointments by patient ID --> .findByPatient_Id(Long patientId);

    List<Appointment> findByDate(LocalDate date);
    List<Appointment> findByDoctor_Id(Long doctorId);
    List<Appointment> findByPatient_Id(Long  patientId);
    Page<Appointment> findAll(Pageable pageable);   // Pagination

    // Count appointments per doctor - CORRECTED JPQL QUERY
    @Query("SELECT new com.clinic.main.dtos.AppointmentPerDoctorDTO(d.id, d.name, d.specialization, d.experience, COUNT(a.id)) " +
            "FROM Appointment a " + // <-- Use entity class name 'Appointment'
            "LEFT JOIN a.doctor d " +
            "GROUP BY d.id, d.name, d.specialization, d.experience")
    List<AppointmentPerDoctorDTO> countAppointmentsPerDoctor();

    @Query("SELECT a FROM Appointment a " +
            "WHERE a.date > CURRENT_DATE " +
            "OR (a.date = CURRENT_DATE AND a.time > CURRENT_TIME) " +
            "ORDER BY a.date ASC, a.time ASC")
    List<Appointment> findUpcomingAppointments();
}
