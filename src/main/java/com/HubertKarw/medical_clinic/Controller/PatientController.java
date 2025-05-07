package com.HubertKarw.medical_clinic.Controller;

import com.HubertKarw.medical_clinic.Model.Patient;
import com.HubertKarw.medical_clinic.Service.PatientService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public List<Patient> getPatients() {
        return patientService.getPatients();
    }

    @GetMapping("/{email}")
    public Patient getPatient(@PathVariable("email") String email) {
        return patientService.getPatient(email);
    }

    @PostMapping
    public Patient addPatient(@RequestBody Patient patient) {
        return patientService.addPatient(patient);
    }

    @DeleteMapping("/{email}")
    public void removePatient(@PathVariable("email") String email) {
        patientService.removePatient(email);
    }

    @PutMapping("/{email}")
    public Patient modifyPatient(@PathVariable("email") String email, @RequestBody Patient patient) {
        return patientService.modifyPatient(email, patient);
    }
}
