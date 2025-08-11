package com.clinic.main.repository;

import com.clinic.main.dtos.DoctorDto;
import com.clinic.main.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    List<Doctor> findBySpecialization(String specialization);   // Derived query by specialization
    Page<Doctor> findAll(Pageable pageable);    // Pagination support (inherited from JpaRepository)

    // Find doctor entity by ID using native SQL
    @Query(value = "SELECT * FROM Doctors WHERE id = ?1", nativeQuery = true)
    Optional<Doctor> findById(Long id); // ⚠️ This overrides default method – not recommended

    // Recommended DTO projection using JPQL
    @Query("SELECT new com.clinic.main.dtos.DoctorDto(d.id, d.name, d.specialization, d.experience, COUNT(a)) " +
            "FROM Doctor d LEFT JOIN d.appointments a " +
            "WHERE d.id = ?1 " +
            "GROUP BY d.id, d.name, d.specialization, d.experience")
    Optional<DoctorDto> findDoctorDtoById(Long id);

    // Custom join query to fetch all doctors with appointments and patients
    @Query("SELECT DISTINCT d FROM Doctor d LEFT JOIN FETCH d.appointments a LEFT JOIN FETCH a.patient")
    List<Doctor> getAllDoctorsWithAppointments();
}
