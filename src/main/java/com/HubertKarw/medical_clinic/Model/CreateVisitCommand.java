package com.HubertKarw.medical_clinic.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CreateVisitCommand {
    private LocalDateTime visitStart;
    private LocalDateTime visitEnd;
//    private Doctor doctor;
//    private Patient patient;
}
