package com.HubertKarw.medical_clinic.Service;

import com.HubertKarw.medical_clinic.Exception.MedicalClinicException;
import com.HubertKarw.medical_clinic.Model.Doctor;
import com.HubertKarw.medical_clinic.Model.Patient;
import com.HubertKarw.medical_clinic.Model.Visit;
import com.HubertKarw.medical_clinic.Repository.DoctorJpaRepository;
import com.HubertKarw.medical_clinic.Repository.PatientJpaRepository;
import com.HubertKarw.medical_clinic.Repository.VisitJpaRepository;
import com.HubertKarw.medical_clinic.Validation.VisitJpaValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VisitJpaService {
    private final VisitJpaRepository repository;
    private final DoctorJpaRepository doctorRepository;
    private final PatientJpaRepository patientRepository;
    public List<Visit> getVisits(Pageable pageable){
        Page<Visit> visits = repository.findAll(pageable);
        return visits.getContent();
    }

    public List<Visit> getVisitsByDoctor(Pageable pageable, String email){
        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(()->new MedicalClinicException("Doctor not found", HttpStatus.NOT_FOUND));
        Page<Visit> visits = repository.findByDoctor(doctor, pageable);
        return visits.getContent();
    }
    public List<Visit> getVisitsByPatient(Pageable pageable, String email){
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(()->new MedicalClinicException("Patient not found", HttpStatus.NOT_FOUND));
        Page<Visit> visits = repository.findByPatient(patient, pageable);
        return visits.getContent();
    }

    public Visit getVisit(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new MedicalClinicException("Visit not found", HttpStatus.NOT_FOUND));
    }

    public Visit createVisit(Visit visit){
        VisitJpaValidator.validateCreationTime(visit,repository);
        return repository.save(visit);
    }

    public void removeVisit(Long id){
        Visit visit = repository.findById(id)
                .orElseThrow(() -> new MedicalClinicException("Visit not found", HttpStatus.NOT_FOUND));
        repository.delete(visit);
    }

    public Visit modifyVisit(Long id, Visit newVisit){
        Visit visit = repository.findById(id)
                .orElseThrow(() -> new MedicalClinicException("Visit not found", HttpStatus.NOT_FOUND));
        visit = visit.update(newVisit);
        return repository.save(visit);
    }

    public Visit assignPatient(Long id, String patientEmail){
        Visit visit = repository.findById(id)
                .orElseThrow(() -> new MedicalClinicException("Visit not found", HttpStatus.NOT_FOUND));
        Patient patient = patientRepository.findByEmail(patientEmail)
                .orElseThrow(() -> new MedicalClinicException("Patient not found", HttpStatus.NOT_FOUND));
        VisitJpaValidator.validatePatientAssignation(visit);
        visit.setPatient(patient);
        return repository.save(visit);
    }
}
