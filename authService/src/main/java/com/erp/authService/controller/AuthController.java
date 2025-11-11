package com.erp.authService.controller;

import com.erp.authService.payload.request.LoginRequestDTO;
import com.erp.authService.payload.response.LoginResponseDTO;
import com.erp.authService.service.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthenticationService service;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> authenticateUser(@RequestBody LoginRequestDTO requestDTO){
        return new ResponseEntity<>(service.doLogin(requestDTO), HttpStatusCode.valueOf(200));
    }

}
