package com.HubertKarw.medical_clinic.Repository;

import com.HubertKarw.medical_clinic.Model.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientJpaRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);
}
