package com.erp.authService.service;

import com.erp.authService.payload.request.PageRequestDTO;
import com.erp.authService.payload.response.PageResponseDTO;

import java.util.List;

public interface IPageService {

    PageResponseDTO create(PageRequestDTO requestDTO);

    PageResponseDTO getById(String id);

    List<PageResponseDTO> getAll();

    List<PageResponseDTO> getAllByModule(String appModuleId);

    List<PageResponseDTO> getRootPagesByModule(String appModuleId);

    PageResponseDTO update(String id, PageRequestDTO requestDTO);

    void delete(String id);
}
