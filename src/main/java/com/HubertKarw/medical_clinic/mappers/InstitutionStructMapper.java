package com.HubertKarw.medical_clinic.mappers;

import com.HubertKarw.medical_clinic.Model.CreateInstitutionCommand;
import com.HubertKarw.medical_clinic.Model.Institution;
import com.HubertKarw.medical_clinic.Model.InstitutionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InstitutionStructMapper {
    InstitutionDTO mapToDTO(Institution institution);

    Institution mapToInstitution(CreateInstitutionCommand command);
}
