package com.erp.studentInformationSystem.mapper;

import com.erp.studentInformationSystem.model.Caste;
import com.erp.studentInformationSystem.payload.request.CasteRequestDTO;
import com.erp.studentInformationSystem.payload.response.CasteResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CasteMapper {

    Caste toEntity(CasteRequestDTO requestDTO);

    CasteResponseDTO toResponseDTO(Caste entity);
}
