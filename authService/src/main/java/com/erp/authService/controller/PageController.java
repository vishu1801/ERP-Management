package com.erp.authService.controller;

import com.erp.authService.payload.request.PageRequestDTO;
import com.erp.authService.payload.response.PageResponseDTO;
import com.erp.authService.service.IPageService;
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
@RequestMapping("/api/v1/pages")
@RequiredArgsConstructor
@Validated
public class PageController {

    private final IPageService pageService;

    @PostMapping
    public ResponseEntity<PageResponseDTO> create(@Valid @RequestBody PageRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pageService.create(requestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PageResponseDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(pageService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<PageResponseDTO>> getAll() {
        return ResponseEntity.ok(pageService.getAll());
    }

    @GetMapping("/module/{appModuleId}")
    public ResponseEntity<List<PageResponseDTO>> getAllByModule(@PathVariable String appModuleId) {
        return ResponseEntity.ok(pageService.getAllByModule(appModuleId));
    }

    @GetMapping("/module/{appModuleId}/root")
    public ResponseEntity<List<PageResponseDTO>> getRootPagesByModule(@PathVariable String appModuleId) {
        return ResponseEntity.ok(pageService.getRootPagesByModule(appModuleId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PageResponseDTO> update(@PathVariable String id,
                                                   @Valid @RequestBody PageRequestDTO requestDTO) {
        return ResponseEntity.ok(pageService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        pageService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
