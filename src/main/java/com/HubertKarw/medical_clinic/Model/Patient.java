package com.HubertKarw.medical_clinic.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Patient {
    private String email;
    private String password;
    private String idCardNo;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthday;

    public Patient update(Patient anotherPatient) {
        this.setEmail(anotherPatient.getEmail());
        this.setPassword(anotherPatient.getPassword());
        this.setIdCardNo(anotherPatient.getIdCardNo());
        this.setFirstName(anotherPatient.getFirstName());
        this.setLastName(anotherPatient.getLastName());
        this.setPhoneNumber(anotherPatient.getPhoneNumber());
        this.setBirthday(anotherPatient.getBirthday());
        return this;
    }
}
