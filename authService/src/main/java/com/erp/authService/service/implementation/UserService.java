package com.erp.authService.service.implementation;

import com.erp.authService.entity.Group;
import com.erp.authService.entity.User;
import com.erp.authService.mapper.UserMapper;
import com.erp.authService.payload.request.UserRequestDTO;
import com.erp.authService.payload.response.UserResponseDTO;
import com.erp.authService.repo.GroupRepository;
import com.erp.authService.repo.UserRepository;
import com.erp.authService.service.IUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        User user = userMapper.toEntity(requestDTO);

        if (requestDTO.getGroupId() != null) {
            user.setGroup(findGroupById(requestDTO.getGroupId()));
        }

        return userMapper.toResponseDTO(userRepository.save(user));
    }

    @Override
    public List<UserResponseDTO> getAllUser() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDTO)
                .toList();
    }

    @Override
    public UserResponseDTO getUserById(String userId) {
        return userMapper.toResponseDTO(findUserById(userId));
    }

    @Override
    public UserResponseDTO updateUser(String userId, UserRequestDTO requestDTO) {
        User user = findUserById(userId);

        if (!user.getUsername().equals(requestDTO.getUserName())
                && userRepository.existsByUsername(requestDTO.getUserName())) {
            // throw new DuplicateResourceException("User", "username", requestDTO.getUserName());
        }

        if (!user.getEmail().equals(requestDTO.getEmail())
                && userRepository.existsByEmail(requestDTO.getEmail())) {
            // throw new DuplicateResourceException("User", "email", requestDTO.getEmail());
        }

        userMapper.updateUser(user, requestDTO);

        if (requestDTO.getGroupId() != null && !requestDTO.getGroupId().isBlank()) {
            user.setGroup(findGroupById(requestDTO.getGroupId()));
        }

        return userMapper.toResponseDTO(userRepository.save(user));
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────

    private User findUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    private Group findGroupById(String id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + id));
    }
}
