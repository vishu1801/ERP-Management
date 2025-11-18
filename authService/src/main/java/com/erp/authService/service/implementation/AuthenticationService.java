package com.erp.authService.service.implementation;

import com.erp.authService.helper.JwtTokenHelper;
import com.erp.authService.model.User;
import com.erp.authService.payload.request.LoginRequestDTO;
import com.erp.authService.payload.response.LoginResponseDTO;
import com.erp.authService.payload.response.UserResponseDTO;
import com.erp.authService.service.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenHelper jwtTokenHelper;

    @Override
    public LoginResponseDTO doLogin(LoginRequestDTO requestDTO){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestDTO.getUsername(), requestDTO.getPassword()));
            String token = jwtTokenHelper.generateToken((User) authentication.getPrincipal());
            return new LoginResponseDTO(token);
        } catch(Exception e){
            throw new IllegalArgumentException("Invalid Credentials");
        }
    }

    @Override
    public User validateToken(String token) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User user) {
                return user;
            } else {
                // Principal is not an instance of User, throw CustomException
                throw new RuntimeException("");
            }
        } else {
            // No authentication or user is not authenticated, throw CustomException
            throw new RuntimeException("");
        }
    }

}
