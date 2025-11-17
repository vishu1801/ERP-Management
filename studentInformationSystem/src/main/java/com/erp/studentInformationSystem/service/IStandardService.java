package com.erp.studentInformationSystem.service;

import com.erp.studentInformationSystem.payload.request.StandardRequestDTO;
import com.erp.studentInformationSystem.payload.response.StandardResponseDTO;
import jakarta.validation.Valid;
import java.util.List;

public interface IStandardService {
    StandardResponseDTO createStandard(@Valid StandardRequestDTO requestDTO);
    
    List<StandardResponseDTO> getAllStandard();

    StandardResponseDTO getStandardById(String yearId);

    StandardResponseDTO updateStandard(String id, StandardRequestDTO requestDTO);
}
