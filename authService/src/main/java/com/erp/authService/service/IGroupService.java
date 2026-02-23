package com.erp.authService.service;

import com.erp.authService.payload.request.GroupRequestDTO;
import com.erp.authService.payload.response.GroupResponseDTO;

import java.util.List;
import java.util.Set;

public interface IGroupService {
    GroupResponseDTO createGroup(GroupRequestDTO request);

    GroupResponseDTO getGroupById(String id);

    List<GroupResponseDTO> getAllGroups();

    GroupResponseDTO updateGroup(String id, GroupRequestDTO request);

    void deleteGroup(String id);

    GroupResponseDTO assignModulesToGroup(String groupId, Set<Long> moduleIds);

    GroupResponseDTO removeModulesFromGroup(String groupId, Set<Long> moduleIds);
}
