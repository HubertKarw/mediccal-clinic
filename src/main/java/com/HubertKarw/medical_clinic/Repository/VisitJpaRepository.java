package com.HubertKarw.medical_clinic.Repository;

import com.HubertKarw.medical_clinic.Model.Doctor;
import com.HubertKarw.medical_clinic.Model.Patient;
import com.HubertKarw.medical_clinic.Model.Visit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VisitJpaRepository extends JpaRepository<Visit, Long> {
    Page<Visit> findByDoctor(Doctor doctor, Pageable pageable);
    Page<Visit> findByPatient(Patient patient, Pageable pageable);
}
