package com.erp.authService.service.implementation;

import com.erp.authService.entity.Group;
import com.erp.authService.entity.User;
import com.erp.authService.mapper.UserMapper;
import com.erp.authService.payload.request.UserFilterRequestDTO;
import com.erp.authService.payload.request.UserRequestDTO;
import com.erp.authService.payload.response.UserResponseDTO;
import com.erp.authService.repo.GroupRepository;
import com.erp.authService.repo.UserRepository;
import com.erp.authService.service.IUserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        User user = userMapper.toEntity(requestDTO);

        if (requestDTO.getGroupId() != null && !requestDTO.getGroupId().isBlank()) {
            user.setGroup(findGroupById(requestDTO.getGroupId()));
        }

        user.setPassword(passwordEncoder.encode("Target@123"));
        return userMapper.toResponseDTO(userRepository.save(user));
    }

    @Override
    public Page<UserResponseDTO> getAllUsers(UserFilterRequestDTO filter, Pageable pageable) {
        Specification<User> spec = buildSpecification(filter);
        return userRepository.findAll(spec, pageable)
                .map(userMapper::toResponseDTO);
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

        // ─── Only update group if groupId is explicitly provided ──────────────
        if (requestDTO.getGroupId() != null && !requestDTO.getGroupId().isBlank()) {
            user.setGroup(findGroupById(requestDTO.getGroupId()));
        }

        return userMapper.toResponseDTO(userRepository.save(user));
    }

    // ─── Specification Builder ────────────────────────────────────────────────

    private Specification<User> buildSpecification(UserFilterRequestDTO filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getFirstName() != null && !filter.getFirstName().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("firstName")),
                        "%" + filter.getFirstName().toLowerCase() + "%"));
            }

            if (filter.getLastName() != null && !filter.getLastName().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("lastName")),
                        "%" + filter.getLastName().toLowerCase() + "%"));
            }

            if (filter.getEmail() != null && !filter.getEmail().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("email")),
                        "%" + filter.getEmail().toLowerCase() + "%"));
            }

            if (filter.getUserName() != null && !filter.getUserName().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("username")),
                        "%" + filter.getUserName().toLowerCase() + "%"));
            }

            if (filter.getUserType() != null) {
                predicates.add(cb.equal(root.get("userType"), filter.getUserType()));
            }

            if (filter.getGroupId() != null && !filter.getGroupId().isBlank()) {
                predicates.add(cb.equal(root.get("group").get("id"), filter.getGroupId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    // ───Helpers ─────────────────────────────────────────────────────────────

    private User findUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    private Group findGroupById(String id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + id));
    }
}
