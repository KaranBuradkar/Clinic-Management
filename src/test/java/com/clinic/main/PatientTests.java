package com.clinic.main;

import com.clinic.main.dtos.PatientDto;
import com.clinic.main.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class PatientTests {

    @Autowired
    private PatientService patientService;

    @Test
    public void testPatientService() {
        PatientDto p = new PatientDto();
        p.setId(3L);
        p.setName("Ram");
        p.setEmail("ram@gmail.com");
        p.setBirthDate(LocalDate.of(2009, 5, 2));
        p.setGender("Male");
        p.setPhoneNo("89483479788");

        p = patientService.getPatientDtoById(1L);
        p.setBirthDate(LocalDate.of(2004, 3, 30));
        patientService.updatePatient(p.getId(), p);

        System.out.println("Get All: "+patientService.getAllPatientDto());
    }

    @Test
    public void testRepositoryGetMethods() {
        // VIEW patient by ID
        System.out.println("Patient By Id: " + patientService.getPatientDtoById(4L));

        // View All Patients
        System.out.println("All Patients: ");
        displayPatients(patientService.getAllPatientDto());

        // View Patients Who is Age between lowerAge and upperAge
        System.out.println("Patient whose Age Range Between 18 and 27: ");
        displayPatients(patientService.getPatientDtoBetweenAge(18, 27));

        // Page Of Patient
        System.out.println("Page of Patient: ");
        displayPatients(patientService.getPatientDtoPage(0, 3, "id", dir));

    }

    @Test
    public void testPatientDetailModification() {
        // ADD NEW PATIENT
        PatientDto p = new PatientDto();
        p.setName("Raju");
        p.setEmail("raju@gmail.com");
        p.setBirthDate(LocalDate.of(2002, 4, 2));
        p.setGender("Male");
        p.setPhoneNo("89483479588");

        PatientDto addedPatient = patientService.addPatient(p);
        System.out.println("New Patient Added: "+ addedPatient);

        // Update Patient Detail
        addedPatient.setEmail("raju.gh0ate@gmail.com");
        PatientDto updatedPatient = patientService.updatePatient(addedPatient.getId(), addedPatient);
        System.out.println("Updated Patient: "+updatedPatient);

        // Delete Patient By ID
        System.out.println(patientService.deletePatientById(updatedPatient.getId()));
    }

    @Test
    public void testGetPatients() {
        // View All Patients
        System.out.println("All Patients: ");
        displayPatients(patientService.getAllPatientDto());
    }

    private void displayPatients(List<PatientDto> patientDtos) {
        for (PatientDto patientDto : patientDtos) {
            System.out.println(patientDto);
        }
    }
}
