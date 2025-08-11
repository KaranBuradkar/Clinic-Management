package com.clinic.main;

import com.clinic.main.dtos.DoctorDto;
import com.clinic.main.service.DoctorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DoctorTests {


    @Autowired
    private DoctorService doctorService;

    @Test
    public void testRepositoryGetMethods() {
        // View All Doctors
        System.out.println("All Doctors: ");
        List<DoctorDto> doctorDtos = doctorService.getAllDoctorDtos();
        displayDoctors(doctorDtos);

        // View Doctor By ID
        System.out.println("Doctor By Id: ");
        DoctorDto doctorDto = doctorService.getDoctorDtoById(3L);
        System.out.println(doctorDto);

        // View Doctor Page
        System.out.println("Page of Doctors: ");
        doctorDtos = doctorService.getAPageOfDoctorDto(4, 5, "id");
        displayDoctors(doctorDtos);

        // View Doctors By Specialization
        System.out.println("Doctors By Specialization: ");
        doctorDtos = doctorService.getDoctorDtosBySpecialization("Dentist");
        displayDoctors(doctorDtos);

        // View Sorted Doctor By field
        System.out.println("Sort doctors by name: ");
        doctorDtos = doctorService.getDoctorDtosSortedBy("name");
        displayDoctors(doctorDtos);
    }

    @Test
    public void testRepositoryDoctorDetailModification() {
        DoctorDto newDoctorDto = new DoctorDto();
        newDoctorDto.setName("Ramu Radhe");
        newDoctorDto.setSpecialization("Emergency");
        newDoctorDto.setExperience(5);

        // ADD Doctor
        System.out.println("New Doctor Added: ");
        DoctorDto addedDoctor = doctorService.addDoctor(newDoctorDto);
        System.out.println(addedDoctor);

        // Update Doctor
        addedDoctor.setSpecialization("Ventilation");
        DoctorDto updatedDoctor = doctorService.updateDoctor(addedDoctor.getId(), addedDoctor);
        System.out.println(updatedDoctor);

        // Delete Doctor
        System.out.println(doctorService.deleteById(updatedDoctor.getId()));
    }

    private void displayDoctors(List<DoctorDto> doctorDtos) {
        for (DoctorDto doctorDto: doctorDtos) {
            System.out.println(doctorDto);
        }
    }
}
