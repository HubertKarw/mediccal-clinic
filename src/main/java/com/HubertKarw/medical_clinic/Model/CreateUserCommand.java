package com.HubertKarw.medical_clinic.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserCommand {
    private String username;
    private String password;
}
