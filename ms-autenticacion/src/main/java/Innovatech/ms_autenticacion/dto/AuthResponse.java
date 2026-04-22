package Innovatech.ms_autenticacion.dto;

import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    private String token;
    private String tipo;
    private Long id;
    private String email;
    private String nombre;
    private Set<String> roles;
}
