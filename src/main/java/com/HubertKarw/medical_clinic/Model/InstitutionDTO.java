package com.HubertKarw.medical_clinic.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class InstitutionDTO {
    private String name;
    private String location;
    private String postalCode;
    private String streetAddress;
    private int streetNumber;
}
