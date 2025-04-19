package com.HubertKarw.medical_clinic.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "institution")
public class Institution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String location;
    private String postalCode;
    private String streetAddress;
    private int streetNumber;
//    @ManyToMany(mappedBy = "institutions")
//    private List<Doctor> doctors;

    public void update(Institution newInstitution) {
        this.setName(newInstitution.getName());
        this.setLocation(newInstitution.getLocation());
        this.setPostalCode(newInstitution.getPostalCode());
        this.setStreetAddress(newInstitution.getStreetAddress());
        this.setStreetNumber(newInstitution.getStreetNumber());
//        this.setDoctors(newInstitution.getDoctors());
    }
}
