package com.erp.studentInformationSystem.service;

import com.erp.studentInformationSystem.payload.request.AcademicYearRequestDTO;
import com.erp.studentInformationSystem.payload.response.AcademicYearResponseDTO;
import java.util.List;

public interface IAcademicYearService {
    AcademicYearResponseDTO createAcademicYear(AcademicYearRequestDTO requestDTO);

    List<AcademicYearResponseDTO> getAllAcademicYear();

    AcademicYearResponseDTO getById(String id);

    AcademicYearResponseDTO updateAcademicYear(String id, AcademicYearRequestDTO requestDTO);
}
