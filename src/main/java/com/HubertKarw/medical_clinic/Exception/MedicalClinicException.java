package com.HubertKarw.medical_clinic.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class MedicalClinicException extends RuntimeException{

    private HttpStatus status;

    public MedicalClinicException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }
}
