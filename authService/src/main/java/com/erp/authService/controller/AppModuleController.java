package com.erp.authService.controller;

import com.erp.authService.payload.request.AppModuleRequestDTO;
import com.erp.authService.payload.response.AppModuleResponseDTO;
import com.erp.authService.service.IAppModuleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/app-modules")
@RequiredArgsConstructor
@Validated
public class AppModuleController {

    private final IAppModuleService appModuleService;

    @PostMapping
    public ResponseEntity<AppModuleResponseDTO> create(@Valid @RequestBody AppModuleRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appModuleService.create(requestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppModuleResponseDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(appModuleService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<AppModuleResponseDTO>> getAll() {
        return ResponseEntity.ok(appModuleService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppModuleResponseDTO> update(@PathVariable String id,
                                                        @Valid @RequestBody AppModuleRequestDTO requestDTO) {
        return ResponseEntity.ok(appModuleService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        appModuleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
