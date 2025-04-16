package com.HubertKarw.medical_clinic.Validation;

import com.HubertKarw.medical_clinic.Exception.PatientCreationException;
import com.HubertKarw.medical_clinic.Model.Patient;
import com.HubertKarw.medical_clinic.Repository.PatientJpaRepository;
import com.HubertKarw.medical_clinic.Repository.PatientRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PatientJPAValidator {
    public static void validatePatientCreation(Patient patient) {
        if (patient.getEmail() == null || patient.getUser() == null || patient.getIdCardNo() == null || patient.getFirstName() == null || patient.getLastName() == null || patient.getPhoneNumber() == null || patient.getBirthday() == null) {
            throw new PatientCreationException("Patient has uninitialized fields", HttpStatus.BAD_REQUEST);
        }
    }

    public static void validatePatientUpdate(Patient patient, Patient anotherPatient) {
        if (!patient.getIdCardNo().equals(anotherPatient.getIdCardNo())) {
            throw new PatientCreationException("Patient cannot change idCardNo", HttpStatus.BAD_REQUEST);
        }
    }

    public static void validateEmailUpdate(Patient patient, Patient anotherPatient, PatientJpaRepository repository) {
        if (!patient.getEmail().equals(anotherPatient.getEmail())) {
            List<Patient> otherPatients = repository.findAll();
            otherPatients.remove(patient);
            if (otherPatients.stream()
                    .map(Patient::getEmail)
                    .anyMatch(patientEmail -> patientEmail.equals(anotherPatient.getEmail()))) {
                throw new PatientCreationException("Cannot add patient with this email", HttpStatus.BAD_REQUEST);
            }
        }
    }


    public static void validateAddingPatient(PatientJpaRepository repository, String email) {
        List<Patient> patients = repository.findAll();

        if (!patients.isEmpty()) {
            Optional<Patient> optionalPatient = repository.findByEmail(email);
            if (optionalPatient.isPresent()){
                throw new PatientCreationException("Cannot add patient with this email", HttpStatus.BAD_REQUEST);
            }
        }
    }
}
