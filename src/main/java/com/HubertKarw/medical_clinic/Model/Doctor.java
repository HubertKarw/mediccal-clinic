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
@Table(name = "Doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private String firstName;
    private String lastName;
    private String specialization;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Doctor_Institution", joinColumns = {@JoinColumn(name = "doctor_id")}, inverseJoinColumns = {@JoinColumn(name = "institution_id")})
    private List<Institution> institutions;

    public void update(Doctor newDoctor) {
        this.setEmail(newDoctor.getEmail());
        this.setUser(newDoctor.getUser());
        this.setFirstName(newDoctor.getFirstName());
        this.setLastName(newDoctor.getLastName());
        this.setSpecialization(newDoctor.getSpecialization());
        this.setInstitutions(newDoctor.getInstitutions());
    }

    public Doctor addInstitution(Institution institution) {
        this.institutions.add(institution);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Doctor))
            return false;

        Doctor other = (Doctor) o;

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
