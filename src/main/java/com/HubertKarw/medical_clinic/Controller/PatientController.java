package com.HubertKarw.medical_clinic.Controller;

import com.HubertKarw.medical_clinic.Model.CreatePatientCommand;
import com.HubertKarw.medical_clinic.Model.Password;
import com.HubertKarw.medical_clinic.Model.Patient;
import com.HubertKarw.medical_clinic.Model.PatientDTO;
import com.HubertKarw.medical_clinic.Service.PatientMapper;
import com.HubertKarw.medical_clinic.Service.PatientService;
import com.HubertKarw.medical_clinic.Service.PatientStructMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;
    private final PatientStructMapper mapper;
    @GetMapping
    public List<PatientDTO> getPatients() {
        return patientService.getPatients().stream()
                .map(mapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{email}")
    public PatientDTO getPatient(@PathVariable("email") String email) {
        return mapper.mapToDTO(patientService.getPatient(email));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientDTO addPatient(@RequestBody CreatePatientCommand command) {
        return mapper.mapToDTO(patientService.addPatient(mapper.mapToPatient(command)));
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePatient(@PathVariable("email") String email) {
        patientService.removePatient(email);
    }

    @PutMapping("/{email}")
    public PatientDTO modifyPatient(@PathVariable("email") String email, @RequestBody CreatePatientCommand patient) {
        return mapper.mapToDTO(patientService.modifyPatient(email, mapper.mapToPatient(patient)));
    }

    @PatchMapping("/{email}")
    public PatientDTO setPassword(@PathVariable("email") String email, @RequestBody Password password) {
        return mapper.mapToDTO(patientService.setPassword(email, password));
    }
}
