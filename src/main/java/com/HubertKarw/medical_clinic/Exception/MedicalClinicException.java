package com.HubertKarw.medical_clinic.Exception;

import org.springframework.http.HttpStatus;

public class MedicalClinicException extends RuntimeException{

    private HttpStatus status;

    public MedicalClinicException(String message){
        super(message);
    }
}
