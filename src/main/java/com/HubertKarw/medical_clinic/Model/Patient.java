package com.HubertKarw.medical_clinic.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @Column(name = "idcard")
    private String idCardNo;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Temporal(TemporalType.DATE)
    private LocalDate birthday;

    public Patient(String email, User user, String idCardNo, String firstName, String lastName, String phoneNumber, LocalDate birthday) {
    }


    public Patient update(Patient anotherPatient) {
        this.setEmail(anotherPatient.getEmail());
        this.setUser(anotherPatient.getUser());
        this.setIdCardNo(anotherPatient.getIdCardNo());
        this.setFirstName(anotherPatient.getFirstName());
        this.setLastName(anotherPatient.getLastName());
        this.setPhoneNumber(anotherPatient.getPhoneNumber());
        this.setBirthday(anotherPatient.getBirthday());
        return this;
    }

    public void setPassword(String password) {
        this.getUser().setPassword(password);
    }
}
