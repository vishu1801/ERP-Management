package com.erp.authService.service;

import com.erp.authService.payload.request.UserFilterRequestDTO;
import com.erp.authService.payload.request.UserRequestDTO;
import com.erp.authService.payload.response.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {

    UserResponseDTO createUser(UserRequestDTO requestDTO);

    Page<UserResponseDTO> getAllUsers(UserFilterRequestDTO filter, Pageable pageable);

    UserResponseDTO getUserById(String userId);

    UserResponseDTO updateUser(String userId, UserRequestDTO requestDTO);
}
