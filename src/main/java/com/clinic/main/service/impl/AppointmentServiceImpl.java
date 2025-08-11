package com.clinic.main.service.impl;

import com.clinic.main.customeExceptions.AppointmentNotFoundException;
import com.clinic.main.dtos.AppointmentDto;
import com.clinic.main.dtos.AppointmentPerDoctorDTO;
import com.clinic.main.entity.Appointment;
import com.clinic.main.entity.Doctor;
import com.clinic.main.entity.Patient;
import com.clinic.main.entityMapper.AppointmentMapper;
import com.clinic.main.repository.AppointmentRepository;
import com.clinic.main.service.AppointmentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentMapper appointmentMapper;

    private final AppointmentRepository appointmentRepository;
    private final DoctorServiceImpl doctorServiceImpl;
    private final PatientServiceImpl patientServiceImpl;

    @Autowired
    public AppointmentServiceImpl(
            AppointmentMapper appointmentMapper, AppointmentRepository appointmentRepository,
            DoctorServiceImpl doctorServiceImpl, PatientServiceImpl patientServiceImpl) {
        this.appointmentMapper = appointmentMapper;
        this.appointmentRepository = appointmentRepository;
        this.doctorServiceImpl = doctorServiceImpl;
        this.patientServiceImpl = patientServiceImpl;
    }

    // View appointments by patient ID
    public List<AppointmentDto> getAppointmentByPatientId(Long patientId) {
        List<Appointment> appointments = appointmentRepository.findByPatient_Id(patientId);
        return appointments.stream()
                .map(appointmentMapper::toDto)
                .toList();
    }

    public List<AppointmentPerDoctorDTO> getAppointmentCountPerDoctor() {
        return appointmentRepository.countAppointmentsPerDoctor();
    }

    public List<AppointmentDto> getAppointmentPage(Integer pageNumber, Integer pageSize, String sortBy) {
        Page<Appointment> appointments = appointmentRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortBy)));
        return appointments.stream()
                .map(appointmentMapper::toDto)
                .toList();
    }

    // Sort appointments by date or time - default - .findAll(Sort.by("date", "time").ascending);
    public List<AppointmentDto> getAppointmentsOrderByDateAndTime() {
        List<Appointment> appointments = appointmentRepository.findAll(Sort.by("date", "time").ascending());
        return appointments.stream()
                .map(appointmentMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public AppointmentDto scheduleAppointment(AppointmentDto appointmentDto) {
        Appointment appointment = appointmentMapper.toEntity(appointmentDto);
        return appointmentMapper.toDto(scheduleAppointment(appointment));
    }

    @Transactional
    @Modifying
    @Override
    public AppointmentDto scheduleAppointment(AppointmentDto appointmentDto, Long patientId, Long doctorId) {
        if (appointmentDto.getId() != null) {
            throw new IllegalArgumentException("AppointmentId must be NULL With Id: "+appointmentDto.getId());
        }
        Appointment appointment = appointmentMapper.toEntity(appointmentDto);
        Patient patient = patientServiceImpl.getPatientByIdAsEntity(patientId);
        Doctor doctor = doctorServiceImpl.getDoctorByIdAsEntity(doctorId);
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        // Bidirectional Consistency
        patient.getAppointments().add(savedAppointment);
        doctor.getAppointments().add(savedAppointment);
        return appointmentMapper.toDto(savedAppointment);
    }

    // Schedule appointment - default - .save(appointment);
    @Modifying
    @Transactional
    Appointment scheduleAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<AppointmentDto> getAllAppointmentDtos(String sortBy) {
        List<Appointment> appointments = getAllAppointmentDto(sortBy);
        return appointments.stream()
                .map(appointmentMapper::toDto)
                .toList();
    }
    // View all appointments - default - .findAll();
    public List<Appointment> getAllAppointmentDto(String sortBy) {
        return appointmentRepository.findAll(Sort.by(Sort.Direction.ASC, sortBy));
    }

    @Override
    public AppointmentDto getAppointmentDtoById(Long appointmentId) {
        return appointmentMapper.toDto(getAppointmentByIdAsEntity(appointmentId));
    }
    // View appointment by ID - default - .findById(appointmentId);
    Appointment getAppointmentByIdAsEntity(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment Not Found With Id: "+appointmentId));
    }
    List<Appointment> getAppointmentsByIdAsEntities(List<Long> appointmentIds) {
        return appointmentIds.stream()
                .map(this::getAppointmentByIdAsEntity)
                .toList();
    }

    @Override
    public List<AppointmentDto> getAppointmentDtosOfDoctorId(Long doctorId) {
        List<Appointment> appointments = getAppointmentByDoctorIdAsEntity(doctorId);
        return appointments.stream()
                .map(appointmentMapper::toDto)
                .toList();
    }
    // View appointments by doctor ID
    List<Appointment> getAppointmentByDoctorIdAsEntity(Long doctorId) {
        return appointmentRepository.findByDoctor_Id(doctorId);
    }

    @Override
    public List<AppointmentDto> getAppointmentDtosOfPatientId(Long patientId) {
        List<Appointment> appointments = getAppointmentByPatientIdAsEntity(patientId);
        return appointments.stream()
                .map(appointmentMapper::toDto)
                .toList();
    }
    // View appointments by patient ID
    List<Appointment> getAppointmentByPatientIdAsEntity(Long patientId) {
        return appointmentRepository.findByPatient_Id(patientId);
    }

    @Override
    public List<AppointmentDto> getAppointmentDtosByDate(LocalDate date) {
        List<Appointment> appointments = getAppointmentByDateAsEntity(date);
        return appointments.stream()
                .map(appointmentMapper::toDto)
                .toList();
    }
    // View appointments by Date
    List<Appointment> getAppointmentByDateAsEntity(LocalDate date) {
        return appointmentRepository.findByDate(date);
    }

    @Override
    public List<AppointmentDto> getUpcomingAppointmentDtos() {
        List<Appointment> appointments = appointmentRepository.findUpcomingAppointments();
        return appointments.stream()
                .map(appointmentMapper::toDto)
                .toList();
    }

    // Cancel appointment - default - .deleteById(appointment);
    @Modifying
    @Transactional
    public String cancelAppointmentById(Long appointmentId) {
        if (!appointmentRepository.existsById(appointmentId))
            throw new AppointmentNotFoundException("Appointment Doesn't Exist For Id: "+appointmentId);
        appointmentRepository.deleteById(appointmentId);
        return "Appointment Cancel Successfully!";
    }

    @Override
    public AppointmentDto updateAppointment(Long appointmentId, AppointmentDto appointmentDto) {
        Appointment appointment = appointmentMapper.toEntity(appointmentDto);
        appointment.setId(appointmentId); // For update ID required.
        return appointmentMapper.toDto(updateAppointment(appointment));
    }
    // Update appointment - default - .save(appointment);
    @Modifying
    @Transactional
    Appointment updateAppointment(Appointment appointment) {
        if (!appointmentRepository.existsById(appointment.getId()))
            throw new AppointmentNotFoundException("Appointment Not Found With Id: "+appointment.getId());
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<AppointmentPerDoctorDTO> getCountAppointmentsPerDoctor() {
        return appointmentRepository.countAppointmentsPerDoctor();
    }

    @Override
    public List<AppointmentDto> getOrderedAppointmentDtosByDateAndTime() {
        List<Appointment> appointments = getOrderedAppointmentByDateAndTimeAsEntities();
        return appointments.stream()
                .map(appointmentMapper::toDto)
                .toList();
    }
    List<Appointment> getOrderedAppointmentByDateAndTimeAsEntities() {
        return appointmentRepository.findAll(Sort.by("date","time").ascending());
    }

    @Override
    public List<AppointmentDto> getAppointmentDtoPage(Integer pageNumber, Integer pageSize, String sortBy) {
        List<Appointment> appointments = getAppointmentPageAsEntities(pageNumber, pageSize, sortBy);
        return appointments.stream()
                .map(appointmentMapper::toDto)
                .toList();
    }

    @Override
    public List<AppointmentDto> getAppointmentsFiltered(Long doctorId, Long patientId, LocalDate date, int page, int size, String sortBy) {
        if (doctorId != null) {
            return getAppointmentDtosOfDoctorId(doctorId);
        }
        if (patientId != null) {
            return getAppointmentDtosOfPatientId(patientId);
        }
        if (date != null) {
            return getAppointmentDtosByDate(date);
        }
        return getAppointmentDtoPage(page, size, sortBy);
    }

    List<Appointment> getAppointmentPageAsEntities(Integer pageNumber, Integer pageSize, String sortBy) {
        return appointmentRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortBy, "time"))).toList();
    }
}
