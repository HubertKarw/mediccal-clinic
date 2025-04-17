package com.HubertKarw.medical_clinic.Service;

import com.HubertKarw.medical_clinic.Model.CreatePatientCommand;
import com.HubertKarw.medical_clinic.Model.Patient;
import com.HubertKarw.medical_clinic.Model.PatientDTO;

public class PatientMapper {
    public static PatientDTO toDTO(Patient patient) {
        return new PatientDTO(patient.getEmail(), patient.getFirstName(),
                patient.getLastName(), patient.getPhoneNumber(), patient.getBirthday());

    }

    public static Patient toPatient(CreatePatientCommand command) {
        return new Patient(command.getEmail(), command.getUser(), command.getIdCardNo(), command.getFirstName(), command.getLastName(), command.getPhoneNumber(), command.getBirthday());
    }
}
