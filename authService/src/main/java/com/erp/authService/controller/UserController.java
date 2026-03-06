package com.erp.authService.controller;

import com.erp.authService.enums.UserType;
import com.erp.authService.payload.request.UserFilterRequestDTO;
import com.erp.authService.payload.request.UserRequestDTO;
import com.erp.authService.payload.response.UserResponseDTO;
import com.erp.authService.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping("/user")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO requestDTO) {
        return new ResponseEntity<>(userService.createUser(requestDTO), HttpStatusCode.valueOf(201));
    }

    /**
     * GET /api/v1/user
     *
     * Query Params (all optional):
     *   - firstName     : filter by first name (partial match)
     *   - lastName      : filter by last name (partial match)
     *   - email         : filter by email (partial match)
     *   - userName      : filter by username (partial match)
     *   - userType      : filter by type (STUDENT, FAMILY, STAFF, ADMIN, GUEST)
     *   - groupId       : filter by assigned group
     *   - page          : page number, default 0
     *   - size          : page size, default 10
     *   - sort          : sort field and direction e.g. firstName,asc
     *
     * Example:
     *   GET /api/v1/user?userType=STUDENT&email=john&page=0&size=10&sort=firstName,asc
     */
    @GetMapping("/user")
    public ResponseEntity<Page<UserResponseDTO>> getUsers(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) UserType userType,
            @RequestParam(required = false) String groupId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,asc") String[] sort) {

        // ─── Build sort direction ─────────────────────────────────────────────
        Sort.Direction direction = sort.length > 1 && sort[1].equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort[0]));

        // ─── Build filter object ──────────────────────────────────────────────
        UserFilterRequestDTO filter = UserFilterRequestDTO.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .userName(userName)
                .userType(userType)
                .groupId(groupId)
                .build();

        return ResponseEntity.ok(userService.getAllUsers(filter, pageable));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") String userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatusCode.valueOf(200));
    }

    @PatchMapping("/user/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable("id") String userId,
            @RequestBody UserRequestDTO requestDTO) {
        return new ResponseEntity<>(userService.updateUser(userId, requestDTO), HttpStatusCode.valueOf(200));
    }
}
