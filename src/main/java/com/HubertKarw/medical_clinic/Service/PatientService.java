package com.HubertKarw.medical_clinic.Service;

import com.HubertKarw.medical_clinic.Model.Patient;
import com.HubertKarw.medical_clinic.Repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public List<Patient> viewPatients(){
        return patientRepository.viewPatients();
    }

    public Patient getPatient(String email){
        return patientRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("User with tis eMail address does not exist"));
    }

    public Patient addPatient (Patient patient){
        return patientRepository.addPatient(patient);
    }

    public void removePatient(String email){
        patientRepository.removePatient(email);
    }

    public Patient modifyPatient(String email, Patient patient){
        return patientRepository.modifyPatient(email,patient);
    }
}
