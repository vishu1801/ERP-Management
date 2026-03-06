package com.erp.authService.mapper;

import com.erp.authService.entity.GroupModulePageAccess;
import com.erp.authService.payload.request.GroupModulePageAccessRequestDTO;
import com.erp.authService.payload.response.GroupModulePageAccessResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GroupModulePageAccessMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "group", ignore = true)      // resolved in service
    @Mapping(target = "appModule", ignore = true)  // resolved in service
    @Mapping(target = "page", ignore = true)       // resolved in service
    GroupModulePageAccess toEntity(GroupModulePageAccessRequestDTO requestDTO);

    @Mapping(target = "groupId", source = "group.id")
    @Mapping(target = "groupName", source = "group.name")
    @Mapping(target = "appModuleId", source = "appModule.id")
    @Mapping(target = "appModuleName", source = "appModule.name")
    @Mapping(target = "pageId", source = "page.id")
    @Mapping(target = "pageName", source = "page.name")
    @Mapping(target = "pagePath", source = "page.path")
    GroupModulePageAccessResponseDTO toResponseDTO(GroupModulePageAccess access);

    List<GroupModulePageAccessResponseDTO> toResponseDTOList(List<GroupModulePageAccess> accessList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "group", ignore = true)
    @Mapping(target = "appModule", ignore = true)
    @Mapping(target = "page", ignore = true)
    void updateEntityFromDTO(GroupModulePageAccessRequestDTO requestDTO, @MappingTarget GroupModulePageAccess access);
}
