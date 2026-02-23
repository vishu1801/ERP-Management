package com.erp.authService.mapper;


import com.erp.authService.entity.Group;
import com.erp.authService.payload.request.GroupRequestDTO;
import com.erp.authService.payload.response.GroupResponseDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {AppModuleMapper.class})
public interface GroupMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "appModules", ignore = true)
    Group toEntity(GroupRequestDTO request);

    GroupResponseDTO toResponse(Group group);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "appModules", ignore = true)
    void updateEntity(GroupRequestDTO request, @MappingTarget Group group);
}
