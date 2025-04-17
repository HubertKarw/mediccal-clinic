package com.HubertKarw.medical_clinic.Service;

import com.HubertKarw.medical_clinic.Model.CreatePatientCommand;
import com.HubertKarw.medical_clinic.Model.Patient;
import com.HubertKarw.medical_clinic.Model.PatientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PatientStructMapper {
    //    @Mapping(source = "email",target = "email")
    public PatientDTO mapToDTO(Patient patient);

    public Patient mapToPatient(CreatePatientCommand command);
}
