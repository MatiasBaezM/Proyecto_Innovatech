package Innovatech.ms_autenticacion.service;

import Innovatech.ms_autenticacion.dto.AuthResponse;
import Innovatech.ms_autenticacion.dto.LoginRequest;
import Innovatech.ms_autenticacion.dto.RegisterRequest;
import Innovatech.ms_autenticacion.entity.Rol;
import Innovatech.ms_autenticacion.entity.Usuario;
import Innovatech.ms_autenticacion.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Registra un nuevo usuario en la plataforma.
     */
    public AuthResponse register(RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado: " + request.getEmail());
        }

        Set<Rol> roles = Set.of(Rol.DEVELOPER); // Rol por defecto
        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            roles = request.getRoles().stream()
                    .map(r -> Rol.valueOf(r.toUpperCase()))
                    .collect(Collectors.toSet());
        }

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .activo(true)
                .build();

        usuarioRepository.save(usuario);

        UserDetails userDetails = buildUserDetails(usuario);
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("roles", usuario.getRoles().stream().map(Rol::name).toList());

        String token = jwtService.generateToken(extraClaims, userDetails);

        return AuthResponse.builder()
                .token(token)
                .tipo("Bearer")
                .id(usuario.getId())
                .email(usuario.getEmail())
                .nombre(usuario.getNombre())
                .roles(usuario.getRoles().stream().map(Rol::name).collect(Collectors.toSet()))
                .build();
    }

    /**
     * Autentica un usuario existente y genera un JWT.
     */
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        UserDetails userDetails = buildUserDetails(usuario);
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("roles", usuario.getRoles().stream().map(Rol::name).toList());

        String token = jwtService.generateToken(extraClaims, userDetails);

        return AuthResponse.builder()
                .token(token)
                .tipo("Bearer")
                .id(usuario.getId())
                .email(usuario.getEmail())
                .nombre(usuario.getNombre())
                .roles(usuario.getRoles().stream().map(Rol::name).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));
        return buildUserDetails(usuario);
    }

    private UserDetails buildUserDetails(Usuario usuario) {
        var authorities = usuario.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.name()))
                .collect(Collectors.toList());

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .authorities(authorities)
                .disabled(!usuario.getActivo())
                .build();
    }
}
