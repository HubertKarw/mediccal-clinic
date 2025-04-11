package com.HubertKarw.medical_clinic.Exception;

import com.HubertKarw.medical_clinic.Model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MedicalClinicExceptionHandler {
    @ExceptionHandler(PatientNotFoundException.class)
    ResponseEntity<ErrorMessage> handlePatientNotFound(PatientNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(exception.getMessage()));
    }
    @ExceptionHandler(PatientCreationException.class)
    ResponseEntity<ErrorMessage> handlePatientCreationException(PatientCreationException exception){
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new ErrorMessage(exception.getMessage()));//400 request
    }
    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorMessage> handleExceptions(Exception exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("unknown Error"));
    }
}
