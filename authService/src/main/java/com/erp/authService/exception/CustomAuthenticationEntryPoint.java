package com.erp.authService.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // Set the response status to 401 Unauthorized
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Set the Content-Type header to application/json
        response.setContentType("application/json");

//        ErrorMessage error = new ErrorMessage(
//                Timestamp.from(Instant.now()),
//                HttpServletResponse.SC_UNAUTHORIZED,
//                authException.getLocalizedMessage(),
//                "Authentication Failed!",
//                request.getRequestURI(), null);

        // Convert the response body to JSON using ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonResponse = objectMapper.writeValueAsString(error);

        // Write the JSON response to the output stream
//        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
        response.getWriter().close();
    }
}

