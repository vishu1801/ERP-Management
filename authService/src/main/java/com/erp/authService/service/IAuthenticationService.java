package com.erp.authService.service;

import com.erp.authService.model.User;
import com.erp.authService.payload.request.LoginRequestDTO;
import com.erp.authService.payload.response.LoginResponseDTO;
import com.erp.authService.payload.response.UserResponseDTO;

public interface IAuthenticationService {
    LoginResponseDTO doLogin( LoginRequestDTO requestDTO);

    User validateToken(String token);
}
