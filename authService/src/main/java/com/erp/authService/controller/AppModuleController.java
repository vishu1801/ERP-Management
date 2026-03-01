package com.erp.authService.controller;

import com.erp.authService.payload.request.AppModuleRequestDTO;
import com.erp.authService.payload.response.AppModuleResponseDTO;
import com.erp.authService.service.IAppModuleService;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/modules")
@RequiredArgsConstructor
public class AppModuleController {

    private final IAppModuleService appModuleService;

    @PostMapping
    public ResponseEntity<AppModuleResponseDTO> createModule(@Valid @RequestBody AppModuleRequestDTO request) {
        return new ResponseEntity<>(appModuleService.createModule(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppModuleResponseDTO> getModuleById(@PathVariable String id) {
        return ResponseEntity.ok(appModuleService.getModuleById(id));
    }

    @GetMapping
    public ResponseEntity<List<AppModuleResponseDTO>> getAllModules() {
        return ResponseEntity.ok(appModuleService.getAllModules());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppModuleResponseDTO> updateModule(
            @PathVariable String id,
            @Valid @RequestBody AppModuleRequestDTO request) {
        return ResponseEntity.ok(appModuleService.updateModule(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable String id) {
        appModuleService.deleteModule(id);
        return ResponseEntity.noContent().build();
    }
}
