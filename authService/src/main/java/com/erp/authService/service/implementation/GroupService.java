package com.erp.authService.service.implementation;

import com.erp.authService.entity.Group;
import com.erp.authService.mapper.GroupMapper;
import com.erp.authService.payload.request.GroupRequestDTO;
import com.erp.authService.payload.response.GroupResponseDTO;
import com.erp.authService.repo.GroupRepository;
import com.erp.authService.repo.AppModuleRepository;
import com.erp.authService.service.IGroupService;
import com.erp.authService.entity.AppModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GroupService implements IGroupService {

    private final GroupRepository groupRepository;
    private final AppModuleRepository appModuleRepository;
    private final GroupMapper groupMapper;

    @Override
    public GroupResponseDTO createGroup(GroupRequestDTO request) {
        if (groupRepository.existsByName(request.getName())) {
//            throw new DuplicateResourceException("Group", "name", request.getName());
        }

        Group group = groupMapper.toEntity(request);

        if (request.getModuleIds() != null && !request.getModuleIds().isEmpty()) {
            Set<AppModule> appModules = appModuleRepository.findAllByIdIn(request.getModuleIds());
            group.setAppModules(appModules);
        }

        return groupMapper.toResponse(groupRepository.save(group));
    }

    @Override
    @Transactional(readOnly = true)
    public GroupResponseDTO getGroupById(String id) {
        Group group = groupRepository.findById(id)
                .orElseThrow();
        return groupMapper.toResponse(group);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupResponseDTO> getAllGroups() {
        return groupRepository.findAll()
                .stream()
                .map(groupMapper::toResponse)
                .toList();
    }

    @Override
    public GroupResponseDTO updateGroup(String id, GroupRequestDTO request) {
        Group group = groupRepository.findById(id)
                .orElseThrow();

        if (!group.getName().equals(request.getName())
                && groupRepository.existsByName(request.getName())) {
//            throw new DuplicateResourceException("Group", "name", request.getName());
        }

        groupMapper.updateEntity(request, group);

        if (request.getModuleIds() != null) {
            Set<AppModule> appModules = appModuleRepository.findAllByIdIn(request.getModuleIds());
            group.setAppModules(appModules);
        }

        return groupMapper.toResponse(groupRepository.save(group));
    }

    @Override
    public void deleteGroup(String id) {
        Group group = groupRepository.findById(id)
                .orElseThrow();
        group.getAppModules().clear(); // Clear join table before deleting
        groupRepository.delete(group);
    }

    @Override
    public GroupResponseDTO assignModulesToGroup(String groupId, Set<Long> moduleIds) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow();

        Set<AppModule> appModules = appModuleRepository.findAllByIdIn(moduleIds);
        group.getAppModules().addAll(appModules);

        return groupMapper.toResponse(groupRepository.save(group));
    }

    @Override
    public GroupResponseDTO removeModulesFromGroup(String groupId, Set<Long> moduleIds) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow();

        group.getAppModules().removeIf(module -> moduleIds.contains(module.getId()));

        return groupMapper.toResponse(groupRepository.save(group));
    }
}
