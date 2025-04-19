package com.HubertKarw.medical_clinic.mappers;

import com.HubertKarw.medical_clinic.Model.CreateDoctorCommand;
import com.HubertKarw.medical_clinic.Model.Doctor;
import com.HubertKarw.medical_clinic.Model.DoctorDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorStructMapper {
    public DoctorDTO mapToDTO(Doctor doctor);

    public Doctor mapToDoctor(CreateDoctorCommand command);
}
