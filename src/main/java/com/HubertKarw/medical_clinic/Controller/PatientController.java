package com.HubertKarw.medical_clinic.Controller;

import com.HubertKarw.medical_clinic.Model.CreatePatientCommand;
import com.HubertKarw.medical_clinic.Model.Password;
import com.HubertKarw.medical_clinic.Model.Patient;
import com.HubertKarw.medical_clinic.Model.PatientDTO;
import com.HubertKarw.medical_clinic.Service.PatientMapper;
import com.HubertKarw.medical_clinic.Service.PatientService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public List<PatientDTO> getPatients() {
        return patientService.getPatients().stream()
                .map(PatientMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{email}")
    public PatientDTO getPatient(@PathVariable("email") String email) {
        return PatientMapper.toDTO(patientService.getPatient(email));
    }

    @PostMapping
    public PatientDTO addPatient(@RequestBody CreatePatientCommand command) {
        return PatientMapper.toDTO(patientService.addPatient(PatientMapper.toPatient(command)));
    }

    @DeleteMapping("/{email}")
    public void removePatient(@PathVariable("email") String email) {
        patientService.removePatient(email);
    }

    @PutMapping("/{email}")
    public PatientDTO modifyPatient(@PathVariable("email") String email, @RequestBody CreatePatientCommand patient) {
        return PatientMapper.toDTO(patientService.modifyPatient(email, PatientMapper.toPatient(patient)));
    }

    @PatchMapping("/{email}")
    public PatientDTO setPassword(@PathVariable("email") String email, @RequestBody Password password) {
        return PatientMapper.toDTO(patientService.setPassword(email, password));
    }

}
