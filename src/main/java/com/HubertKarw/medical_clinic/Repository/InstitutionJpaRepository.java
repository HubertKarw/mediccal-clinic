package com.HubertKarw.medical_clinic.Repository;

import com.HubertKarw.medical_clinic.Model.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstitutionJpaRepository extends JpaRepository<Institution, Long> {
    Optional<Institution> findByName(String name);
}
