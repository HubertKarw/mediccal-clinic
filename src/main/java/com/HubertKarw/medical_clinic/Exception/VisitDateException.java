package com.HubertKarw.medical_clinic.Exception;

import org.springframework.http.HttpStatus;

public class VisitDateException extends MedicalClinicException{
    public VisitDateException(String message){
        super(message, HttpStatus.BAD_REQUEST);
    }
}
