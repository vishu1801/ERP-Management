package com.erp.authService.mapper;

import com.erp.authService.entity.User;
import com.erp.authService.payload.request.UserRequestDTO;
import com.erp.authService.payload.response.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "group", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "username", source = "userName")
    User toEntity(UserRequestDTO requestDTO);

    @Mapping(target = "groupId", source = "group.id")
    @Mapping(target = "groupName", source = "group.name")
    @Mapping(target = "groupDisplayName", source = "group.displayName")
    @Mapping(target = "userName", source = "username")
    UserResponseDTO toResponseDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "group", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "username", source = "userName")
    void updateUser(@MappingTarget User user, UserRequestDTO requestDTO);
}
