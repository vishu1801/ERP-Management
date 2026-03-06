package com.erp.authService.mapper;

import com.erp.authService.entity.Page;
import com.erp.authService.payload.request.PageRequestDTO;
import com.erp.authService.payload.response.PageResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PageMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "appModule", ignore = true)  // resolved in service
    @Mapping(target = "parent", ignore = true)      // resolved in service
    @Mapping(target = "subPages", ignore = true)
    Page toEntity(PageRequestDTO requestDTO);

    @Mapping(target = "appModuleId", source = "appModule.id")
    @Mapping(target = "appModuleName", source = "appModule.name")
    @Mapping(target = "parentId", source = "parent.id")
    @Mapping(target = "parentName", source = "parent.name")
    @Mapping(target = "subPages", source = "subPages")
    PageResponseDTO toResponseDTO(Page page);

    List<PageResponseDTO> toResponseDTOList(List<Page> pages);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "appModule", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "subPages", ignore = true)
    void updateEntityFromDTO(PageRequestDTO requestDTO, @MappingTarget Page page);
}
