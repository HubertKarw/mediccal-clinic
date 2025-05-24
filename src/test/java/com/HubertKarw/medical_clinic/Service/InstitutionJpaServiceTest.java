package com.HubertKarw.medical_clinic.Service;

import com.HubertKarw.medical_clinic.Exception.MedicalClinicException;
import com.HubertKarw.medical_clinic.Model.Institution;
import com.HubertKarw.medical_clinic.Repository.InstitutionJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

public class InstitutionJpaServiceTest {
    InstitutionJpaRepository institutionJpaRepository;
    InstitutionJpaService institutionJpaService;

    @BeforeEach
    void setup() {
        this.institutionJpaRepository = Mockito.mock(InstitutionJpaRepository.class);
        this.institutionJpaService = new InstitutionJpaService(institutionJpaRepository);
    }

    @Test
    void getInstitutions_institutionsExists_institutionsReturned() {
        //given
        Institution inst1 = new Institution(1L, "n", "l", "pc", "sa", 1);
        Institution inst2 = new Institution(2L, "n", "l", "pc", "sa", 1);
        Institution inst3 = new Institution(3L, "n", "l", "pc", "sa", 1);
        PageImpl<Institution> page = new PageImpl<>(List.of(inst1, inst2, inst3));
        when(institutionJpaRepository.findAll(any(Pageable.class))).thenReturn(page);
        //when
        List<Institution> result = institutionJpaService.getInstitutions(PageRequest.of(0, 3));
        //then
        assertAll(
                () -> assertEquals(3, result.size()),
                () -> assertEquals(1L, result.get(0).getId()),
                () -> assertEquals(2L, result.get(1).getId()),
                () -> assertEquals(3L, result.get(2).getId()),
                () -> assertEquals("n", result.get(0).getName())
        );
    }

    @Test
    void getInstitution_institutionExists_institutionReturned() {
        //given
        Institution inst = new Institution(1L, "n", "l", "pc", "sa", 1);
        when(institutionJpaRepository.findByName("n")).thenReturn(Optional.of(inst));
        //when
        Institution result = institutionJpaService.getInstitution("n");
        //then
        assertEquals(result.getId(), inst.getId());
    }

    @Test
    void getInstitution_InstitutionDoesNotExist_throwException() {
        //given
        when(institutionJpaRepository.findByName("n")).thenReturn(Optional.empty());
        //when
        //then
        MedicalClinicException exception = assertThrows(MedicalClinicException.class, () -> institutionJpaService.getInstitution("n"));
        assertEquals("INSTITUTION NOT FOUND", exception.getMessage());
    }

    @Test
    void addInstitution_institutionValid_institutionAdded() {
        //given
        Institution inst = new Institution(1L, "n", "l", "pc", "sa", 1);
        when(institutionJpaRepository.save(any())).thenReturn(inst);
        //when
        Institution result = institutionJpaService.addInstitution(inst);
        //then
        assertEquals(inst.getId(), result.getId());
        verify(institutionJpaRepository).save(inst);
    }

    @Test
    void removeInstitution_institutionExists_institutionRemoved() {
        //given
        Institution inst = new Institution(1L, "n", "l", "pc", "sa", 1);
        when(institutionJpaRepository.findByName("n")).thenReturn(Optional.of(inst));
        //when
        institutionJpaService.removeInstitution("n");
        //then
        verify(institutionJpaRepository).delete(inst);
    }

    @Test
    void removeInstitution_InstitutionDoesNotExist_throwException() {
        //given
        when(institutionJpaRepository.findByName("n")).thenReturn(Optional.empty());
        //when
        //then
        MedicalClinicException exception = assertThrows(MedicalClinicException.class, () -> institutionJpaService.removeInstitution("n"));
        assertEquals("INSTITUTION NOT FOUND", exception.getMessage());
    }

    @Test
    void modifyInstitution_institutionExists_institutionModified() {
        //given
        Institution inst = new Institution(1L, "n", "l", "pc", "sa", 1);
        Institution instModified = new Institution(1L, "name", "loc", "pc", "sa", 1);
        when(institutionJpaRepository.findByName("n")).thenReturn(Optional.of(inst));
        //when
        Institution result = institutionJpaService.modifyInstitution("n", instModified);
        //then
        assertEquals(instModified.getId(), inst.getId());
        assertEquals(instModified.getName(), inst.getName());
        assertEquals(instModified.getLocation(), inst.getLocation());
        verify(institutionJpaRepository).save(inst);
    }

    @Test
    void modifyInstitution_InstitutionDoesNotExist_throwException() {
        //given
        Institution inst = new Institution(1L, "n", "l", "pc", "sa", 1);
        when(institutionJpaRepository.findByName("n")).thenReturn(Optional.empty());
        //when
        //then
        MedicalClinicException exception = assertThrows(MedicalClinicException.class, () -> institutionJpaService.modifyInstitution("n", inst));
        assertEquals("INSTITUTION NOT FOUND", exception.getMessage());
    }
}
