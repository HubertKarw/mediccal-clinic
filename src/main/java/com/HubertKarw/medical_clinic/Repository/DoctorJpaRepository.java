package com.HubertKarw.medical_clinic.Repository;

import com.HubertKarw.medical_clinic.Model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorJpaRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByEmail(String email);
}
