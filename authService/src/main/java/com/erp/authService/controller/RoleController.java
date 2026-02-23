package com.erp.authService.controller;

import com.erp.authService.payload.request.RoleRequestDTO;
import com.erp.authService.payload.response.RoleResponseDTO;
import com.erp.authService.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class RoleController {

    private final IRoleService service;

    @PostMapping
    public ResponseEntity<RoleResponseDTO> assignModulesToRole(@RequestBody RoleRequestDTO requestDTO){
        return new ResponseEntity<>(service.assignModulesToRole(null, null), HttpStatusCode.valueOf(200));
    }

//    @GetMapping
//    public ResponseEntity<RoleResponseDTO> getRoleMapping(@RequestBody RoleRequestDTO requestDTO){
//        return new ResponseEntity<>(service.(requestDTO), HttpStatusCode.valueOf(200));
//    }


}
