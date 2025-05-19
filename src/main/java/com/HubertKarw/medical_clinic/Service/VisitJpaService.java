package com.HubertKarw.medical_clinic.Service;

import com.HubertKarw.medical_clinic.Exception.MedicalClinicException;
import com.HubertKarw.medical_clinic.Model.Doctor;
import com.HubertKarw.medical_clinic.Model.Patient;
import com.HubertKarw.medical_clinic.Model.Visit;
import com.HubertKarw.medical_clinic.Repository.DoctorJpaRepository;
import com.HubertKarw.medical_clinic.Repository.VisitJpaRepository;
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
    public List<Visit> getVisits(Pageable pageable){
        Page<Visit> visits = repository.findAll(pageable);
        return visits.getContent();
    }

    public List<Visit> getVisitsByDoctor(Pageable pageable, Long doctorID){
        Doctor doctor = doctorRepository.findById(doctorID)
                .orElseThrow(()->new MedicalClinicException("Doctor not found", HttpStatus.NOT_FOUND));
        Page<Visit> visits = repository.findByDoctor(doctor, pageable);
        return visits.getContent();
    }
    public List<Visit> getVisitsByPatient(Pageable pageable, Patient patient){
        Page<Visit> visits = repository.findByPatient(patient, pageable);
        return visits.getContent();
    }
}
