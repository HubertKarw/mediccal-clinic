package com.HubertKarw.medical_clinic.Exception;

import com.HubertKarw.medical_clinic.Model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MedicalClinicExceptionHandler {
    @ExceptionHandler(MedicalClinicException.class)
    ResponseEntity<ErrorMessage> handlePatientNotFound(MedicalClinicException exception) {
        return ResponseEntity.status(exception.getStatus()).body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorMessage> handleExceptions(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("unknown Error"));
    }
}
