package Innovatech.ms_api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Configuración de seguridad reactiva para el API Gateway.
 * Valida JWT entrantes y permite acceso público a endpoints de autenticación.
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        // Endpoints públicos (login, registro)
                        .pathMatchers("/api/auth/**").permitAll()
                        // Actuator health para health checks de K8s
                        .pathMatchers("/actuator/health").permitAll()
                        // Fallback endpoints
                        .pathMatchers("/fallback/**").permitAll()
                        // Todo lo demás requiere autenticación
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> {})
                );

        return http.build();
    }
}
