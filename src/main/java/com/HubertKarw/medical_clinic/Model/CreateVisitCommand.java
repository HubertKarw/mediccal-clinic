package com.HubertKarw.medical_clinic.Model;

import java.time.LocalDateTime;

public class CreateVisitCommand {
    private LocalDateTime visitStart;
    private LocalDateTime visitEnd;
    private Doctor doctor;
    private Patient patient;
}
