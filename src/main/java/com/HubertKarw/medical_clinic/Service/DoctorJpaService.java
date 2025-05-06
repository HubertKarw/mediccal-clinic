package com.HubertKarw.medical_clinic.Service;

import com.HubertKarw.medical_clinic.Exception.MedicalClinicException;
import com.HubertKarw.medical_clinic.Model.Doctor;
import com.HubertKarw.medical_clinic.Model.DoctorDTO;
import com.HubertKarw.medical_clinic.Model.Institution;
import com.HubertKarw.medical_clinic.Repository.DoctorJpaRepository;
import com.HubertKarw.medical_clinic.Repository.InstitutionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorJpaService {
    private final DoctorJpaRepository repository;
    private final InstitutionJpaRepository institutionRepository;

    public List<Doctor> getDoctors(Pageable pageable) {
        Page<Doctor> doctors = repository.findAll(pageable);
        return doctors.getContent();
    }

    public Doctor getDoctor(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new MedicalClinicException("Doctor not found", HttpStatus.NOT_FOUND));
    }

    public Doctor addDoctor(Doctor doctor) {
        return repository.save(doctor);
    }

    public void removeDoctor(String email) {
        Doctor doctor = repository.findByEmail(email)
                .orElseThrow(() -> new MedicalClinicException("Doctor not found", HttpStatus.NOT_FOUND));
        repository.delete(doctor);
    }

    public Doctor modifyDoctor(String email, Doctor newDoctor) {
        Doctor doctor = repository.findByEmail(email)
                .orElseThrow(() -> new MedicalClinicException("Doctor not found", HttpStatus.NOT_FOUND));
        doctor.update(newDoctor);
        return repository.save(doctor);
    }

    public Doctor assignToInstitution(String email, String name) {
        Doctor doctor = repository.findByEmail(email)
                .orElseThrow(() -> new MedicalClinicException("Doctor not found", HttpStatus.NOT_FOUND));
        Institution institution = institutionRepository.findByName(name)
                .orElseThrow(() -> new MedicalClinicException("Institution not found", HttpStatus.NOT_FOUND));
        doctor = doctor.addInstitution(institution);
        return repository.save(doctor);
    }
}
