package com.erp.apiGateway.filter;

import io.micrometer.common.util.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RouteValidator routeValidator;

    public AuthenticationFilter(RouteValidator routeValidator) {
        super(Config.class);
        this.routeValidator = routeValidator;
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
                try{
                    //jwt decoder
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
