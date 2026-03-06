package com.erp.authService.service.implementation;

import com.erp.authService.entity.AppModule;
import com.erp.authService.entity.Group;
import com.erp.authService.entity.GroupModulePageAccess;
import com.erp.authService.entity.Page;
import com.erp.authService.mapper.GroupMapper;
import com.erp.authService.payload.request.GroupRequestDTO;
import com.erp.authService.payload.response.GroupResponseDTO;
import com.erp.authService.repo.AppModuleRepository;
import com.erp.authService.repo.GroupModulePageAccessRepository;
import com.erp.authService.repo.GroupRepository;
import com.erp.authService.repo.PageRepository;
import com.erp.authService.service.IGroupService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupService implements IGroupService {

    private final GroupRepository groupRepository;
    private final AppModuleRepository appModuleRepository;
    private final PageRepository pageRepository;
    private final GroupModulePageAccessRepository groupModulePageAccessRepository;
    private final GroupMapper groupMapper;

    @Override
    public GroupResponseDTO create(GroupRequestDTO requestDTO) {
        if (groupRepository.existsByName(requestDTO.getName())) {
            throw new IllegalArgumentException("Group with name '" + requestDTO.getName() + "' already exists");
        }

        Group group = groupMapper.toEntity(requestDTO);

        if (requestDTO.getAppModuleIds() != null && !requestDTO.getAppModuleIds().isEmpty()) {
            Set<AppModule> modules = new HashSet<>(appModuleRepository.findAllById(requestDTO.getAppModuleIds()));
            group.setAppModules(modules);
        }

        Group savedGroup = groupRepository.save(group);

        // ─── Default: grant access to ALL existing pages for this new group ───────
        assignAllPagesToGroup(savedGroup);

        return groupMapper.toResponseDTO(savedGroup);
    }

    @Override
    @Transactional(readOnly = true)
    public GroupResponseDTO getById(String id) {
        return groupMapper.toResponseDTO(findGroupById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupResponseDTO> getAll() {
        return groupRepository.findAll()
                .stream()
                .map(groupMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GroupResponseDTO update(String id, GroupRequestDTO requestDTO) {
        Group group = findGroupById(id);

        if (groupRepository.existsByNameAndIdNot(requestDTO.getName(), id)) {
            throw new IllegalArgumentException("Group with name '" + requestDTO.getName() + "' already exists");
        }

        groupMapper.updateEntityFromDTO(requestDTO, group);

        if (requestDTO.getAppModuleIds() != null) {
            Set<AppModule> modules = new HashSet<>(appModuleRepository.findAllById(requestDTO.getAppModuleIds()));
            group.setAppModules(modules);
        }

        return groupMapper.toResponseDTO(groupRepository.save(group));
    }

    @Override
    public void delete(String id) {
        groupRepository.delete(findGroupById(id));
    }

    @Override
    public GroupResponseDTO addModules(String groupId, Set<String> appModuleIds) {
        Group group = findGroupById(groupId);
        Set<AppModule> newModules = new HashSet<>(appModuleRepository.findAllById(appModuleIds));
        group.getAppModules().addAll(newModules);
        Group savedGroup = groupRepository.save(group);

        // ─── When new modules are added to a group, grant access to all their pages ─
        newModules.forEach(module ->
                assignModulePagesToGroup(savedGroup, module)
        );

        return groupMapper.toResponseDTO(savedGroup);
    }

    @Override
    public GroupResponseDTO removeModules(String groupId, Set<String> appModuleIds) {
        Group group = findGroupById(groupId);
        group.getAppModules().removeIf(module -> appModuleIds.contains(module.getId()));
        return groupMapper.toResponseDTO(groupRepository.save(group));
    }

    // ─── Private Helpers ─────────────────────────────────────────────────────────

    /**
     * When a new Group is created, fetch ALL pages across ALL modules
     * and create a GroupModulePageAccess record with isAccessible = true for each.
     */
    private void assignAllPagesToGroup(Group group) {
        List<Page> allPages = pageRepository.findAll();

        List<GroupModulePageAccess> accessList = allPages.stream()
                .filter(page -> !groupModulePageAccessRepository
                        .existsByGroup_IdAndAppModule_IdAndPage_Id(
                                group.getId(),
                                page.getAppModule().getId(),
                                page.getId()))
                .map(page -> buildAccessRecord(group, page.getAppModule(), page))
                .collect(Collectors.toList());

        if (!accessList.isEmpty()) {
            groupModulePageAccessRepository.saveAll(accessList);
        }
    }

    /**
     * When a new AppModule is added to a Group, grant access to all pages of that module.
     */
    private void assignModulePagesToGroup(Group group, AppModule module) {
        List<Page> modulePages = pageRepository.findByAppModule_Id(module.getId());

        List<GroupModulePageAccess> accessList = modulePages.stream()
                .filter(page -> !groupModulePageAccessRepository
                        .existsByGroup_IdAndAppModule_IdAndPage_Id(
                                group.getId(),
                                module.getId(),
                                page.getId()))
                .map(page -> buildAccessRecord(group, module, page))
                .collect(Collectors.toList());

        if (!accessList.isEmpty()) {
            groupModulePageAccessRepository.saveAll(accessList);
        }
    }

    /**
     * Builds a GroupModulePageAccess record with isAccessible = true by default.
     */
    private GroupModulePageAccess buildAccessRecord(Group group, AppModule module, Page page) {
        GroupModulePageAccess access = new GroupModulePageAccess();
        access.setGroup(group);
        access.setAppModule(module);
        access.setPage(page);
        access.setIsAccessible(true);
        return access;
    }

    private Group findGroupById(String id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + id));
    }
}
