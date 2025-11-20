package com.erp.studentInformationSystem.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
public class UserContextFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;

        // Read the headers set by Gateway
        String userId = request.getHeader("X-USER-ID");
        String role = request.getHeader("X-USER-ROLE");

        // Store in ThreadLocal
        RequestContext context = RequestContext.getCurrentContext();
        context.setUserId(userId);
        context.setUserRole(role);

        try {
            chain.doFilter(req, res);
        } finally {
            RequestContext.clear();
        }
    }
}
