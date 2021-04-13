package com.example.trial.mapper;

import com.example.trial.dto.UserDto;
import com.example.trial.dto.UserPatchDto;
import com.example.trial.model.User;
import org.mapstruct.*;

/**
 * Mapper of {@link User} entity
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface UserMapper {

    @Mapping(source = "login", target = "login")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "dateOfBirth", target = "dateOfBirth")
    @Mapping(source = "gender", target = "gender")
    User map(UserDto userDto);

    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "dateOfBirth", target = "dateOfBirth")
    @Mapping(source = "gender", target = "gender")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUser(UserPatchDto userPatchDto, @MappingTarget User user);

}
