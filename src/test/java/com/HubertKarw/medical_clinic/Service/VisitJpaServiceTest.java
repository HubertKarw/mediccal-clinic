package com.HubertKarw.medical_clinic.Service;

import com.HubertKarw.medical_clinic.Model.Doctor;
import com.HubertKarw.medical_clinic.Model.User;
import com.HubertKarw.medical_clinic.Model.Visit;
import com.HubertKarw.medical_clinic.Repository.DoctorJpaRepository;
import com.HubertKarw.medical_clinic.Repository.PatientJpaRepository;
import com.HubertKarw.medical_clinic.Repository.VisitJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
    void getVisits_visitsExist_visitsReturned(){
        User user = new User(1L,"user","pass");
        Doctor doctor = new Doctor(1L,"email",user,"fn","ln","spec",new ArrayList<>());
        Visit visit1 = new Visit(1L, LocalDateTime.of(2025,11,11,10,15, 0),LocalDateTime.of(2025,11,11,10,30, 0),doctor,null);
        Visit visit2 = new Visit(2L, LocalDateTime.of(2025,11,12,10,15, 0),LocalDateTime.of(2025,11,12,10,30, 0),doctor,null);
        Visit visit3 = new Visit(3L, LocalDateTime.of(2025,11,13,10,15, 0),LocalDateTime.of(2025,11,13,10,30, 0),doctor,null);
        PageImpl<Visit> page = new PageImpl<>(List.of(visit1,visit2,visit3));
        when(repository.findAll(any(Pageable.class))).thenReturn(page);
        //when
        List<Visit> result = service.getVisits(PageRequest.of(0,3));
        //then
        assertAll(
                () -> assertEquals(3,result.size()),
                () -> assertEquals(1L,result.get(0).getId()),
                () -> assertEquals(LocalDateTime.of(2025,11,11,10,15, 0),result.get(0).getVisitStart()),
                () -> assertEquals(LocalDateTime.of(2025,11,11,10,30, 0),result.get(0).getVisitEnd()),
                () -> assertEquals(1L,result.get(0).getDoctor().getId()),
                () -> assertNull(result.get(0).getPatient())
        );
    }

    @Test
    void getVisit_VisitExists_visitReturned(){
        //given
        User user = new User(1L,"user","pass");
        Doctor doctor = new Doctor(1L,"email",user,"fn","ln","spec",new ArrayList<>());
        Visit visit1 = new Visit(1L, LocalDateTime.of(2025,11,11,10,15, 0),LocalDateTime.of(2025,11,11,10,30, 0),doctor,null);
        when(repository.findById(1L)).thenReturn(Optional.of(visit1))
        //when
        Visit visit = service.getVisit(1L);
        //then
    }
}
