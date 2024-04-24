package vn.edu.iuh.fit.configs;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("quoting", r -> r.path("/api/quoting/**").uri("http://localhost:8081"))
                .route("login", r -> r.path("/api/auth/login").uri("http://localhost:8081"))
                .route("auth", r -> r.path("/api/auth/**").uri("http://localhost:8081/api"))
                .build();
    }
}
