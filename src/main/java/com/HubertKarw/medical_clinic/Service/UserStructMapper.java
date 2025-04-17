package com.HubertKarw.medical_clinic.Service;

import com.HubertKarw.medical_clinic.Model.CreateUserCommand;
import com.HubertKarw.medical_clinic.Model.User;
import com.HubertKarw.medical_clinic.Model.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserStructMapper {

    //    @Mapping(source = "username",target = "username")
    public UserDTO toDto(User user);

    public User toUser(CreateUserCommand command);
}
