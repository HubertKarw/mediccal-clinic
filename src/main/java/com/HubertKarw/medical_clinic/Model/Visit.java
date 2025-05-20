package com.HubertKarw.medical_clinic.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @Column(name = "VISIT_START")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime visitStart;
    @Column(name = "VISIT_END")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime visitEnd;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private Doctor doctor;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    public Visit update(Visit newVisit) {
//        this.setDoctor( newVisit.getDoctor());
        this.setVisitStart( newVisit.getVisitStart());
        this.setVisitEnd( newVisit.getVisitEnd());
//        this.setPatient( newVisit.getPatient());
        return this;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;

        if (!(o instanceof Visit)) return false;

        Visit other = (Visit) o;

        return id!= null && id.equals(other.getId());
    }

    public int hashCode(){
        return getClass().hashCode();
    }
}
