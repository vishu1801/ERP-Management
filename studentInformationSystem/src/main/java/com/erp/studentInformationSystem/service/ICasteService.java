package com.erp.studentInformationSystem.service;

import com.erp.studentInformationSystem.payload.request.CasteRequestDTO;
import com.erp.studentInformationSystem.payload.response.CasteResponseDTO;
import jakarta.validation.Valid;
import java.util.List;

public interface ICasteService {
    CasteResponseDTO createCaste(@Valid CasteRequestDTO requestDTO);

    CasteResponseDTO getCasteById(String casteId);

    List<CasteResponseDTO> getAllCaste();

    CasteResponseDTO updateCaste(String id, CasteRequestDTO requestDTO);
}
