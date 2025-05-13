package com.HubertKarw.medical_clinic.Service;

import com.HubertKarw.medical_clinic.Model.Patient;
import com.HubertKarw.medical_clinic.Model.User;
import com.HubertKarw.medical_clinic.Repository.PatientJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

public class PatientJpaServiceTest {
    PatientJpaRepository patientJpaRepository;
    PatientJpaService patientJpaService;

    @BeforeEach
    void setup() {
        this.patientJpaRepository = Mockito.mock(PatientJpaRepository.class);
        this.patientJpaService = new PatientJpaService(patientJpaRepository);
    }
    @Test
    void getPatient_patientsExist_patientsReturned() {
        //given
        User user = new User(1l, "123", "321");
        Patient patient1 = new Patient(123L, "x1", user, "123", "xyz", "xzy", "123", LocalDate.of(1999, 11, 11));
        Patient patient2 = new Patient(124L, "x2", user, "124", "xyz", "xzy", "123", LocalDate.of(1999, 12, 11));
        Patient patient3 = new Patient(125L, "x3", user, "125", "xyz", "xzy", "123", LocalDate.of(1999, 10, 11));
        PageImpl<Patient> page = new PageImpl<>(List.of(patient1, patient2, patient3));
        when(patientJpaRepository.findAll(any(Pageable.class))).thenReturn(page);
        //when
        List<Patient> result = patientJpaService.getPatients(PageRequest.of(0,3));
        //then
        assertAll(
                () -> assertEquals(3,result.size()),
                () -> assertEquals(123,result.get(0).getId()),
                () -> assertEquals(124,result.get(1).getId()),
                () -> assertEquals(125,result.get(2).getId())
        );
    }

    @Test
    void getPatient_patientExists_patientReturned(){
        //given
        User user = new User(1l, "123", "321");
        Patient patient = new Patient(123L, "x1", user, "123", "xyz", "xzy", "123", LocalDate.of(1999, 11, 11));
        when(patientJpaRepository.findByEmail("x1")).thenReturn(Optional.of(patient));
        //when
        Patient result = patientJpaService.getPatient("x1");
        //then
        assertEquals(patient.getId(),result.getId());
    }

    @Test
    void addPatient_validPatient_patientAdded(){
        //given
        User user = new User(1l, "123", "321");
        Patient patient = new Patient(null, "x1", user, "123", "xyz", "xzy", "123", LocalDate.of(1999, 11, 11));
        Patient patientSaved = new Patient(123L, "x1", user, "123", "xyz", "xzy", "123", LocalDate.of(1999, 11, 11));
        when(patientJpaRepository.save(any())).thenReturn(patientSaved);
        //when
        Patient result = patientJpaService.addPatient(patient);
        //then
        assertEquals(patient.getEmail(),patientSaved.getEmail());
    }
    @Test
    void removePatient_patientExists_patientRemoved(){
        //given
        User user = new User(1l, "123", "321");
        Patient patient = new Patient(null, "x1", user, "123", "xyz", "xzy", "123", LocalDate.of(1999, 11, 11));
        Patient patientSaved = new Patient(123L, "x1", user, "123", "xyz", "xzy", "123", LocalDate.of(1999, 11, 11));
        when(patientJpaRepository.findByEmail(any())).thenReturn(Optional.of(patient));
        //when
        patientJpaService.removePatient(patient.getEmail());
        //then
        assertEquals(1,1);
    }
    @Test
    void modifyPatient_patientExists_patientModified(){
        User user = new User(1l, "123", "321");
        Patient patient = new Patient(null, "x1", user, "123", "xyz", "xzy", "123", LocalDate.of(1999, 11, 11));
        Patient modifiedPatient = new Patient(123L, "x1", user, "123", "xyzz", "xzy", "123", LocalDate.of(1999, 11, 11));
        when(patientJpaRepository.findByEmail(any())).thenReturn(Optional.of(patient));
        when(patient.update(any(Patient.class))).thenReturn(modifiedPatient);
        when(patientJpaRepository.save(any())).thenReturn(modifiedPatient);
        //when
        patientJpaService.modifyPatient("x1",modifiedPatient);
        //then
        assertNotEquals(patient.getFirstName(),modifiedPatient.getFirstName());
    }
}
