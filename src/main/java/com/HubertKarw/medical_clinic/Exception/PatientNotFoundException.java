package com.HubertKarw.medical_clinic.Exception;

import org.springframework.http.HttpStatus;

public class PatientNotFoundException extends MedicalClinicException{
    public PatientNotFoundException(String message){
        super(message, HttpStatus.NOT_FOUND);
    }
}
