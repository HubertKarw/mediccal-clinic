package com.HubertKarw.medical_clinic.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;


@Data
@AllArgsConstructor
public class PatientDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthday;

}
