package com.HubertKarw.medical_clinic.Service;

import com.HubertKarw.medical_clinic.Exception.PatientNotFoundException;
import com.HubertKarw.medical_clinic.Model.Patient;
import com.HubertKarw.medical_clinic.Repository.PatientJpaRepository;
import com.HubertKarw.medical_clinic.Validation.PatientJPAValidator;
import com.HubertKarw.medical_clinic.Validation.PatientValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientJpaService {
    private final PatientJpaRepository repository;

    public List<Patient> getPatients(){
        return repository.findAll();
    }
    public Patient getPatient(String email){
        return repository.findByEmail(email)
                .orElseThrow(()->new PatientNotFoundException("Patient with this email does not exist"));
    }
    public Patient addPatient(Patient patient){
        PatientJPAValidator.validatePatientCreation(patient);
        PatientJPAValidator.validateAddingPatient(repository, patient.getEmail());
        return repository.save(patient);
    }
    public void removePatient(String email){
        Patient patient = repository.findByEmail(email)
                .orElseThrow(()-> new PatientNotFoundException("no patient with this email"));
        repository.delete(patient);
    }
    public Patient modifyPatient(String email, Patient newPatient){
        Patient patient = repository.findByEmail(email)
                .orElseThrow(()->new PatientNotFoundException("no patient with this email"));
        PatientJPAValidator.validatePatientCreation(newPatient);
        PatientJPAValidator.validatePatientUpdate(patient, newPatient);
        PatientJPAValidator.validateEmailUpdate(patient, newPatient, repository);
        patient.update(newPatient);
        return repository.save(patient);
    }
}
