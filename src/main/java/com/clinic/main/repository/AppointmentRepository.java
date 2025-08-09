package com.clinic.main.repository;

import com.clinic.main.dtos.AppointmentPerDoctorDTO;
import com.clinic.main.dtos.WeeklyAppointmentCountDTO;
import com.clinic.main.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Schedule appointment - default - .save(appointment);
    // View all appointments - default - .findAll();
    // View appointment by ID - default - .findById(appointmentId);
    // View appointments by date  - default - .findByDate(date);
    List<Appointment> findByDate(LocalDate date);
    // View upcoming appointments - default - .findAll(Sort.by("date").descending());
    // Cancel appointment - default - .delete(appointment);
    // Update appointment - default - .save(appointment);
    // Sort appointments by date or time - default - .findAll(Sort.by("date", "time").ascending);

    // View appointments by doctor ID
//    @Query(value = "SELECT a.* FROM Appointments a " +
//            "WHERE a.doctor.id=?1", nativeQuery = true) // Good For Practice Not A Good Practice
    List<Appointment> findByDoctor_Id(Long doctorId);

    // View appointments by patient ID
    List<Appointment> findByPatient_Id(Long  patientId);

    // Count appointments per doctor - CORRECTED JPQL QUERY
    @Query("SELECT new com.clinic.main.dtos.AppointmentPerDoctorDTO(d.id, d.name, d.specialization, d.experience, COUNT(a.id)) " +
            "FROM Appointment a " + // <-- Use entity class name 'Appointment'
            "LEFT JOIN a.doctor d " +
            "GROUP BY d.id, d.name, d.specialization, d.experience")
    List<AppointmentPerDoctorDTO> countAppointmentsPerDoctor();

    // Count daily or weekly appointments
//    @Query("SELECT new com.clinic.main.dtos.WeeklyAppointmentCountDTO(FUNCTION('DATE_FORMAT', a.date, '%Y-%m-%d'), count(id)) " +
//            "FROM Appointment a GROUP BY FUNCTION('DATE_FORMAT', a.date, '%Y-%m-%d')")
//    List<WeeklyAppointmentCountDTO> countAppointmentPerDay();

//    @Query(value = "SELECT new com.clinic.main.dtos.WeeklyAppointmentCountDTO(" +
//            "FUNCTION('WEEK', date), count(id)) FROM Appointment GROUP BY FUNCTION('WEEK', date)")
//    List<WeeklyAppointmentCountDTO> countAppointmentsPerWeek();

    // Count appointments for the last 7 days
//    @Query("SELECT new com.clinic.main.dtos.WeeklyAppointmentCountDTO(" +
//            "FUNCTION('DAYNAME', a.date), " +
//            "COUNT(a.id)) " +
//            "FROM Appointment a " +
//            "WHERE a.date >= CURRENT_DATE() - 7 " + // Use CURRENT_DATE() for portability
//            "GROUP BY FUNCTION('DAYNAME', a.date) " +
//            "ORDER BY a.date") // Order by day
//    List<WeeklyAppointmentCountDTO> countAppointmentsLast7Days();

    // Pagination
    Page<Appointment> findAll(Pageable pageable);

//    @Query(value = "SELECT * FROM Appointments WHERE date >= CURDATE() ORDER BY date ASC", nativeQuery = true)
//    List<Appointment> upcomingAppointments();
}
