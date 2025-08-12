package com.clinic.main.repository;

import com.clinic.main.dtos.PatientDto;
import com.clinic.main.entity.Appointment;
import com.clinic.main.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Add patient - default -> .save(patient);
    // View all patients - default -> .findAll();
    // View patient by ID - default -> .findById(id);
    // Update patient - .save(patient);
    // Delete patient - default -> .delete(patient);

    // @EntityGraph(attributePaths = {"appointments"})
    @Query(value = "SELECT * FROM Patients WHERE id = ?1", nativeQuery = true)
    Optional<Patient> findPatientById(Long aLong);

    @Query(value = "SELECT new com.clinic.main.dtos.PatientDto(id, name, birthDate, gender, phoneNo, email, age) " +
            "FROM Patient WHERE id = ?1")
    Optional<PatientDto> findPatientDtoById(Long aLong);

    // Filter patients by age range
    @Query(value = "SELECT * FROM Patients WHERE age > ?1 and age < ?2", nativeQuery = true)
    List<Patient> findPatientBetweenAge(Integer lowerAge, Integer higherAge);

    // Pagination
    Page<Patient> findAll(Pageable pageable);

    @Query("SELECT DISTINCT p FROM Patient p LEFT JOIN FETCH p.appointments a LEFT JOIN FETCH a.doctor d")
    List<Patient> getAllPatient();

}
