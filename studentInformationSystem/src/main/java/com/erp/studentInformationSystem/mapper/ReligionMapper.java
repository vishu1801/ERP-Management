package com.erp.studentInformationSystem.mapper;

import com.erp.studentInformationSystem.model.Religion;
import com.erp.studentInformationSystem.payload.request.ReligionRequestDTO;
import com.erp.studentInformationSystem.payload.response.ReligionResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReligionMapper {
    Religion toEntity(ReligionRequestDTO requestDTO);

    ReligionResponseDTO toResponseDTO(Religion entity);
}
