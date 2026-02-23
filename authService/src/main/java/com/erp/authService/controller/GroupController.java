package com.erp.authService.controller;

import com.erp.authService.payload.request.GroupRequestDTO;
import com.erp.authService.payload.response.GroupResponseDTO;
import com.erp.authService.service.IGroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class GroupController {
    private final IGroupService groupService;

    @PostMapping
    public ResponseEntity<GroupResponseDTO> createGroup(@Valid @RequestBody GroupRequestDTO request) {
        return new ResponseEntity<>(groupService.createGroup(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponseDTO> getGroupById(@PathVariable String id) {
        return ResponseEntity.ok(groupService.getGroupById(id));
    }

    @GetMapping
    public ResponseEntity<List<GroupResponseDTO>> getAllGroups() {
        return ResponseEntity.ok(groupService.getAllGroups());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupResponseDTO> updateGroup(
            @PathVariable String id,
            @Valid @RequestBody GroupRequestDTO request) {
        return ResponseEntity.ok(groupService.updateGroup(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable String id) {
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/modules")
    public ResponseEntity<GroupResponseDTO> assignModules(
            @PathVariable String id,
            @RequestBody Set<Long> moduleIds) {
        return ResponseEntity.ok(groupService.assignModulesToGroup(id, moduleIds));
    }

    @DeleteMapping("/{id}/modules")
    public ResponseEntity<GroupResponseDTO> removeModules(
            @PathVariable String id,
            @RequestBody Set<Long> moduleIds) {
        return ResponseEntity.ok(groupService.removeModulesFromGroup(id, moduleIds));
    }
}
