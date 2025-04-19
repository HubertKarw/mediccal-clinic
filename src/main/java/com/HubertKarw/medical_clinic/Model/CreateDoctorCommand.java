package com.HubertKarw.medical_clinic.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CreateDoctorCommand {
    private Long id;
    private String email;
    private User user;
    private String firstName;
    private String lastName;
    private String specialization;
    private List<Institution> institutions;
}
