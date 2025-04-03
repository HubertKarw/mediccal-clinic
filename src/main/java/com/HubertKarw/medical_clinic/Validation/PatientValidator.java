package com.HubertKarw.medical_clinic.Validation;

import com.HubertKarw.medical_clinic.Model.Patient;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PatientValidator {
    public static void validatePatientCreation(Patient patient) {
        if (patient.getEmail() == null || patient.getPassword() == null
                || patient.getIdCardNo() == null || patient.getFirstName() == null
                || patient.getLastName() == null || patient.getPhoneNumber() == null
                || patient.getBirthday() == null) {
            throw new IllegalArgumentException("Patient has uninitialized fields");
        }
    }

    public static void validatePatientUpdate(Patient patient, Patient anotherPatient) {
        if (!patient.getIdCardNo().equals(anotherPatient.getIdCardNo())) {
            throw new IllegalArgumentException("Patient cannot change idCardNo");
        }
    }
    public static void validateAddingPatient(List<Patient> patients, String email){
        if(!patients.isEmpty()){
            if(patients.stream().map(Patient::getEmail).anyMatch(patientEmail -> patientEmail.equals(email))){
                throw new IllegalArgumentException("Cannot add patient with this email");
            }
        }
    }
}
