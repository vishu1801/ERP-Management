package com.erp.studentInformationSystem.service;

import com.erp.studentInformationSystem.payload.request.ReligionRequestDTO;
import com.erp.studentInformationSystem.payload.response.ReligionResponseDTO;
import jakarta.validation.Valid;
import java.util.List;

public interface IReligionService {
    ReligionResponseDTO updateReligion(String id, ReligionRequestDTO requestDTO);

    ReligionResponseDTO getReligionById(String religionId);

    List<ReligionResponseDTO> getAllReligion();

    ReligionResponseDTO createReligion(@Valid ReligionRequestDTO requestDTO);
}
