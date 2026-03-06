package com.erp.authService.service.implementation;

import com.erp.authService.entity.AppModule;
import com.erp.authService.mapper.AppModuleMapper;
import com.erp.authService.mapper.PageMapper;
import com.erp.authService.payload.request.AppModuleRequestDTO;
import com.erp.authService.payload.response.AppModuleResponseDTO;
import com.erp.authService.repo.AppModuleRepository;
import com.erp.authService.repo.PageRepository;
import com.erp.authService.service.IAppModuleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AppModuleService implements IAppModuleService {

    private final AppModuleRepository appModuleRepository;
    private final PageRepository pageRepository;
    private final AppModuleMapper appModuleMapper;
    private final PageMapper pageMapper;

    @Override
    public AppModuleResponseDTO create(AppModuleRequestDTO requestDTO) {
        if (appModuleRepository.existsByName(requestDTO.getName())) {
            throw new IllegalArgumentException("AppModule with name '" + requestDTO.getName() + "' already exists");
        }

        AppModule module = appModuleMapper.toEntity(requestDTO);
        AppModule saved = appModuleRepository.save(module);

        AppModuleResponseDTO responseDTO = appModuleMapper.toResponseDTO(saved);
        responseDTO.setPages(pageMapper.toResponseDTOList(
                pageRepository.findByAppModule_IdAndParentIsNull(saved.getId())));
        return responseDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public AppModuleResponseDTO getById(String id) {
        AppModule module = findModuleById(id);
        AppModuleResponseDTO responseDTO = appModuleMapper.toResponseDTO(module);
        responseDTO.setPages(pageMapper.toResponseDTOList(
                pageRepository.findByAppModule_IdAndParentIsNull(id)));
        return responseDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppModuleResponseDTO> getAll() {
        return appModuleRepository.findAll()
                .stream()
                .map(module -> {
                    AppModuleResponseDTO dto = appModuleMapper.toResponseDTO(module);
                    dto.setPages(pageMapper.toResponseDTOList(
                            pageRepository.findByAppModule_IdAndParentIsNull(module.getId())));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public AppModuleResponseDTO update(String id, AppModuleRequestDTO requestDTO) {
        AppModule module = findModuleById(id);

        if (appModuleRepository.existsByNameAndIdNot(requestDTO.getName(), id)) {
            throw new IllegalArgumentException("AppModule with name '" + requestDTO.getName() + "' already exists");
        }

        appModuleMapper.updateEntityFromDTO(requestDTO, module);
        AppModule saved = appModuleRepository.save(module);

        AppModuleResponseDTO responseDTO = appModuleMapper.toResponseDTO(saved);
        responseDTO.setPages(pageMapper.toResponseDTOList(
                pageRepository.findByAppModule_IdAndParentIsNull(saved.getId())));
        return responseDTO;
    }

    @Override
    public void delete(String id) {
        appModuleRepository.delete(findModuleById(id));
    }

    // ─── Helpers ────────────────────────────────────────────────────────────────

    private AppModule findModuleById(String id) {
        return appModuleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AppModule not found with id: " + id));
    }
}
