package com.erp.authService.service.implementation;

import com.erp.authService.entity.AppModule;
import com.erp.authService.entity.Group;
import com.erp.authService.entity.GroupModulePageAccess;
import com.erp.authService.entity.Page;
import com.erp.authService.mapper.PageMapper;
import com.erp.authService.payload.request.PageRequestDTO;
import com.erp.authService.payload.response.PageResponseDTO;
import com.erp.authService.repo.AppModuleRepository;
import com.erp.authService.repo.GroupModulePageAccessRepository;
import com.erp.authService.repo.GroupRepository;
import com.erp.authService.repo.PageRepository;
import com.erp.authService.service.IPageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PageService implements IPageService {

    private final PageRepository pageRepository;
    private final AppModuleRepository appModuleRepository;
    private final GroupRepository groupRepository;
    private final GroupModulePageAccessRepository groupModulePageAccessRepository;
    private final PageMapper pageMapper;

    @Override
    public PageResponseDTO create(PageRequestDTO requestDTO) {
        AppModule module = findModuleById(requestDTO.getAppModuleId());

        if (pageRepository.existsByPathAndAppModule_Id(requestDTO.getPath(), requestDTO.getAppModuleId())) {
            throw new IllegalArgumentException("Page with path '" + requestDTO.getPath() + "' already exists in this module");
        }

        Page page = pageMapper.toEntity(requestDTO);
        page.setAppModule(module);

        if (requestDTO.getParentId() != null) {
            Page parent = findPageById(requestDTO.getParentId());
            page.setParent(parent);
        }

        Page savedPage = pageRepository.save(page);

        // ─── Default: grant access to this new page for ALL existing groups ────────
        assignPageToAllGroups(savedPage, module);

        return pageMapper.toResponseDTO(savedPage);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponseDTO getById(String id) {
        return pageMapper.toResponseDTO(findPageById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PageResponseDTO> getAll() {
        return pageMapper.toResponseDTOList(pageRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PageResponseDTO> getAllByModule(String appModuleId) {
        findModuleById(appModuleId);
        return pageMapper.toResponseDTOList(pageRepository.findByAppModule_Id(appModuleId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PageResponseDTO> getRootPagesByModule(String appModuleId) {
        findModuleById(appModuleId);
        return pageMapper.toResponseDTOList(pageRepository.findByAppModule_IdAndParentIsNull(appModuleId));
    }

    @Override
    public PageResponseDTO update(String id, PageRequestDTO requestDTO) {
        Page page = findPageById(id);
        AppModule module = findModuleById(requestDTO.getAppModuleId());

        if (pageRepository.existsByPathAndAppModule_IdAndIdNot(requestDTO.getPath(), requestDTO.getAppModuleId(), id)) {
            throw new IllegalArgumentException("Page with path '" + requestDTO.getPath() + "' already exists in this module");
        }

        pageMapper.updateEntityFromDTO(requestDTO, page);
        page.setAppModule(module);

        if (requestDTO.getParentId() != null) {
            if (requestDTO.getParentId().equals(id)) {
                throw new IllegalArgumentException("A page cannot be its own parent");
            }
            page.setParent(findPageById(requestDTO.getParentId()));
        } else {
            page.setParent(null);
        }

        return pageMapper.toResponseDTO(pageRepository.save(page));
    }

    @Override
    public void delete(String id) {
        pageRepository.delete(findPageById(id));
    }

    // ─── Private Helpers ─────────────────────────────────────────────────────────

    /**
     * When a new Page is created, fetch ALL existing groups
     * and create a GroupModulePageAccess record with isAccessible = true for each.
     */
    private void assignPageToAllGroups(Page page, AppModule module) {
        List<Group> allGroups = groupRepository.findAll();

        List<GroupModulePageAccess> accessList = allGroups.stream()
                .filter(group -> !groupModulePageAccessRepository
                        .existsByGroup_IdAndAppModule_IdAndPage_Id(
                                group.getId(),
                                module.getId(),
                                page.getId()))
                .map(group -> buildAccessRecord(group, module, page))
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

    private Page findPageById(String id) {
        return pageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Page not found with id: " + id));
    }

    private AppModule findModuleById(String id) {
        return appModuleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AppModule not found with id: " + id));
    }
}
