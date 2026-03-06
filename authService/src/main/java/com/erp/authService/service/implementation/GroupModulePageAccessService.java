package com.erp.authService.service.implementation;

import com.erp.authService.entity.AppModule;
import com.erp.authService.entity.Group;
import com.erp.authService.entity.GroupModulePageAccess;
import com.erp.authService.entity.Page;
import com.erp.authService.mapper.GroupModulePageAccessMapper;
import com.erp.authService.payload.request.GroupModulePageAccessRequestDTO;
import com.erp.authService.payload.response.GroupModulePageAccessResponseDTO;
import com.erp.authService.repo.AppModuleRepository;
import com.erp.authService.repo.GroupModulePageAccessRepository;
import com.erp.authService.repo.GroupRepository;
import com.erp.authService.repo.PageRepository;
import com.erp.authService.service.IGroupModulePageAccessService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupModulePageAccessService implements IGroupModulePageAccessService {

    private final GroupModulePageAccessRepository accessRepository;
    private final GroupRepository groupRepository;
    private final AppModuleRepository appModuleRepository;
    private final PageRepository pageRepository;
    private final GroupModulePageAccessMapper accessMapper;

    @Override
    public GroupModulePageAccessResponseDTO create(GroupModulePageAccessRequestDTO requestDTO) {
        if (accessRepository.existsByGroup_IdAndAppModule_IdAndPage_Id(
                requestDTO.getGroupId(), requestDTO.getAppModuleId(), requestDTO.getPageId())) {
            throw new IllegalArgumentException("Access record already exists for this group, module and page combination");
        }

        Group group = findGroupById(requestDTO.getGroupId());
        AppModule module = findModuleById(requestDTO.getAppModuleId());
        Page page = findPageById(requestDTO.getPageId());

        if (!page.getAppModule().getId().equals(requestDTO.getAppModuleId())) {
            throw new IllegalArgumentException("Page does not belong to the specified module");
        }

        GroupModulePageAccess access = accessMapper.toEntity(requestDTO);
        access.setGroup(group);
        access.setAppModule(module);
        access.setPage(page);

        return accessMapper.toResponseDTO(accessRepository.save(access));
    }

    @Override
    @Transactional(readOnly = true)
    public GroupModulePageAccessResponseDTO getById(String id) {
        return accessMapper.toResponseDTO(findAccessById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupModulePageAccessResponseDTO> getAll() {
        return accessMapper.toResponseDTOList(accessRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupModulePageAccessResponseDTO> getByGroup(String groupId) {
        findGroupById(groupId);
        return accessMapper.toResponseDTOList(accessRepository.findByGroup_Id(groupId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupModulePageAccessResponseDTO> getByGroupAndModule(String groupId, String appModuleId) {
        findGroupById(groupId);
        findModuleById(appModuleId);
        return accessMapper.toResponseDTOList(accessRepository.findByGroup_IdAndAppModule_Id(groupId, appModuleId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupModulePageAccessResponseDTO> getAccessiblePagesByGroup(String groupId) {
        findGroupById(groupId);
        return accessMapper.toResponseDTOList(accessRepository.findByGroup_IdAndIsAccessibleTrue(groupId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupModulePageAccessResponseDTO> getAccessiblePagesByGroupAndModule(String groupId, String appModuleId) {
        findGroupById(groupId);
        findModuleById(appModuleId);
        return accessMapper.toResponseDTOList(
                accessRepository.findByGroup_IdAndAppModule_IdAndIsAccessibleTrue(groupId, appModuleId));
    }

    @Override
    public GroupModulePageAccessResponseDTO update(String id, GroupModulePageAccessRequestDTO requestDTO) {
        GroupModulePageAccess access = findAccessById(id);

        Group group = findGroupById(requestDTO.getGroupId());
        AppModule module = findModuleById(requestDTO.getAppModuleId());
        Page page = findPageById(requestDTO.getPageId());

        if (!page.getAppModule().getId().equals(requestDTO.getAppModuleId())) {
            throw new IllegalArgumentException("Page does not belong to the specified module");
        }

        accessRepository.findByGroup_IdAndAppModule_IdAndPage_Id(
                requestDTO.getGroupId(), requestDTO.getAppModuleId(), requestDTO.getPageId())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new IllegalArgumentException("Access record already exists for this combination");
                    }
                });

        accessMapper.updateEntityFromDTO(requestDTO, access);
        access.setGroup(group);
        access.setAppModule(module);
        access.setPage(page);

        return accessMapper.toResponseDTO(accessRepository.save(access));
    }

    @Override
    public GroupModulePageAccessResponseDTO toggleAccess(String groupId, String appModuleId, String pageId) {
        GroupModulePageAccess access = accessRepository
                .findByGroup_IdAndAppModule_IdAndPage_Id(groupId, appModuleId, pageId)
                .orElseThrow(() -> new EntityNotFoundException("Access record not found for given group, module and page"));

        access.setIsAccessible(!access.getIsAccessible());
        return accessMapper.toResponseDTO(accessRepository.save(access));
    }

    @Override
    public void delete(String id) {
        accessRepository.delete(findAccessById(id));
    }

    @Override
    public void deleteByGroupModulePage(String groupId, String appModuleId, String pageId) {
        if (!accessRepository.existsByGroup_IdAndAppModule_IdAndPage_Id(groupId, appModuleId, pageId)) {
            throw new EntityNotFoundException("Access record not found for given group, module and page");
        }
        accessRepository.deleteByGroup_IdAndAppModule_IdAndPage_Id(groupId, appModuleId, pageId);
    }

    // ─── Helpers ────────────────────────────────────────────────────────────────

    private GroupModulePageAccess findAccessById(String id) {
        return accessRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("GroupModulePageAccess not found with id: " + id));
    }

    private Group findGroupById(String id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + id));
    }

    private AppModule findModuleById(String id) {
        return appModuleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AppModule not found with id: " + id));
    }

    private Page findPageById(String id) {
        return pageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Page not found with id: " + id));
    }
}
