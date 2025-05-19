package com.HubertKarw.medical_clinic.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "VISIT")
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime visitStart;
    private LocalDateTime visitEnd;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "docctor_id", referencedColumnName = "id")
    private Doctor doctor;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    public Boolean isValidVisit(){
        return visitStart.getMinute() % 15 == 0;
    }

}
