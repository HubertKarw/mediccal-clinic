package com.HubertKarw.medical_clinic.Exception;

import org.springframework.http.HttpStatus;

public class PatientCreationException extends MedicalClinicException {
    public PatientCreationException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
