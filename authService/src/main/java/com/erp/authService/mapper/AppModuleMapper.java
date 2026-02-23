package com.erp.authService.mapper;

import com.erp.authService.entity.AppModule;
import com.erp.authService.payload.request.AppModuleRequestDTO;
import com.erp.authService.payload.response.AppModuleResponseDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AppModuleMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "groups", ignore = true)
    AppModule toEntity(AppModuleRequestDTO request);

    AppModuleResponseDTO toResponse(AppModule module);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "groups", ignore = true)
    void updateEntity(AppModuleRequestDTO request, @MappingTarget AppModule module);
}
