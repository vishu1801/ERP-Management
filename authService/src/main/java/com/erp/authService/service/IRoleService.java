package com.erp.authService.service;

import com.erp.authService.payload.response.RoleResponseDTO;
import java.util.List;

public interface IRoleService {
    RoleResponseDTO assignModulesToRole(String roleId, List<String> moduleIds);
}
