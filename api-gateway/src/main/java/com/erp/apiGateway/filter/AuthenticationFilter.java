package com.erp.apiGateway.filter;

import io.micrometer.common.util.StringUtils;
import java.util.Map;
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
    private final WebClient.Builder webClient;


    public AuthenticationFilter(RouteValidator routeValidator, WebClient.Builder webClient) {
        super(Config.class);
        this.routeValidator = routeValidator;
        this.webClient = webClient;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                //header contains token or not

                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Missing header");
                }

                String token = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

                if (!StringUtils.isEmpty(token) && token.startsWith("Bearer ")) {
                    token = token.substring(7);
                }

                return webClient.build().post()
                        .uri("lb://AUTH-SERVICE/auth/getUser")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .retrieve()
                        .bodyToMono(Map.class)
                        .flatMap(user -> {

                            String id = (String) user.get("id");

                            Map<String, Object> roleMap = (Map<String, Object>) user.get("role");
                            String roleName = (String) roleMap.get("name");

                            ServerHttpRequest mutated = exchange.getRequest().mutate()
                                    .header("X-USER-ID", id)
                                    .header("X-USER-ROLE", roleName)
                                    .build();

                            return chain.filter(exchange.mutate().request(mutated).build());

                        })
                        .onErrorResume(e -> {
                            throw new RuntimeException("Unauthorized: " + e.getMessage());
                        });


            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
