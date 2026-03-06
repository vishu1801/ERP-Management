package com.erp.authService.mapper;

import com.erp.authService.entity.AppModule;
import com.erp.authService.payload.request.AppModuleRequestDTO;
import com.erp.authService.payload.response.AppModuleResponseDTO;
import com.erp.authService.payload.response.AppModuleSummaryResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppModuleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "groups", ignore = true)
    @Mapping(target = "pages", ignore = true)
    AppModule toEntity(AppModuleRequestDTO requestDTO);

    @Mapping(target = "pages", ignore = true) // root pages populated in service
    AppModuleResponseDTO toResponseDTO(AppModule appModule);

    AppModuleSummaryResponseDTO toSummaryResponseDTO(AppModule appModule);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "groups", ignore = true)
    @Mapping(target = "pages", ignore = true)
    void updateEntityFromDTO(AppModuleRequestDTO requestDTO, @MappingTarget AppModule appModule);
}
