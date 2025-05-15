package com.HubertKarw.medical_clinic.Service;

import com.HubertKarw.medical_clinic.Exception.MedicalClinicException;
import com.HubertKarw.medical_clinic.Model.Doctor;
import com.HubertKarw.medical_clinic.Model.Institution;
import com.HubertKarw.medical_clinic.Model.User;
import com.HubertKarw.medical_clinic.Repository.DoctorJpaRepository;
import com.HubertKarw.medical_clinic.Repository.InstitutionJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DoctorJpaServiceTest {
    InstitutionJpaRepository institutionJpaRepository;
    DoctorJpaRepository doctorJpaRepository;
    DoctorJpaService doctorJpaService;


    @BeforeEach
    void setup() {
        this.institutionJpaRepository = Mockito.mock(InstitutionJpaRepository.class);
        this.doctorJpaRepository = Mockito.mock(DoctorJpaRepository.class);
        this.doctorJpaService = new DoctorJpaService(doctorJpaRepository, institutionJpaRepository);
    }

    @Test
    void getDoctors_doctorsExist_doctorsReturned() {
        //given
        User user = new User(1L, "123", "321");
        Doctor doc1 = new Doctor(1L, "xyz@xyz.com", user, "xyz", "zyx", "spec", null);
        Doctor doc2 = new Doctor(2L, "xyzz@xyz.com", user, "xyzz", "zyx", "spec", null);
        Doctor doc3 = new Doctor(3L, "xyzx@xyz.com", user, "xyzx", "zyx", "spec", null);
        PageImpl<Doctor> page = new PageImpl<>(List.of(doc1, doc2, doc3));
        when(doctorJpaRepository.findAll(any(Pageable.class))).thenReturn(page);
        //when
        List<Doctor> result = doctorJpaService.getDoctors(PageRequest.of(0, 3));
        //then
        assertAll(
                () -> assertEquals(3, result.size()),
                () -> assertEquals(1L, result.get(0).getId()),
                () -> assertEquals("xyz@xyz.com", result.get(0).getEmail()),
                () -> assertEquals("xyz", result.get(0).getFirstName()),
                () -> assertEquals("zyx", result.get(0).getLastName()),
                () -> assertEquals("spec", result.get(0).getSpecialization()),
                () -> assertNull(result.get(0).getInstitutions()),
                () -> assertEquals(2L, result.get(1).getId()),
                () -> assertEquals(3L, result.get(2).getId())
        );
    }

    @Test
    void getDoctor_doctorExists_doctorReturned() {
        //given
        User user = new User(1L, "123", "321");
        Doctor doc = new Doctor(1L, "xyz@xyz.com", user, "xyz", "zyx", "spec", null);
        when(doctorJpaRepository.findByEmail("xyz@xyz.com")).thenReturn(Optional.of(doc));
        //when
        Doctor result = doctorJpaService.getDoctor("xyz@xyz.com");
        //then
        assertAll(
                () -> assertEquals(result.getId(), doc.getId()),
                () -> assertEquals(result.getEmail(), doc.getEmail()),
                () -> assertEquals(result.getUser(), doc.getUser()),
                () -> assertEquals(result.getFirstName(), doc.getFirstName()),
                () -> assertEquals(result.getLastName(), doc.getLastName())
        );
    }

    @Test
    void getDoctor_doctorDoesNotExists_throwException() {
        //given
        when(doctorJpaRepository.findByEmail("xyz@xyz.com")).thenReturn(Optional.empty());
        //when
        //then
        MedicalClinicException exception = Assertions.assertThrows(MedicalClinicException.class,()->doctorJpaService.getDoctor("xyz@xyz.com"));
        assertEquals("Doctor not found", exception.getMessage());
    }

    @Test
    void addDoctor_doctorExists_DoctorAdded() {
        //given
        User user = new User(1L, "123", "321");
        Doctor doc = new Doctor(1L, "xyz@xyz.com", user, "xyz", "zyx", "spec", null);
        when(doctorJpaRepository.save(any())).thenReturn(doc);
        //when
        Doctor result = doctorJpaService.addDoctor(doc);
        //then
        assertEquals(1L, result.getId());
        verify(doctorJpaRepository).save(doc);
    }

    @Test
    void removeDoctor_doctorExists_doctorRemoved() {
        //given
        User user = new User(1L, "123", "321");
        Doctor doc = new Doctor(1L, "xyz@xyz.com", user, "xyz", "zyx", "spec", null);
        when(doctorJpaRepository.findByEmail("xyz@xyz.com")).thenReturn(Optional.of(doc));
        //when
        doctorJpaService.removeDoctor("xyz@xyz.com");
        //then
        verify(doctorJpaRepository).delete(doc);
    }

    @Test
    void removeDoctor_doctorDoesNotExists_throwException() {
        //given
        when(doctorJpaRepository.findByEmail("xyz@xyz.com")).thenReturn(Optional.empty());
        //when
        //then
        MedicalClinicException exception = Assertions.assertThrows(MedicalClinicException.class,()->doctorJpaService.removeDoctor("xyz@xyz.com"));
        assertEquals("Doctor not found", exception.getMessage());
    }

    @Test
    void modifyDoctor_doctorExists_ModifiedDoctor() {
        //given
        User user = new User(1L, "123", "321");
        Doctor doc = new Doctor(1L, "xyz", user, "xyz", "zyx", "spec", new ArrayList<>());
        Doctor modifiedDoc = new Doctor(1L, "xyzz", user, "xyzz", "zyx", "spec", new ArrayList<>());
        when(doctorJpaRepository.findByEmail("xyz")).thenReturn(Optional.of(doc));
        //when
        doctorJpaService.modifyDoctor("xyz", modifiedDoc);
        //then
        assertEquals(doc.getId(), modifiedDoc.getId());
        assertEquals(doc.getEmail(), modifiedDoc.getEmail());
        assertEquals(doc.getUser(), modifiedDoc.getUser());
        assertEquals(doc.getFirstName(), modifiedDoc.getFirstName());
        assertEquals(doc.getLastName(), modifiedDoc.getLastName());
        verify(doctorJpaRepository).save(doc);
    }
    @Test
    void modifyDoctor_doctorDoesNotExists_throwException() {
        //given
        User user = new User(1L, "123", "321");
        Doctor doc = new Doctor(1L, "xyz", user, "xyz", "zyx", "spec", new ArrayList<>());
        when(doctorJpaRepository.findByEmail("xyz@xyz.com")).thenReturn(Optional.empty());
        //when
        //then
        MedicalClinicException exception = Assertions.assertThrows(MedicalClinicException.class,()->doctorJpaService.modifyDoctor("xyz@xyz.com",doc));
        assertEquals("Doctor not found", exception.getMessage());
    }

    @Test
    void assignToInstitution_doctorAnsInstitutionExists_assignedInstitution() {
        //given
        Institution institution = new Institution(1L, "name", "loc", "11-11", "addr", 1);
        User user = new User(1L, "123", "321");
        Doctor doc = new Doctor(1L, "xyz@xyz.com", user, "xyz", "zyx", "spec", new ArrayList<>());
        when(doctorJpaRepository.findByEmail("xyz@xyz.com")).thenReturn(Optional.of(doc));
        when(institutionJpaRepository.findByName("name")).thenReturn(Optional.of(institution));
        //when
        doctorJpaService.assignToInstitution("xyz@xyz.com", "name");
        //then
        assertEquals(institution, doc.getInstitutions().get(0));
    }
    @Test
    void assignToInstitution_doctorDoesNotExists_throwException() {
        //given
        User user = new User(1L, "123", "321");
        Doctor doc = new Doctor(1L, "xyz", user, "xyz", "zyx", "spec", new ArrayList<>());
        Institution institution = new Institution(1L, "name", "loc", "11-11", "addr", 1);
        when(doctorJpaRepository.findByEmail("xyz")).thenReturn(Optional.empty());
        when(institutionJpaRepository.findByName("name")).thenReturn(Optional.of(institution));
        //when
        //then
        MedicalClinicException exception = Assertions.assertThrows(MedicalClinicException.class,()->doctorJpaService.assignToInstitution("xyz","name"));
        assertEquals("Doctor not found", exception.getMessage());
        assertNotEquals("Institution not found", exception.getMessage());
    }
    @Test
    void assignToInstitution_institutionDoesNotExists_throwException() {
        //given
        User user = new User(1L, "123", "321");
        Doctor doc = new Doctor(1L, "xyz", user, "xyz", "zyx", "spec", new ArrayList<>());
        Institution institution = new Institution(1L, "name", "loc", "11-11", "addr", 1);
        when(doctorJpaRepository.findByEmail("xyz")).thenReturn(Optional.of(doc));
        when(institutionJpaRepository.findByName("name")).thenReturn(Optional.empty());
        //when
        //then
        MedicalClinicException exception = Assertions.assertThrows(MedicalClinicException.class,()->doctorJpaService.assignToInstitution("xyz","name"));
        assertNotEquals("Doctor not found", exception.getMessage());
        assertEquals("Institution not found", exception.getMessage());
    }
}

