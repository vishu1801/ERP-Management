package com.erp.authService.service.implementation;

import com.erp.authService.entity.AppModule;
import com.erp.authService.mapper.AppModuleMapper;
import com.erp.authService.payload.request.AppModuleRequestDTO;
import com.erp.authService.payload.response.AppModuleResponseDTO;
import com.erp.authService.repo.AppModuleRepository;
import com.erp.authService.service.IAppModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppModuleService implements IAppModuleService {

    private final AppModuleRepository appModuleRepository;
    private final AppModuleMapper appModuleMapper;

    @Override
    public AppModuleResponseDTO createModule(AppModuleRequestDTO request) {
        if (appModuleRepository.existsByName(request.getName())) {
//            throw new DuplicateResourceException("AppModule", "name", request.getName());
        }
        AppModule appModule = appModuleMapper.toEntity(request);
        return appModuleMapper.toResponse(appModuleRepository.save(appModule));
    }

    @Override
    @Transactional(readOnly = true)
    public AppModuleResponseDTO getModuleById(String id) {
        AppModule appModule = appModuleRepository.findById(id)
                .orElseThrow();
        return appModuleMapper.toResponse(appModule);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppModuleResponseDTO> getAllModules() {
        return appModuleRepository.findAll()
                .stream()
                .map(appModuleMapper::toResponse)
                .toList();
    }

    @Override
    public AppModuleResponseDTO updateModule(String id, AppModuleRequestDTO request) {
        AppModule appModule = appModuleRepository.findById(id)
                .orElseThrow();

        if (!appModule.getName().equals(request.getName())
                && appModuleRepository.existsByName(request.getName())) {
//            throw new DuplicateResourceException("AppModule", "name", request.getName());
        }

        appModuleMapper.updateEntity(request, appModule);
        return appModuleMapper.toResponse(appModuleRepository.save(appModule));
    }

    @Override
    public void deleteModule(String id) {
        AppModule appModule = appModuleRepository.findById(id)
                .orElseThrow();
        appModuleRepository.delete(appModule);
    }
}
