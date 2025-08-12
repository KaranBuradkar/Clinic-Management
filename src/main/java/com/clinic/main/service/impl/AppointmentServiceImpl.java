package com.clinic.main.service.impl;

import com.clinic.main.customeExceptions.AppointmentNotFoundException;
import com.clinic.main.dtos.AppointmentDto;
import com.clinic.main.dtos.AppointmentPerDoctorDTO;
import com.clinic.main.entity.Appointment;
import com.clinic.main.entity.Doctor;
import com.clinic.main.entity.Patient;
import com.clinic.main.entityMapper.AppointmentMapper;
import com.clinic.main.entityMapper.DoctorMapper;
import com.clinic.main.entityMapper.PatientMapper;
import com.clinic.main.repository.AppointmentRepository;
import com.clinic.main.service.AppointmentService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    private static final Logger log = LoggerFactory.getLogger(AppointmentServiceImpl.class);
    private final AppointmentMapper appointmentMapper;
    private final PatientMapper patientMapper;
    private final DoctorMapper doctorMapper;

    private final AppointmentRepository appointmentRepository;
    private final DoctorServiceImpl doctorServiceImpl;
    private final PatientServiceImpl patientServiceImpl;

    @Autowired
    public AppointmentServiceImpl(AppointmentMapper appointmentMapper,
                                  PatientMapper patientMapper, DoctorMapper doctorMapper,
                                  AppointmentRepository appointmentRepository, DoctorServiceImpl doctorServiceImpl,
                                  PatientServiceImpl patientServiceImpl) {
        this.appointmentMapper = appointmentMapper;
        this.patientMapper = patientMapper;
        this.doctorMapper = doctorMapper;
        this.appointmentRepository = appointmentRepository;
        this.doctorServiceImpl = doctorServiceImpl;
        this.patientServiceImpl = patientServiceImpl;
    }



    @Override
    @Transactional
    public AppointmentDto scheduleAppointment(AppointmentDto appointmentDto) {
        // Removed log.info("Scheduling appointment from DTO: {}", appointmentDto); due to potential PII
        Appointment appointment = appointmentMapper.toEntity(appointmentDto);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        log.info("Successfully scheduled appointment with ID: {}", savedAppointment.getId());
        return appointmentMapper.toDto(savedAppointment);
    }

    @Override
    @Transactional
    @Modifying
    public AppointmentDto scheduleAppointment(AppointmentDto appointmentDto, Long patientId, Long doctorId) {
        log.info("Scheduling appointment for patient ID {} with doctor ID {}", patientId, doctorId);
        if (appointmentDto.getId() != null) {
            log.warn("Attempt to schedule an appointment with a pre-existing ID.");
            throw new IllegalArgumentException("AppointmentId must be NULL With Id: "+appointmentDto.getId());
        }

        Appointment appointment = appointmentMapper.toEntity(appointmentDto);
        Patient patient = patientMapper.toEntity(patientServiceImpl.getPatientDtoById(patientId));
        Doctor doctor = doctorMapper.toEntity(doctorServiceImpl.getDoctorDtoById(doctorId));

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

        Appointment savedAppointment = appointmentRepository.save(appointment);
        log.info("Appointment successfully scheduled with ID: {}", savedAppointment.getId());

        // Bidirectional Consistency
        patient.getAppointments().add(savedAppointment);
        doctor.getAppointments().add(savedAppointment);
        return appointmentMapper.toDto(savedAppointment);
    }

    @Override
    public List<AppointmentDto> getAllAppointmentDtos(String sortBy) {
        log.info("Fetching all appointments, sorted by {}.", sortBy);
        List<Appointment> appointments = appointmentRepository.findAll(Sort.by(Sort.Direction.ASC, sortBy));
        return appointments.stream()
                .map(appointmentMapper::toDto)
                .toList();
    }

    @Override
    public AppointmentDto getAppointmentDtoById(Long appointmentId) {
        log.trace("Attempting to find appointment entity by ID: {}", appointmentId);
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> {
                    log.error("Appointment with ID {} not found.", appointmentId);
                    return new AppointmentNotFoundException("Appointment Not Found With Id: " + appointmentId);
                });
        return appointmentMapper.toDto(appointment);
    }

    @Override
    public List<AppointmentDto> getAppointmentDtosOfDoctorId(Long doctorId) {
        log.debug("Fetching appointments for doctor with ID: {}", doctorId);
        List<Appointment> appointments = appointmentRepository.findByDoctor_Id(doctorId);
        log.debug("Found {} appointments for doctor ID: {}", appointments.size(), doctorId);
        return appointments.stream()
                .map(appointmentMapper::toDto)
                .toList();
    }

    @Override
    public List<AppointmentDto> getAppointmentDtosOfPatientId(Long patientId) {
        log.debug("Fetching appointments for patient with ID: {}", patientId);
        List<Appointment> appointments = appointmentRepository.findByPatient_Id(patientId);

        log.debug("Found {} appointments for patient ID: {}", appointments.size(), patientId);
        return appointments.stream()
                .map(appointmentMapper::toDto)
                .toList();
    }

    @Override
    public List<AppointmentDto> getAppointmentDtosByDate(LocalDate date) {
        log.debug("Fetching appointments for date: {}", date);
        List<Appointment> appointments = appointmentRepository.findByDate(date);
        log.debug("Found {} appointments for date: {}", appointments.size(), date);
        return appointments.stream()
                .map(appointmentMapper::toDto)
                .toList();
    }

    @Override
    public List<AppointmentDto> getUpcomingAppointmentDtos() {
        log.info("Fetching upcoming appointments.");
        List<Appointment> appointments = appointmentRepository.findUpcomingAppointments();
        log.debug("Found {} upcoming appointments.", appointments.size());
        return appointments.stream()
                .map(appointmentMapper::toDto)
                .toList();
    }

    // Cancel appointment - default - .deleteById(appointment);
    @Override
    @Modifying
    @Transactional
    public String cancelAppointmentById(Long appointmentId) {
        log.info("Attempting to cancel appointment with ID: {}", appointmentId);
        if (!appointmentRepository.existsById(appointmentId)) {
            log.error("Failed to cancel: Appointment with ID {} doesn't exist.", appointmentId);
            throw new AppointmentNotFoundException("Appointment Doesn't Exist For Id: "+appointmentId);
        }
        appointmentRepository.deleteById(appointmentId);
        log.info("Appointment with ID {} successfully canceled.", appointmentId);
        return "Appointment Cancel Successfully!";
    }

    @Override
    @Transactional
    public AppointmentDto updateAppointment(Long appointmentId, AppointmentDto appointmentDto) {

        if (!appointmentRepository.existsById(appointmentId)) {
            log.error("Failed to update: Appointment with ID {} not found.", appointmentId);
            throw new AppointmentNotFoundException("Appointment Not Found With Id: "+appointmentId);
        }

        log.info("Updating appointment with ID {}.", appointmentId);
        Appointment appointment = appointmentMapper.toEntity(appointmentDto);
        appointment.setId(appointmentId); // For update ID required.

        Appointment updatedAppointment = appointmentRepository.save(appointment);
        log.info("Appointment with ID {} updated successfully.", updatedAppointment.getId());
        return appointmentMapper.toDto(updatedAppointment);
    }


    @Override
    public List<AppointmentPerDoctorDTO> getCountAppointmentsPerDoctor() {
        log.info("Counting appointments per doctor.");
        return appointmentRepository.countAppointmentsPerDoctor();
    }

    @Override
    public List<AppointmentDto> getOrderedAppointmentDtosByDateAndTime() {
        log.info("Fetching and ordering appointments by date and time.");
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
        log.info("Fetching appointment page {} with size {} and sorting by {}.", pageNumber, pageSize, sortBy);
        List<Appointment> appointments = appointmentRepository
                .findAll(PageRequest.of(pageNumber, pageSize,
                        Sort.by(Sort.Direction.ASC, sortBy, "time"))).toList();

        return appointments.stream()
                .map(appointmentMapper::toDto)
                .toList();
    }

    @Override
    public List<AppointmentDto> getAppointmentsFiltered(Long doctorId, Long patientId, LocalDate date, int page, int size, String sortBy) {
        log.info("Filtering appointments with parameters: doctorId={}, patientId={}, date={}, page={}, size={}, sortBy={}", doctorId, patientId, date, page, size, sortBy);
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

}