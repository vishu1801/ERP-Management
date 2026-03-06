package com.erp.authService.mapper;

import com.erp.authService.entity.AppModule;
import com.erp.authService.entity.Group;
import com.erp.authService.payload.request.GroupRequestDTO;
import com.erp.authService.payload.response.AppModuleSummaryResponseDTO;
import com.erp.authService.payload.response.GroupResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GroupMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "appModules", ignore = true) // handled manually in service
    Group toEntity(GroupRequestDTO requestDTO);

    @Mapping(target = "appModules", source = "appModules")
    GroupResponseDTO toResponseDTO(Group group);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "appModules", ignore = true)
    void updateEntityFromDTO(GroupRequestDTO requestDTO, @MappingTarget Group group);

    default AppModuleSummaryResponseDTO toAppModuleSummaryDTO(AppModule appModule) {
        if (appModule == null) return null;
        return AppModuleSummaryResponseDTO.builder()
                .id(appModule.getId())
                .name(appModule.getName())
                .displayName(appModule.getDisplayName())
                .description(appModule.getDescription())
                .build();
    }
}
