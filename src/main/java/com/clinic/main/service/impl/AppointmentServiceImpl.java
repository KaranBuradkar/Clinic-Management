package com.clinic.main.service.impl;

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
    private final AppointmentRepository appointmentRepository;

    private final DoctorServiceImpl doctorServiceImpl;

    private final PatientServiceImpl patientServiceImpl;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, DoctorServiceImpl doctorServiceImpl, PatientServiceImpl patientServiceImpl) {
        this.appointmentRepository = appointmentRepository;
        this.doctorServiceImpl = doctorServiceImpl;
        this.patientServiceImpl = patientServiceImpl;
    }

    // View appointments by patient ID
    public List<AppointmentDto> getAppointmentByPatientId(Long patientId) {
        List<Appointment> appointments = appointmentRepository.findByPatient_Id(patientId);
        return AppointmentMapper.mapToDto(appointments);
    }

    public List<AppointmentPerDoctorDTO> getAppointmentCountPerDoctor() {
        return appointmentRepository.countAppointmentsPerDoctor();
    }

    public List<AppointmentDto> getAppointmentPage(Integer pageNumber, Integer pageSize, String sortBy) {
        Page<Appointment> appointments = appointmentRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortBy)));
        return AppointmentMapper.mapToDto(appointments.toList());
    }

    // Sort appointments by date or time - default - .findAll(Sort.by("date", "time").ascending);
    public List<AppointmentDto> getAppointmentsOrderByDateAndTime() {
        List<Appointment> all = appointmentRepository.findAll(Sort.by("date", "time").ascending());
        return AppointmentMapper.mapToDto(all);
    }

    @Override
    @Transactional
    public AppointmentDto scheduleAppointment(AppointmentDto appointmentDto) {
        Appointment appointment = dtoToAppointment(appointmentDto);
        return AppointmentMapper.mapToDto(scheduleAppointment(appointment));
    }
    // Schedule appointment - default - .save(appointment);
    @Modifying
    @Transactional
    Appointment scheduleAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }
    public List<AppointmentDto> scheduleAppointments(List<AppointmentDto> appointmentDtos) {
        List<Appointment> appointments = appointmentDtos.stream()
                .map(this::dtoToAppointment)
                .toList();
        return AppointmentMapper.mapToDto(scheduleAllAppointments(appointments));
    }
    // Schedule All appointments - default - .save(appointment);
    @Transactional
    List<Appointment> scheduleAllAppointments(List<Appointment> appointments) {
        return appointmentRepository.saveAll(appointments);
    }

    @Override
    public List<AppointmentDto> getAllAppointmentDtos() {
        return AppointmentMapper.mapToDto(getAllAppointmentDto());
    }
    // View all appointments - default - .findAll();
    public List<Appointment> getAllAppointmentDto() {
        return appointmentRepository.findAll(Sort.by("id").ascending());
    }

    @Override
    public AppointmentDto getAppointmentDtoById(Long appointmentId) {
        return AppointmentMapper.mapToDto(getAppointmentByIdAsEntity(appointmentId));
    }
    // View appointment by ID - default - .findById(appointmentId);
    Appointment getAppointmentByIdAsEntity(Long appointmentId) {
        return appointmentRepository.findById(appointmentId).orElseThrow();
    }
    List<Appointment> getAppointmentsByIdAsEntities(List<Long> appointmentIds) {
        return appointmentIds.stream()
                .map(this::getAppointmentByIdAsEntity)
                .toList();
    }

    @Override
    public List<AppointmentDto> getAppointmentDtosOfDoctorId(Long doctorId) {
        return AppointmentMapper.mapToDto(getAppointmentByDoctorIdAsEntity(doctorId));
    }
    // View appointments by doctor ID
    List<Appointment> getAppointmentByDoctorIdAsEntity(Long doctorId) {
        return appointmentRepository.findByDoctor_Id(doctorId);
    }

    @Override
    public List<AppointmentDto> getAppointmentDtosOfPatientId(Long patientId) {
        return AppointmentMapper.mapToDto(getAppointmentByPatientIdAsEntity(patientId));
    }
    // View appointments by patient ID
    List<Appointment> getAppointmentByPatientIdAsEntity(Long patientId) {
        return appointmentRepository.findByPatient_Id(patientId);
    }

    @Override
    public List<AppointmentDto> getAppointmentDtosByDate(LocalDate date) {
        return AppointmentMapper.mapToDto(getAppointmentByDateAsEntity(date));
    }
    // View appointments by Date
    List<Appointment> getAppointmentByDateAsEntity(LocalDate date) {
        return appointmentRepository.findByDate(date);
    }

    @Override
    public List<AppointmentDto> getUpcomingAppointmentDtos() {
//        return AppointmentMapper.mapToDto(getUpcomingAppointmentAsEntity());
        return null;
    }
    // View upcoming appointments - default - .findAll(Sort.by("date").descending());
//    public List<Appointment> getUpcomingAppointmentAsEntity() {
//        return appointmentRepository.upcomingAppointments();
//    }

    @Override
    public String cancelAppointment(AppointmentDto appointmentDto) {
        Appointment appointment = dtoToAppointment(appointmentDto);
        return cancelAppointment(appointment);
    }
    // Cancel appointment - default - .delete(appointment);
    @Modifying
    @Transactional
    public String cancelAppointment(Appointment appointment) {
        appointmentRepository.delete(appointment);
        return "Appointment Cancel Successfully!";
    }

    // Cancel appointment - default - .deleteById(appointment);
    @Modifying
    @Transactional
    public String cancelAppointmentById(Long appointmentId) {
        appointmentRepository.deleteById(appointmentId);
        return "Appointment Cancel Successfully!";
    }

    @Override
    public AppointmentDto updateAppointment(AppointmentDto appointmentDto) {
        Appointment appointment = dtoToAppointment(appointmentDto);
        appointment.setId(appointment.getId()); // For update ID required.
        return AppointmentMapper.mapToDto(updateAppointment(appointment));
    }
    // Update appointment - default - .save(appointment);
    @Modifying
    @Transactional
    Appointment updateAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<AppointmentPerDoctorDTO> getCountAppointmentsPerDoctor() {
        return appointmentRepository.countAppointmentsPerDoctor();
    }

    @Override
    public List<AppointmentDto> getOrderedAppointmentDtosByDateAndTime() {
        return AppointmentMapper.mapToDto(getOrderedAppointmentByDateAndTimeAsEntities());
    }
    List<Appointment> getOrderedAppointmentByDateAndTimeAsEntities() {
        return appointmentRepository.findAll(Sort.by("date","time").ascending());
    }

    @Override
    public List<AppointmentDto> getAppointmentDtoPage(Integer pageNumber, Integer pageSize, String sortBy) {
        return AppointmentMapper.mapToDto(getAppointmentPageAsEntities(pageNumber, pageSize, sortBy));
    }
    List<Appointment> getAppointmentPageAsEntities(Integer pageNumber, Integer pageSize, String sortBy) {
        return appointmentRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortBy))).toList();
    }


    @Transactional
    private Appointment dtoToAppointment(AppointmentDto appointmentDto) {
        Doctor doctor = doctorServiceImpl.getDoctorByIdAsEntity(appointmentDto.getDoctorId());
        Patient patient = patientServiceImpl.getPatientByIdAsEntity(appointmentDto.getPatientId());
        if(doctor == null) throw  new IllegalArgumentException("Doctor not found!");
        if(patient == null) throw  new IllegalArgumentException("Patient not found!");
        return AppointmentMapper.mapToAppointment(appointmentDto, doctor, patient);
    }
}
