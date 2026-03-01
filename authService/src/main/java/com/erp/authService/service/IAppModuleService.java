package com.erp.authService.service;

import com.erp.authService.payload.request.AppModuleRequestDTO;
import com.erp.authService.payload.response.AppModuleResponseDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface IAppModuleService {
    AppModuleResponseDTO createModule(AppModuleRequestDTO request);

    AppModuleResponseDTO getModuleById(String id);

    List<AppModuleResponseDTO> getAllModules();

    AppModuleResponseDTO updateModule(String id, @Valid AppModuleRequestDTO request);

    void deleteModule(String id);
}
