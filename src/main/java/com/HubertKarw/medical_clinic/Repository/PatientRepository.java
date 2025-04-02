package com.HubertKarw.medical_clinic.Repository;

import com.HubertKarw.medical_clinic.Model.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PatientRepository {

    private final List<Patient> patients;

    public List<Patient> viewPatients(){
        return patients;
    }

    public Optional<Patient> findByEmail(String email){
        return patients.stream()
                .filter(patient -> patient.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public Patient addPatient(Patient patient){
        patients.add(patient);
        return patient;
    }
    public void removePatient(String email){
        Patient p = patients.stream()
                .filter(patient -> patient.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("User with tis eMail address does not exist"));
        patients.remove(p);
    }
    public Patient modifyPatient(String email, Patient patient){
        Patient p = patients.stream()
                .filter(pat -> pat.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("User with tis eMail address does not exist"));
        patients.remove(p);
        patients.add(patient);
        return p;

    }
}
