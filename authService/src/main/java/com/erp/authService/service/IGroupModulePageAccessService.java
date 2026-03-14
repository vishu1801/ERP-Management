package com.erp.authService.service;

import com.erp.authService.payload.request.GroupModulePageAccessRequestDTO;
import com.erp.authService.payload.response.AccessiblePageResponseDTO;
import com.erp.authService.payload.response.GroupModulePageAccessResponseDTO;

import java.util.List;

public interface IGroupModulePageAccessService {

    GroupModulePageAccessResponseDTO create(GroupModulePageAccessRequestDTO requestDTO);

    GroupModulePageAccessResponseDTO getById(String id);

    List<GroupModulePageAccessResponseDTO> getAll();

    List<GroupModulePageAccessResponseDTO> getByGroup(String groupId);

    List<GroupModulePageAccessResponseDTO> getByGroupAndModule(String groupId, String appModuleId);

    List<GroupModulePageAccessResponseDTO> getAccessiblePagesByGroup(String groupId);

    List<GroupModulePageAccessResponseDTO> getAccessiblePagesByGroupAndModule(String groupId, String appModuleId);

    List<AccessiblePageResponseDTO> getAccessiblePagesHierarchy(String groupId, String appModuleId);

    GroupModulePageAccessResponseDTO update(String id, GroupModulePageAccessRequestDTO requestDTO);

    GroupModulePageAccessResponseDTO toggleAccess(String groupId, String appModuleId, String pageId);

    void delete(String id);

    void deleteByGroupModulePage(String groupId, String appModuleId, String pageId);
}
