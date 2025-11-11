package com.erp.authService.service;

import com.erp.authService.payload.request.LoginRequestDTO;
import com.erp.authService.payload.response.LoginResponseDTO;

public interface IAuthenticationService {
    LoginResponseDTO doLogin( LoginRequestDTO requestDTO);
}
