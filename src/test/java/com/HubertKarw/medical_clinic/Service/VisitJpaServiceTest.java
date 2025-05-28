package com.HubertKarw.medical_clinic.Service;

import com.HubertKarw.medical_clinic.Exception.VisitDateException;
import com.HubertKarw.medical_clinic.Model.Doctor;
import com.HubertKarw.medical_clinic.Model.Patient;
import com.HubertKarw.medical_clinic.Model.User;
import com.HubertKarw.medical_clinic.Model.Visit;
import com.HubertKarw.medical_clinic.Repository.DoctorJpaRepository;
import com.HubertKarw.medical_clinic.Repository.PatientJpaRepository;
import com.HubertKarw.medical_clinic.Repository.VisitJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class VisitJpaServiceTest {

    DoctorJpaRepository doctorRepository;
    PatientJpaRepository patientRepository;
    VisitJpaRepository repository;
    VisitJpaService service;

    @BeforeEach
    void setup() {
        this.doctorRepository = Mockito.mock(DoctorJpaRepository.class);
        this.patientRepository = Mockito.mock(PatientJpaRepository.class);
        this.repository = Mockito.mock(VisitJpaRepository.class);
        this.service = new VisitJpaService(repository, doctorRepository, patientRepository);
    }

    @Test
    void getVisits_visitsExist_visitsReturned() {
        User user = new User(1L, "user", "pass");
        Doctor doctor = new Doctor(1L, "email", user, "fn", "ln", "spec", new ArrayList<>());
        Visit visit1 = new Visit(1L, LocalDateTime.of(2025, 11, 11, 10, 15, 0), LocalDateTime.of(2025, 11, 11, 10, 30, 0), doctor, null);
        Visit visit2 = new Visit(2L, LocalDateTime.of(2025, 11, 12, 10, 15, 0), LocalDateTime.of(2025, 11, 12, 10, 30, 0), doctor, null);
        Visit visit3 = new Visit(3L, LocalDateTime.of(2025, 11, 13, 10, 15, 0), LocalDateTime.of(2025, 11, 13, 10, 30, 0), doctor, null);
        PageImpl<Visit> page = new PageImpl<>(List.of(visit1, visit2, visit3));
        when(repository.findAll(any(Pageable.class))).thenReturn(page);
        //when
        List<Visit> result = service.getVisits(PageRequest.of(0, 3));
        //then
        assertAll(
                () -> assertEquals(3, result.size()),
                () -> assertEquals(1L, result.get(0).getId()),
                () -> assertEquals(LocalDateTime.of(2025, 11, 11, 10, 15, 0), result.get(0).getVisitStart()),
                () -> assertEquals(LocalDateTime.of(2025, 11, 11, 10, 30, 0), result.get(0).getVisitEnd()),
                () -> assertEquals(1L, result.get(0).getDoctor().getId()),
                () -> assertNull(result.get(0).getPatient())
        );
    }

    @Test
    void getVisit_VisitExists_visitReturned() {
        //given
        User user = new User(1L, "user", "pass");
        Doctor doctor = new Doctor(1L, "email", user, "fn", "ln", "spec", new ArrayList<>());
        Visit visit1 = new Visit(1L, LocalDateTime.of(2025, 11, 11, 10, 15, 0), LocalDateTime.of(2025, 11, 11, 10, 30, 0), doctor, null);
        when(repository.findById(1L)).thenReturn(Optional.of(visit1));
        //when
        Visit result = service.getVisit(1L);
        //then
        assertAll(
                () -> assertEquals(1L, result.getId()),
                () -> assertEquals(LocalDateTime.of(2025, 11, 11, 10, 15, 0), result.getVisitStart()),
                () -> assertEquals(LocalDateTime.of(2025, 11, 11, 10, 30, 0), result.getVisitEnd()),
                () -> assertEquals(1L, result.getDoctor().getId()),
                () -> assertEquals("email", result.getDoctor().getEmail()),
                () -> assertEquals("fn", result.getDoctor().getFirstName()),
                () -> assertEquals("ln", result.getDoctor().getLastName()),
                () -> assertEquals("spec", result.getDoctor().getSpecialization()),
                () -> assertEquals(0, result.getDoctor().getInstitutions().size()),
                () -> assertEquals("user", result.getDoctor().getUser().getUsername()),
                () -> assertEquals("pass", result.getDoctor().getUser().getPassword())
        );
    }

    @Test
    void getVisitsByDoctor_doctorHasVisits_visitsReturned(){
        //given
        User user = new User(1L, "user", "pass");
        Doctor doctor = new Doctor(1L, "email", user, "fn", "ln", "spec", new ArrayList<>());
        Visit visit1 = new Visit(1L, LocalDateTime.of(2025, 11, 11, 10, 15, 0), LocalDateTime.of(2025, 11, 11, 10, 30, 0), doctor, null);
        Visit visit2 = new Visit(2L, LocalDateTime.of(2025, 11, 11, 10, 45, 0), LocalDateTime.of(2025, 11, 11, 11, 0, 0), doctor, null);
        PageImpl<Visit> page = new PageImpl<>(List.of(visit1, visit2));
        when(repository.findByDoctor(eq(doctor),any(Pageable.class))).thenReturn(page);
        when(doctorRepository.findByEmail(any())).thenReturn(Optional.of(doctor));
        //when
        List<Visit> result = service.getVisitsByDoctor(PageRequest.of(0,2),"email");
        //then
        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertEquals(1L, result.get(0).getId()),
                () -> assertEquals(LocalDateTime.of(2025, 11, 11, 10, 15, 0), result.get(0).getVisitStart()),
                () -> assertEquals(LocalDateTime.of(2025, 11, 11, 10, 30, 0), result.get(0).getVisitEnd()),
                () -> assertEquals(1L, result.get(0).getDoctor().getId()),
                () -> assertNull(result.get(0).getPatient())
        );
    }

    @Test
    void getVisitsByPatient_PatientHasVisits_visitsReturned(){
        //given
        User user = new User(1L, "user", "pass");
        Doctor doctor = new Doctor(1L, "email", user, "fn", "ln", "spec", new ArrayList<>());
        Patient patient = new Patient(123L, "x1", user, "123", "xyz", "xzy", "123", LocalDate.of(1999, 11, 11));
        Visit visit1 = new Visit(1L, LocalDateTime.of(2025, 11, 11, 10, 15, 0), LocalDateTime.of(2025, 11, 11, 10, 30, 0), doctor, patient);
        Visit visit2 = new Visit(2L, LocalDateTime.of(2025, 11, 11, 10, 45, 0), LocalDateTime.of(2025, 11, 11, 11, 0, 0), doctor, patient);
        PageImpl<Visit> page = new PageImpl<>(List.of(visit1, visit2));
        when(repository.findByPatient(eq(patient),any(Pageable.class))).thenReturn(page);
        when(patientRepository.findByEmail(any())).thenReturn(Optional.of(patient));
        //when
        List<Visit> result = service.getVisitsByPatient(PageRequest.of(0,2),"x1");
        //then
        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertEquals(1L, result.get(0).getId()),
                () -> assertEquals(LocalDateTime.of(2025, 11, 11, 10, 15, 0), result.get(0).getVisitStart()),
                () -> assertEquals(LocalDateTime.of(2025, 11, 11, 10, 30, 0), result.get(0).getVisitEnd()),
                () -> assertEquals(1L, result.get(0).getDoctor().getId()),
                () -> assertEquals(123L, result.get(0).getPatient().getId())
        );
    }

    @Test
    void createVisit_validVisit_visitReturned(){
        User user = new User(1L, "user", "pass");
        Doctor doctor = new Doctor(1L, "email", user, "fn", "ln", "spec", new ArrayList<>());
        Visit visit = new Visit(1L, LocalDateTime.of(2025, 11, 11, 10, 15, 0), LocalDateTime.of(2025, 11, 11, 10, 30, 0), doctor, null);
        when(repository.save(any())).thenReturn(visit);
        //when
        Visit result = service.createVisit(visit);
        //then
        assertAll(
                () -> assertEquals(1L, result.getId()),
                () -> assertEquals(LocalDateTime.of(2025, 11, 11, 10, 15, 0), result.getVisitStart()),
                () -> assertEquals(LocalDateTime.of(2025, 11, 11, 10, 30, 0), result.getVisitEnd()),
                () -> assertEquals(1L, result.getDoctor().getId()),
                () -> assertEquals("email", result.getDoctor().getEmail()),
                () -> assertEquals("fn", result.getDoctor().getFirstName()),
                () -> assertEquals("ln", result.getDoctor().getLastName()),
                () -> assertEquals("spec", result.getDoctor().getSpecialization()),
                () -> assertEquals(0, result.getDoctor().getInstitutions().size()),
                () -> assertEquals("user", result.getDoctor().getUser().getUsername()),
                () -> assertEquals("pass", result.getDoctor().getUser().getPassword())
        );
    }
    @Test
    void createVisit_visitInPast_throwsException(){
        User user = new User(1L, "user", "pass");
        Doctor doctor = new Doctor(1L, "email", user, "fn", "ln", "spec", new ArrayList<>());
        Visit visit = new Visit(1L, LocalDateTime.of(2020, 11, 11, 10, 15, 0), LocalDateTime.of(2020, 11, 11, 10, 30, 0), doctor, null);
        when(repository.save(any())).thenReturn(visit);
        //when
        //then
        VisitDateException exception = Assertions.assertThrows(VisitDateException.class,() -> service.createVisit(visit));
        assertEquals("Cannot create visit in past",exception.getMessage());
    }
    @Test
    void createVisit_visitDurationEqualsZero_throwsException(){
        User user = new User(1L, "user", "pass");
        Doctor doctor = new Doctor(1L, "email", user, "fn", "ln", "spec", new ArrayList<>());
        Visit visit = new Visit(1L, LocalDateTime.of(2025, 11, 11, 10, 15, 0), LocalDateTime.of(2025, 11, 11, 10, 15, 0), doctor, null);
        when(repository.save(any())).thenReturn(visit);
        //when
        //then
        VisitDateException exception = Assertions.assertThrows(VisitDateException.class,() -> service.createVisit(visit));
        assertEquals("Dates are not valid",exception.getMessage());
    }
    @Test
    void createVisit_overlappingVisit_throwsException(){
        User user = new User(1L, "user", "pass");
        Doctor doctor = new Doctor(1L, "email", user, "fn", "ln", "spec", new ArrayList<>());
        Visit visit = new Visit(1L, LocalDateTime.of(2025, 11, 11, 10, 15, 0), LocalDateTime.of(2025, 11, 11, 10, 45, 0), doctor, null);
        when(repository.save(any())).thenReturn(visit);
        when(repository.findOverlappingVisits(any(),any(),any())).thenReturn(List.of(visit));
        //when
        //then
        VisitDateException exception = Assertions.assertThrows(VisitDateException.class,() -> service.createVisit(visit));
        assertEquals("There are overlapping visits",exception.getMessage());
    }
    @Test
    void createVisit_startTimeNotInQuarterOfAnHour_throwsException(){
        User user = new User(1L, "user", "pass");
        Doctor doctor = new Doctor(1L, "email", user, "fn", "ln", "spec", new ArrayList<>());
        Visit visit = new Visit(1L, LocalDateTime.of(2025, 11, 11, 10, 16, 0), LocalDateTime.of(2025, 11, 11, 10, 33, 0), doctor, null);
        when(repository.save(any())).thenReturn(visit);
        //when
        //then
        VisitDateException exception = Assertions.assertThrows(VisitDateException.class,() -> service.createVisit(visit));
        assertEquals("Start Date not in quarter of an Hour",exception.getMessage());
    }
    @Test
    void createVisit_endTimeNotInQuarterOfAnHour_throwsException(){
        User user = new User(1L, "user", "pass");
        Doctor doctor = new Doctor(1L, "email", user, "fn", "ln", "spec", new ArrayList<>());
        Visit visit = new Visit(1L, LocalDateTime.of(2025, 11, 11, 10, 15, 0), LocalDateTime.of(2025, 11, 11, 10, 33, 0), doctor, null);
        when(repository.save(any())).thenReturn(visit);
        //when
        //then
        VisitDateException exception = Assertions.assertThrows(VisitDateException.class,() -> service.createVisit(visit));
        assertEquals("End Date not in quarter of an Hour",exception.getMessage());
    }

    @Test
    void assignPatient_visitAndPatientValid_patientAssigned(){
        //given
        User user = new User(1L, "user", "pass");
        Doctor doctor = new Doctor(1L, "email", user, "fn", "ln", "spec", new ArrayList<>());
        Patient patient = new Patient(123L, "x1", user, "123", "xyz", "xzy", "123", LocalDate.of(1999, 11, 11));
        Visit visit = new Visit(1L, LocalDateTime.of(2025, 11, 11, 10, 15, 0), LocalDateTime.of(2025, 11, 11, 10, 30, 0), doctor, null);
        Visit bookedVisit = new Visit(1L, LocalDateTime.of(2025, 11, 11, 10, 15, 0), LocalDateTime.of(2025, 11, 11, 10, 30, 0), doctor, patient);
        when(repository.findById(any())).thenReturn(Optional.of(visit));
        when(patientRepository.findByEmail(any())).thenReturn(Optional.of(patient));
        when(repository.save(visit)).thenReturn(bookedVisit);
        //when
        Visit result = service.assignPatient(1L,"x1");
        //then
        assertAll(
                () -> assertEquals(1L, result.getId()),
                () -> assertEquals(LocalDateTime.of(2025, 11, 11, 10, 15, 0), result.getVisitStart()),
                () -> assertEquals(LocalDateTime.of(2025, 11, 11, 10, 30, 0), result.getVisitEnd()),
                () -> assertEquals(1L, result.getDoctor().getId()),
                () -> assertEquals("email", result.getDoctor().getEmail()),
                () -> assertEquals("fn", result.getDoctor().getFirstName()),
                () -> assertEquals("ln", result.getDoctor().getLastName()),
                () -> assertEquals("spec", result.getDoctor().getSpecialization()),
                () -> assertEquals(0, result.getDoctor().getInstitutions().size()),
                () -> assertEquals("user", result.getDoctor().getUser().getUsername()),
                () -> assertEquals("pass", result.getDoctor().getUser().getPassword()),
                () -> assertEquals(123L, result.getPatient().getId())
        );
    }

}
