package com.erp.authService.service;

import com.erp.authService.payload.request.AppModuleRequestDTO;
import com.erp.authService.payload.response.AppModuleResponseDTO;

import java.util.List;

public interface IAppModuleService {

    AppModuleResponseDTO create(AppModuleRequestDTO requestDTO);

    AppModuleResponseDTO getById(String id);

    List<AppModuleResponseDTO> getAll();

    AppModuleResponseDTO update(String id, AppModuleRequestDTO requestDTO);

    void delete(String id);
}
