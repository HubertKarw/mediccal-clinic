package com.HubertKarw.medical_clinic.mappers;

import com.HubertKarw.medical_clinic.Model.CreateVisitCommand;
import com.HubertKarw.medical_clinic.Model.Visit;
import com.HubertKarw.medical_clinic.Model.VisitDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VisitStructMapper {
    VisitDTO mapToDTO(Visit visit);
    Visit mapToVisit(CreateVisitCommand command);
}
