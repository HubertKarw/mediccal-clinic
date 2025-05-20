package com.HubertKarw.medical_clinic.Controller;

import com.HubertKarw.medical_clinic.Model.*;
import com.HubertKarw.medical_clinic.Service.DoctorJpaService;
import com.HubertKarw.medical_clinic.Service.PatientJpaService;
import com.HubertKarw.medical_clinic.Service.VisitJpaService;
import com.HubertKarw.medical_clinic.mappers.VisitStructMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/visit")
public class VisitController {
    private final VisitJpaService service;
    private final DoctorJpaService doctorService;
    private final PatientJpaService patientService;
    private final VisitStructMapper mapper;


    @GetMapping
    public List<VisitDTO> getVisits(Pageable pageable){
        return service.getVisits(pageable).stream().map(mapper::mapToDTO).toList();
    }

    @GetMapping("/patient/{email}")
    public List<VisitDTO> getVisitsByPatient(@PathVariable("email") String email, Pageable pageable){
        return service.getVisitsByPatient(pageable, email).stream().map(mapper::mapToDTO).toList();
    }

    @GetMapping("/doctor/{email}")
    public List<VisitDTO> getVisitsByDoctor(@PathVariable("email") String email, Pageable pageable){
        return service.getVisitsByDoctor(pageable, email).stream().map(mapper::mapToDTO).toList();
    }

    @GetMapping("/{id}")
    public VisitDTO getVisit(@PathVariable long id){
        return mapper.mapToDTO(service.getVisit(id));
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public VisitDTO createVisit(@RequestBody CreateVisitCommand command){
//        return mapper.mapToDTO(service.createVisit(mapper.mapToVisit(command)));
//    }
    @PostMapping("/doctor/{docEmail}/patient/{patEmail}")
    @ResponseStatus(HttpStatus.CREATED)
    public VisitDTO createVisit(@RequestBody CreateVisitCommand command, @PathVariable("docEmail") String doctorEmail, @PathVariable("patEmail") String patientEmail){
        System.out.println("IN POST");
        Visit visit = mapper.mapToVisit(command);
        visit.setDoctor(doctorService.getDoctor(doctorEmail));
        visit.setPatient(patientService.getPatient(patientEmail));
        return mapper.mapToDTO(service.createVisit(visit));
    }

    @DeleteMapping("/{id}")
    public void removeVisit(@PathVariable("id") long id){
        service.removeVisit(id);
    }

    @PutMapping("/{id}")
    public VisitDTO modifyVisit(@PathVariable("id") long id, @RequestBody CreateVisitCommand command){
        return mapper.mapToDTO(service.modifyVisit(id, mapper.mapToVisit(command)));
    }
}
