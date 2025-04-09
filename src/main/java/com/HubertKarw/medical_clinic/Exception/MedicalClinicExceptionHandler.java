package com.HubertKarw.medical_clinic.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MedicalClinicExceptionHandler {
    @ExceptionHandler(PatientNotFoundException.class)
    ResponseEntity<String> handlePatientNotFound(PatientNotFoundException pnfe){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pnfe.getMessage());
    }
    @ExceptionHandler(PatientCreationException.class)
    ResponseEntity<String> handlePatientCreationException(PatientCreationException pce){
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(pce.getMessage());//400 request
    }
    @ExceptionHandler(Exception.class)
    ResponseEntity<String> handleExceptions(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unknown Error");
    }
}
