package com.HubertKarw.medical_clinic.Validation;

import com.HubertKarw.medical_clinic.Exception.PatientCreationException;
import com.HubertKarw.medical_clinic.Model.Patient;
import com.HubertKarw.medical_clinic.Repository.PatientRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PatientValidator {
    public static void validatePatientCreation(Patient patient) {
        if (patient.getEmail() == null || patient.getPassword() == null || patient.getIdCardNo() == null || patient.getFirstName() == null || patient.getLastName() == null || patient.getPhoneNumber() == null || patient.getBirthday() == null) {
            throw new PatientCreationException("Patient has uninitialized fields");
        }
    }

    public static void validatePatientUpdate(Patient patient, Patient anotherPatient) {
        if (!patient.getIdCardNo().equals(anotherPatient.getIdCardNo())) {
            throw new PatientCreationException("Patient cannot change idCardNo");
        }
    }

    public static void validateEmailUpdate(Patient patient, Patient anotherPatient, PatientRepository repository) {
        if (!patient.getEmail().equals(anotherPatient.getEmail())) {
            List<Patient> otherPatients = repository.getPatients();
            otherPatients.remove(patient);
            if (otherPatients.stream()
                    .map(Patient::getEmail)
                    .anyMatch(patientEmail -> patientEmail.equals(anotherPatient.getEmail()))) {
                throw new PatientCreationException("Cannot add patient with this email");
            }
        }
    }


    public static void validateAddingPatient(PatientRepository repository, String email) {
        List<Patient> patients = repository.getPatients();

        if (!patients.isEmpty()) {
            if (patients.stream()
                    .map(Patient::getEmail)
                    .anyMatch(patientEmail -> patientEmail.equals(email))) {
                throw new PatientCreationException("Cannot add patient with this email");
            }
        }
    }
}
