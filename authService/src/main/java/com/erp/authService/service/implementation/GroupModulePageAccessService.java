package com.erp.authService.service.implementation;

import com.erp.authService.entity.AppModule;
import com.erp.authService.entity.Group;
import com.erp.authService.entity.GroupModulePageAccess;
import com.erp.authService.entity.Page;
import com.erp.authService.mapper.GroupModulePageAccessMapper;
import com.erp.authService.payload.request.GroupModulePageAccessRequestDTO;
import com.erp.authService.payload.response.AccessiblePageResponseDTO;
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Transactional(readOnly = true)
    public List<AccessiblePageResponseDTO> getAccessiblePagesHierarchy(String groupId, String appModuleId) {
        findGroupById(groupId);
        findModuleById(appModuleId);

        List<GroupModulePageAccess> accessList = accessRepository
                .findByGroup_IdAndAppModule_IdAndIsAccessibleTrueOrderByPage_DisplayOrderAsc(groupId, appModuleId);

        Set<String> accessiblePageIds = accessList.stream()
                .map(a -> a.getPage().getId())
                .collect(Collectors.toSet());

        Map<String, AccessiblePageResponseDTO> dtoMap = accessList.stream()
                .collect(Collectors.toMap(
                        a -> a.getPage().getId(),
                        a -> toAccessiblePageDTO(a.getPage())
                ));

        List<AccessiblePageResponseDTO> rootPages = new ArrayList<>();

        for (GroupModulePageAccess access : accessList) {
            Page page = access.getPage();
            AccessiblePageResponseDTO dto = dtoMap.get(page.getId());

            if (page.getParent() == null || !accessiblePageIds.contains(page.getParent().getId())) {
                rootPages.add(dto);
            } else {
                AccessiblePageResponseDTO parentDto = dtoMap.get(page.getParent().getId());
                if (parentDto != null) {
                    parentDto.getSubPages().add(dto);
                }
            }
        }

        Comparator<AccessiblePageResponseDTO> byDisplayOrder =
                Comparator.comparingInt(p -> p.getDisplayOrder() != null ? p.getDisplayOrder() : Integer.MAX_VALUE);

        rootPages.sort(byDisplayOrder);
        rootPages.forEach(p -> sortSubPagesRecursively(p, byDisplayOrder));

        return rootPages;
    }

    private AccessiblePageResponseDTO toAccessiblePageDTO(Page page) {
        return AccessiblePageResponseDTO.builder()
                .pageId(page.getId())
                .pageName(page.getName())
                .pageDisplayName(page.getDisplayName())
                .pagePath(page.getPath())
                .pageIcon(page.getIcon())
                .displayOrder(page.getDisplayOrder())
                .build();
    }

    private void sortSubPagesRecursively(AccessiblePageResponseDTO dto, Comparator<AccessiblePageResponseDTO> comparator) {
        dto.getSubPages().sort(comparator);
        dto.getSubPages().forEach(sub -> sortSubPagesRecursively(sub, comparator));
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
