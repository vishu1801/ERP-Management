package com.erp.authService.service;

import com.erp.authService.payload.request.UserRequestDTO;
import com.erp.authService.payload.response.UserResponseDTO;
import java.util.List;

public interface IUserService {
    UserResponseDTO createUser(UserRequestDTO requestDTO);

    List<UserResponseDTO> getAllUser();

    UserResponseDTO getUserById(String userId);

    UserResponseDTO updateUser(String userId, UserRequestDTO requestDTO);
}
