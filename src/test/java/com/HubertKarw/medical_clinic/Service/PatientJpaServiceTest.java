package com.HubertKarw.medical_clinic.Service;

import com.HubertKarw.medical_clinic.Exception.MedicalClinicException;
import com.HubertKarw.medical_clinic.Exception.PatientNotFoundException;
import com.HubertKarw.medical_clinic.Model.Patient;
import com.HubertKarw.medical_clinic.Model.User;
import com.HubertKarw.medical_clinic.Repository.PatientJpaRepository;
import org.junit.jupiter.api.Assertions;
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
import static org.mockito.Mockito.*;

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
        List<Patient> result = patientJpaService.getPatients(PageRequest.of(0, 3));
        //then
        assertAll(
                () -> assertEquals(3, result.size()),
                () -> assertEquals(123, result.get(0).getId()),
                () -> assertEquals(124, result.get(1).getId()),
                () -> assertEquals(125, result.get(2).getId())
        );
    }

    @Test
    void getPatient_patientExists_patientReturned() {
        //given
        User user = new User(1l, "123", "321");
        Patient patient = new Patient(123L, "x1", user, "123", "xyz", "xzy", "123", LocalDate.of(1999, 11, 11));
        when(patientJpaRepository.findByEmail("x1")).thenReturn(Optional.of(patient));
        //when
        Patient result = patientJpaService.getPatient("x1");
        //then
        assertEquals(patient.getId(), result.getId());
    }

    @Test
    void getPatient_patientDoesNotExist_throwException() {
        //given
        when(patientJpaRepository.findByEmail("123")).thenReturn(Optional.empty());
        //when
        //then
        MedicalClinicException exception = Assertions.assertThrows(MedicalClinicException.class, () -> patientJpaService.getPatient("123"));
        assertEquals("Patient with this email does not exist", exception.getMessage());
    }

    @Test
    void addPatient_validPatient_patientAdded() {
        //given
        User user = new User(1l, "123", "321");
        Patient patient = new Patient(null, "x1", user, "123", "xyz", "xzy", "123", LocalDate.of(1999, 11, 11));
        Patient patientSaved = new Patient(123L, "x1", user, "123", "xyz", "xzy", "123", LocalDate.of(1999, 11, 11));
        when(patientJpaRepository.save(any())).thenReturn(patientSaved);
        //when
        Patient result = patientJpaService.addPatient(patient);
        //then
        assertEquals(patient.getEmail(), patientSaved.getEmail());
    }

    @Test
    void removePatient_patientExists_patientRemoved() {
        //given
        User user = new User(1l, "123", "321");
        Patient patient = new Patient(null, "x1", user, "123", "xyz", "xzy", "123", LocalDate.of(1999, 11, 11));
        Patient patientSaved = new Patient(123L, "x1", user, "123", "xyz", "xzy", "123", LocalDate.of(1999, 11, 11));
        when(patientJpaRepository.findByEmail(any())).thenReturn(Optional.of(patient));
        //when
        patientJpaService.removePatient(patient.getEmail());
        //then
        verify(patientJpaRepository).delete(patient);
    }

    @Test
    void removePatient_patientDoesNotExist_throwException() {
        //given
        when(patientJpaRepository.findByEmail("123")).thenReturn(Optional.empty());
        //when
        //then
        PatientNotFoundException exception = Assertions.assertThrows(PatientNotFoundException.class, () -> patientJpaService.removePatient("123"));
        assertEquals("no patient with this email", exception.getMessage());
    }

    @Test
    void modifyPatient_patientExists_patientModified() {
        User user = new User(1l, "123", "321");
        Patient patient = new Patient(null, "x1", user, "123", "xyz", "xzy", "123", LocalDate.of(1999, 11, 11));
        Patient modifiedPatient = new Patient(123L, "x1", user, "123", "xyzz", "xzy", "123", LocalDate.of(1999, 11, 11));
        when(patientJpaRepository.findByEmail(any())).thenReturn(Optional.of(patient));
        when(patientJpaRepository.save(any())).thenReturn(modifiedPatient);
        //when
        Patient result = patientJpaService.modifyPatient("x1", modifiedPatient);
        //then
        assertEquals(modifiedPatient.getFirstName(), result.getFirstName());
    }

    @Test
    void modifyPatient_patientDoesNotExist_throwException() {
        //given
        User user = new User(1l, "123", "321");
        Patient modifiedPatient = new Patient(123L, "x1", user, "123", "xyzz", "xzy", "123", LocalDate.of(1999, 11, 11));
        when(patientJpaRepository.findByEmail("123")).thenReturn(Optional.empty());
        //when
        //then
        PatientNotFoundException exception = Assertions.assertThrows(PatientNotFoundException.class, () -> patientJpaService.modifyPatient("123", modifiedPatient));
        assertEquals("no patient with this email", exception.getMessage());
    }
}
