package com.HubertKarw.medical_clinic.Model;



import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class VisitDTO {
    private LocalDateTime visitStart;
    private LocalDateTime visitEnd;
    private Doctor doctor;
    private Patient patient;
}
