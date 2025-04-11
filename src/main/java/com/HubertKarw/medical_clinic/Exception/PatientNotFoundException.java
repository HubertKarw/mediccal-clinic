package com.HubertKarw.medical_clinic.Exception;

public class PatientNotFoundException extends RuntimeException{
    public PatientNotFoundException(String message){
        super(message);
    }
}
