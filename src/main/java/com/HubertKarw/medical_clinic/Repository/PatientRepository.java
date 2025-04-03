package com.HubertKarw.medical_clinic.Repository;

import com.HubertKarw.medical_clinic.Model.Patient;
import com.HubertKarw.medical_clinic.Validation.PatientValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PatientRepository {

    private final List<Patient> patients;

    public List<Patient> getPatients() {
        return new ArrayList<>(patients);
    }

    public Optional<Patient> findByEmail(String email) {
        return patients.stream()
                .filter(patient -> patient.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public Patient addPatient(Patient patient) {
        PatientValidator.validatePatientCreation(patient);
        PatientValidator.validateAddingPatient(new ArrayList<>(patients), patient.getEmail());
        patients.add(patient);
        return patient;
    }

    public void removePatient(String email) {
        Patient patient = patients.stream()
                .filter(patient1 -> patient1.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User with tis eMail address does not exist"));
        patients.remove(patient);
    }

    public Patient modifyPatient(String email, Patient newPatient) {
        Patient patient = patients.stream()
                .filter(pat -> pat.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User with tis eMail address does not exist"));
        PatientValidator.validatePatientCreation(patient);
        PatientValidator.validatePatientUpdate(patient, newPatient);
        return patient.update(newPatient);
    }
}
