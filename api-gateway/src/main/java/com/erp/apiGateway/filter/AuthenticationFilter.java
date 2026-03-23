package com.erp.apiGateway.filter;

import io.micrometer.common.util.StringUtils;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RouteValidator routeValidator;
    private final WebClient plainWebClient;

    @Value("${AUTH_SERVICE_URL:lb://AUTH-SERVICE}")
    private String authServiceUrl;

    public AuthenticationFilter(RouteValidator routeValidator, WebClient plainWebClient) {
        super(Config.class);
        this.routeValidator = routeValidator;
        this.plainWebClient = plainWebClient;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();
            System.out.println("[Gateway] Incoming request: " + exchange.getRequest().getMethod() + " " + path);
            System.out.println("[Gateway] AUTH_SERVICE_URL = " + authServiceUrl);

            if (routeValidator.isSecured.test(exchange.getRequest())) {
                System.out.println("[Gateway] Route is SECURED, validating token...");

                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    System.out.println("[Gateway] ERROR: Missing Authorization header");
                    throw new RuntimeException("Missing header");
                }

                String token = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

                if (!StringUtils.isEmpty(token) && token.startsWith("Bearer ")) {
                    token = token.substring(7);
                }

                System.out.println("[Gateway] Calling auth service for token validation: " + authServiceUrl + "/auth/getUser");
                return plainWebClient.post()
                        .uri(authServiceUrl + "/auth/getUser")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .retrieve()
                        .bodyToMono(Map.class)
                        .flatMap(user -> {
                            String id = (String) user.get("id");
                            System.out.println("[Gateway] Token valid, user id: " + id + ", forwarding request...");
                            ServerHttpRequest mutated = exchange.getRequest().mutate()
                                    .header("X-USER-ID", id)
                                    .build();
                            return chain.filter(exchange.mutate().request(mutated).build());
                        })
                        .onErrorResume(e -> {
                            System.out.println("[Gateway] Token validation FAILED: " + e.getMessage());
                            throw new RuntimeException("Unauthorized: " + e.getMessage());
                        });
            }
            System.out.println("[Gateway] Route is OPEN, forwarding directly...");
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
