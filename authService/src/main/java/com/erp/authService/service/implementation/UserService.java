package com.erp.authService.service.implementation;

import com.erp.authService.mapper.UserMapper;
import com.erp.authService.entity.User;
import com.erp.authService.payload.request.UserRequestDTO;
import com.erp.authService.payload.response.UserResponseDTO;
import com.erp.authService.repo.UserRepository;
import com.erp.authService.service.IUserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserResponseDTO createUser(UserRequestDTO requestDTO){
        User user = userMapper.toEntity(requestDTO);
        User savedUser = userRepository.save(user);

        return userMapper.toResponseDTO(savedUser);
    }

    @Override
    public List<UserResponseDTO> getAllUser() {
        return userRepository.findAll().stream().map(userMapper::toResponseDTO).toList();
    }

    @Override
    public UserResponseDTO getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return userMapper.toResponseDTO(user);
    }

    @Override
    public UserResponseDTO updateUser(String userId, UserRequestDTO requestDTO) {
        User user = userRepository.findById(userId).orElseThrow();

        // Check for duplicate username (excluding current user)
        if (!user.getUsername().equals(requestDTO.getUserName())
                && userRepository.existsByUsername(requestDTO.getUserName())) {
//            throw new DuplicateResourceException("User", "username", request.getUsername());
        }

        // Check for duplicate email (excluding current user)
        if (!user.getEmail().equals(requestDTO.getEmail())
                && userRepository.existsByEmail(requestDTO.getEmail())) {
//            throw new DuplicateResourceException("User", "email", request.getEmail());
        }

        userMapper.updateUser(user, requestDTO);

        return userMapper.toResponseDTO(userRepository.save(user));
    }
}
