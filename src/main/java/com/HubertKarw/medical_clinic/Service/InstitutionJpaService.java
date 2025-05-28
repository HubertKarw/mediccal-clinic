package com.HubertKarw.medical_clinic.Service;

import com.HubertKarw.medical_clinic.Exception.MedicalClinicException;
import com.HubertKarw.medical_clinic.Model.Institution;
import com.HubertKarw.medical_clinic.Repository.InstitutionJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstitutionJpaService {
    private final InstitutionJpaRepository repository;

    public List<Institution> getInstitutions(Pageable pageable) {
        Page<Institution> institutions = repository.findAll(pageable);
        return institutions.getContent();
    }

    public Institution getInstitution(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new MedicalClinicException("INSTITUTION NOT FOUND", HttpStatus.NOT_FOUND));
    }

    @Transactional
    public Institution addInstitution(Institution institution) {
        return repository.save(institution);
    }

    @Transactional
    public void removeInstitution(String name) {
        Institution institution = repository.findByName(name)
                .orElseThrow(() -> new MedicalClinicException("INSTITUTION NOT FOUND", HttpStatus.NOT_FOUND));
        repository.delete(institution);
    }

    @Transactional
    public Institution modifyInstitution(String name, Institution newInstitution) {
        Institution institution = repository.findByName(name)
                .orElseThrow(() -> new MedicalClinicException("INSTITUTION NOT FOUND", HttpStatus.NOT_FOUND));
        institution.update(newInstitution);
        return repository.save(institution);
    }
}
