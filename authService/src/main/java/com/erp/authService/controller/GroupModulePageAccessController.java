package com.erp.authService.controller;

import com.erp.authService.payload.request.GroupModulePageAccessRequestDTO;
import com.erp.authService.payload.response.AccessiblePageResponseDTO;
import com.erp.authService.payload.response.GroupModulePageAccessResponseDTO;
import com.erp.authService.service.IGroupModulePageAccessService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/group-access")
@RequiredArgsConstructor
@Validated
public class GroupModulePageAccessController {

    private final IGroupModulePageAccessService accessService;

    @PostMapping
    public ResponseEntity<GroupModulePageAccessResponseDTO> create(
            @Valid @RequestBody GroupModulePageAccessRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accessService.create(requestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupModulePageAccessResponseDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(accessService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<GroupModulePageAccessResponseDTO>> getAll() {
        return ResponseEntity.ok(accessService.getAll());
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<GroupModulePageAccessResponseDTO>> getByGroup(@PathVariable String groupId) {
        return ResponseEntity.ok(accessService.getByGroup(groupId));
    }

    @GetMapping("/group/{groupId}/module/{appModuleId}")
    public ResponseEntity<List<GroupModulePageAccessResponseDTO>> getByGroupAndModule(
            @PathVariable String groupId,
            @PathVariable String appModuleId) {
        return ResponseEntity.ok(accessService.getByGroupAndModule(groupId, appModuleId));
    }

    @GetMapping("/group/{groupId}/accessible")
    public ResponseEntity<List<GroupModulePageAccessResponseDTO>> getAccessiblePagesByGroup(
            @PathVariable String groupId) {
        return ResponseEntity.ok(accessService.getAccessiblePagesByGroup(groupId));
    }

    @GetMapping("/group/{groupId}/module/{appModuleId}/accessible")
    public ResponseEntity<List<AccessiblePageResponseDTO>> getAccessiblePagesByGroupAndModule(
            @PathVariable String groupId,
            @PathVariable String appModuleId) {
        return ResponseEntity.ok(accessService.getAccessiblePagesHierarchy(groupId, appModuleId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupModulePageAccessResponseDTO> update(
            @PathVariable String id,
            @Valid @RequestBody GroupModulePageAccessRequestDTO requestDTO) {
        return ResponseEntity.ok(accessService.update(id, requestDTO));
    }

    @PatchMapping("/group/{groupId}/module/{appModuleId}/page/{pageId}/toggle")
    public ResponseEntity<GroupModulePageAccessResponseDTO> toggleAccess(
            @PathVariable String groupId,
            @PathVariable String appModuleId,
            @PathVariable String pageId) {
        return ResponseEntity.ok(accessService.toggleAccess(groupId, appModuleId, pageId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        accessService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/group/{groupId}/module/{appModuleId}/page/{pageId}")
    public ResponseEntity<Void> deleteByGroupModulePage(
            @PathVariable String groupId,
            @PathVariable String appModuleId,
            @PathVariable String pageId) {
        accessService.deleteByGroupModulePage(groupId, appModuleId, pageId);
        return ResponseEntity.noContent().build();
    }
}
