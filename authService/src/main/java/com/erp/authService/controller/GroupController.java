package com.erp.authService.controller;

import com.erp.authService.payload.request.GroupRequestDTO;
import com.erp.authService.payload.response.GroupResponseDTO;
import com.erp.authService.service.IGroupService;
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
import java.util.Set;

@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
@Validated
public class GroupController {

    private final IGroupService groupService;

    @PostMapping
    public ResponseEntity<GroupResponseDTO> create(@Valid @RequestBody GroupRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.create(requestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponseDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(groupService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<GroupResponseDTO>> getAll() {
        return ResponseEntity.ok(groupService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupResponseDTO> update(@PathVariable String id,
                                                    @Valid @RequestBody GroupRequestDTO requestDTO) {
        return ResponseEntity.ok(groupService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        groupService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/modules/add")
    public ResponseEntity<GroupResponseDTO> addModules(@PathVariable String id,
                                                        @RequestBody Set<String> appModuleIds) {
        return ResponseEntity.ok(groupService.addModules(id, appModuleIds));
    }

    @PatchMapping("/{id}/modules/remove")
    public ResponseEntity<GroupResponseDTO> removeModules(@PathVariable String id,
                                                           @RequestBody Set<String> appModuleIds) {
        return ResponseEntity.ok(groupService.removeModules(id, appModuleIds));
    }
}
