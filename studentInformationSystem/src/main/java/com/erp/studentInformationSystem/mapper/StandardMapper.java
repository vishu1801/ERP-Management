package com.erp.studentInformationSystem.mapper;

import com.erp.studentInformationSystem.model.Standard;
import com.erp.studentInformationSystem.payload.request.StandardRequestDTO;
import com.erp.studentInformationSystem.payload.response.StandardResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StandardMapper {
    Standard toEntity(StandardRequestDTO requestDTO);

     StandardResponseDTO toResponseDTO(Standard entity);
}
