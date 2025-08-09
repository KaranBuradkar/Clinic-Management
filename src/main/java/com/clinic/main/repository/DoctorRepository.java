package com.clinic.main.repository;

import com.clinic.main.dtos.DoctorDto;
import com.clinic.main.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

//    @Modifying
//    @Query(value = "update Doctors p set p.name=?1, p.specialization=?2, p.experience=?3 where p.id=?4", nativeQuery = true)
//    void updateDoctor(String name, String specialization, Integer exp, Long doctorId);
//
//    // Add doctor - default-> .save(doctor);
//    // Below is not recommended and jpql is not support insert query
//    @Modifying
//    @Transactional
//    @Query(value = "Insert into Doctors(name, specialization, experience) values(?, ?, ?)", nativeQuery = true)
//    void addDoctor(String name, String specialization, Integer experience);
//
//    // View all doctors - default-> .findAll(); // Best Practice
//    // But If you Want Pagination then
//    @Query(value = "SELECT * FROM Doctors", nativeQuery = true)
//    Page<Doctor> getOnePageOf(Pageable page);
//
//    // View doctor by ID - default-> .findById();
//    @Query(value = "SELECT * FROM Doctors d WHERE d.id = ?1", nativeQuery = true) // Native or SQL query
//    Doctor getDoctorById(Long id);
//
//    // Update doctor - default -> .save();
//    @Modifying
//    @Transactional
//    @Query(value = "UPDATE Doctor d SET d.name=?1, d.specialization=?2, d.experience=?3 WHERE d.id=?2")
//    void updateDoctor(Doctor doctor, Long doctorId);
//
//    // Delete doctor - default -> .delete(doctor); or .deleteById(doctorId);
//    @Modifying
//    @Transactional
//    @Query(value = "DELETE FROM Doctors WHERE id=?1", nativeQuery = true)
//    void deleteDoctorById(Long doctorId);
//
//    // Search doctor by specialization
//    List<Doctor> findBySpecialization(String specialization);
//    @Query(value = "SELECT d FROM Doctor d WHERE d.specialization=?1") // This good way but modify when table name change.
//    List<Doctor> getDoctorBySpecialization(String specialization);
//
//    // Sort doctors by name or experience
//    @Query("SELECT d FROM Doctor d ORDER BY ?1 ASC")
//    List<Doctor> findSortDoctorsBy(String sortBy);

    // ======= BEST PRACTICES ONLY =============//
    @Override
//    @EntityGraph(attributePaths = {"appointments"})
    @Query(value = "SELECT * FROM Doctors WHERE id = ?")
    Optional<Doctor> findById(Long aLong);

    @Query(value = "SELECT new com.clinic.main.dtos.DoctorDto(id, name, specialization, experience) FROM Doctor WHERE id = ?1")
    Optional<DoctorDto> findDoctorDtoById(Long aLong);


    // Find doctor by specialization (derived query)
    List<Doctor> findBySpecialization(String specialization);

    // Pagination support
    Page<Doctor> findAll(Pageable pageable);

    // Sorting support
    List<Doctor> findAll(Sort sort);

//    @Query("SELECT p FROM Patient p LEFT JOIN FETCH p.appointments a ORDER BY ?1")
    @Query("SELECT d FROM Doctor d LEFT JOIN FETCH d.appointments a LEFT JOIN FETCH a.patient p")
    Optional<List<Doctor>> getAllDoctor(String sortBy);

    // Default JpaRepository methods:
    // - save(Doctor doctor)          --> for insert & update
    // - findById(Long id)            --> for finding by ID
    // - findAll()                    --> for all doctors
    // - deleteById(Long id)          --> for delete

}
