package com.HubertKarw.medical_clinic.Service;

import com.HubertKarw.medical_clinic.Exception.PatientCreationException;
import com.HubertKarw.medical_clinic.Exception.PatientNotFoundException;
import com.HubertKarw.medical_clinic.Model.Password;
import com.HubertKarw.medical_clinic.Model.Patient;
import com.HubertKarw.medical_clinic.Repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public List<Patient> getPatients() {
        return patientRepository.getPatients();
    }

    public Patient getPatient(String email) {
        return patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("User with tis eMail address does not exist"));
    }

    public Patient addPatient(Patient patient) {
        return patientRepository.addPatient(patient);
    }

    public void removePatient(String email) {
        patientRepository.removePatient(email);
    }

    public Patient modifyPatient(String email, Patient patient) {
        return patientRepository.modifyPatient(email, patient);
    }

    public Patient setPassword(String email, Password password) {
        return patientRepository.setPassword(email, password);
    }
}
