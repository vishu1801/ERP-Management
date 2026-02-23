package com.erp.authService.service.implementation;

import com.erp.authService.entity.Module;
import com.erp.authService.entity.Role;
import com.erp.authService.payload.response.RoleResponseDTO;
import com.erp.authService.repo.ModuleRepository;
import com.erp.authService.repo.RoleRepository;
import com.erp.authService.service.IRoleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;

    private final ModuleRepository moduleRepository;

    @Override
    public RoleResponseDTO assignModulesToRole(String roleId, List<String> moduleIds) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        List<Module> modules = moduleRepository.findAllById(moduleIds);

        if (modules.isEmpty())
            throw new RuntimeException("No modules found for given IDs");

        role.setModules(modules);
        roleRepository.save(role);
        return new RoleResponseDTO();
    }
}
