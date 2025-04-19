package com.HubertKarw.medical_clinic.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class DoctorDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String specialization;
    private List<Institution> institutions;
}
