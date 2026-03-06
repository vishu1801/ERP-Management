package com.erp.authService.controller;

import com.erp.authService.payload.request.UserRequestDTO;
import com.erp.authService.payload.response.UserResponseDTO;
import com.erp.authService.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping("/user")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO requestDTO) {
        return new ResponseEntity<>(userService.createUser(requestDTO), HttpStatusCode.valueOf(201));
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserResponseDTO>> getUser() {
        return new ResponseEntity<>(userService.getAllUser(), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") String userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatusCode.valueOf(200));
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable("id") String userId,
            @RequestBody UserRequestDTO requestDTO) {
        return new ResponseEntity<>(userService.updateUser(userId, requestDTO), HttpStatusCode.valueOf(200));
    }
}
