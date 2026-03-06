package com.erp.authService.service;

import com.erp.authService.payload.request.GroupRequestDTO;
import com.erp.authService.payload.response.GroupResponseDTO;

import java.util.List;
import java.util.Set;

public interface IGroupService {

    GroupResponseDTO create(GroupRequestDTO requestDTO);

    GroupResponseDTO getById(String id);

    List<GroupResponseDTO> getAll();

    GroupResponseDTO update(String id, GroupRequestDTO requestDTO);

    void delete(String id);

    GroupResponseDTO addModules(String groupId, Set<String> appModuleIds);

    GroupResponseDTO removeModules(String groupId, Set<String> appModuleIds);
}
