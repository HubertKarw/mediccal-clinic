package com.HubertKarw.medical_clinic.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private String username;
    private String password;

    public User update(User newUser) {
        this.setUsername(newUser.getUsername());
        this.setPassword(newUser.getPassword());
        return this;
    }
}
