package com.erp.studentInformationSystem.mapper;

import com.erp.studentInformationSystem.model.AcademicYear;
import com.erp.studentInformationSystem.payload.request.AcademicYearRequestDTO;
import com.erp.studentInformationSystem.payload.response.AcademicYearResponseDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface AcademicYearMapper {
    AcademicYear toEntity(AcademicYearRequestDTO requestDTO);

    AcademicYearResponseDTO toResponseDTO(AcademicYear savedYear);
}
