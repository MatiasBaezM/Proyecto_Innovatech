package Innovatech.ms_api_gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Controlador de fallback para Circuit Breaker.
 * Responde cuando un microservicio backend no está disponible.
 */
@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/auth")
    public Mono<ResponseEntity<Map<String, String>>> authFallback() {
        return Mono.just(ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        "error", "Servicio de autenticación no disponible",
                        "message", "Por favor, intente nuevamente en unos momentos"
                )));
    }

    @GetMapping("/proyectos")
    public Mono<ResponseEntity<Map<String, String>>> proyectosFallback() {
        return Mono.just(ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        "error", "Servicio de proyectos no disponible",
                        "message", "Por favor, intente nuevamente en unos momentos"
                )));
    }

    @GetMapping("/recursos")
    public Mono<ResponseEntity<Map<String, String>>> recursosFallback() {
        return Mono.just(ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        "error", "Servicio de recursos no disponible",
                        "message", "Por favor, intente nuevamente en unos momentos"
                )));
    }

    @GetMapping("/analiticas")
    public Mono<ResponseEntity<Map<String, String>>> analiticasFallback() {
        return Mono.just(ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        "error", "Servicio de analíticas no disponible",
                        "message", "Por favor, intente nuevamente en unos momentos"
                )));
    }
}
