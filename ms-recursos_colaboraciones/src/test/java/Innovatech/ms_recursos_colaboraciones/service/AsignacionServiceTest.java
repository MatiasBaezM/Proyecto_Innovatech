package Innovatech.ms_recursos_colaboraciones.service;

import Innovatech.ms_recursos_colaboraciones.dto.AsignacionDTO;
import Innovatech.ms_recursos_colaboraciones.entity.Asignacion;
import Innovatech.ms_recursos_colaboraciones.entity.Asignacion.EstadoAsignacion;
import Innovatech.ms_recursos_colaboraciones.entity.Recurso;
import Innovatech.ms_recursos_colaboraciones.entity.Recurso.TipoRecurso;
import Innovatech.ms_recursos_colaboraciones.repository.AsignacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Prueba unitaria para AsignacionService.
 * Usa Mockito para mockear el repositorio JPA y evitar dependencias de base de datos.
 */
@ExtendWith(MockitoExtension.class)
class AsignacionServiceTest {

    @Mock
    private AsignacionRepository asignacionRepository;

    @Mock
    private RecursoService recursoService;

    @InjectMocks
    private AsignacionService asignacionService;

    private Recurso recursoDisponible;
    private Recurso recursoNoDisponible;
    private AsignacionDTO asignacionDTO;

    @BeforeEach
    void setUp() {
        recursoDisponible = Recurso.builder()
                .id(1L)
                .nombre("Juan Pérez")
                .tipo(TipoRecurso.DESARROLLADOR)
                .disponible(true)
                .costoHora(new BigDecimal("50.00"))
                .especialidad("Java/Spring Boot")
                .email("juan@innovatech.com")
                .build();

        recursoNoDisponible = Recurso.builder()
                .id(2L)
                .nombre("María López")
                .tipo(TipoRecurso.DISENADOR)
                .disponible(false)
                .costoHora(new BigDecimal("45.00"))
                .especialidad("UI/UX")
                .email("maria@innovatech.com")
                .build();

        asignacionDTO = AsignacionDTO.builder()
                .recursoId(1L)
                .proyectoId(100L)
                .horasAsignadas(40)
                .fechaInicio(LocalDate.of(2026, 5, 1))
                .fechaFin(LocalDate.of(2026, 6, 30))
                .rol("Desarrollador Backend")
                .build();
    }

    @Test
    @DisplayName("Debe asignar recurso exitosamente cuando está disponible y tiene menos de 3 asignaciones activas")
    void asignarRecurso_recursoDisponible_debeCrearAsignacion() {
        // Arrange
        when(recursoService.obtenerPorId(1L)).thenReturn(recursoDisponible);
        when(asignacionRepository.countByRecursoIdAndEstado(1L, EstadoAsignacion.ACTIVA)).thenReturn(1L);
        when(asignacionRepository.save(any(Asignacion.class))).thenAnswer(invocation -> {
            Asignacion saved = invocation.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        // Act
        Asignacion resultado = asignacionService.asignarRecurso(asignacionDTO);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getProyectoId()).isEqualTo(100L);
        assertThat(resultado.getHorasAsignadas()).isEqualTo(40);
        assertThat(resultado.getEstado()).isEqualTo(EstadoAsignacion.ACTIVA);
        assertThat(resultado.getRecurso().getNombre()).isEqualTo("Juan Pérez");

        verify(recursoService, times(1)).obtenerPorId(1L);
        verify(asignacionRepository, times(1)).countByRecursoIdAndEstado(1L, EstadoAsignacion.ACTIVA);
        verify(asignacionRepository, times(1)).save(any(Asignacion.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el recurso no está disponible")
    void asignarRecurso_recursoNoDisponible_debeLanzarExcepcion() {
        // Arrange
        asignacionDTO.setRecursoId(2L);
        when(recursoService.obtenerPorId(2L)).thenReturn(recursoNoDisponible);

        // Act & Assert
        assertThatThrownBy(() -> asignacionService.asignarRecurso(asignacionDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("no está disponible");

        verify(asignacionRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el recurso tiene 3 o más asignaciones activas")
    void asignarRecurso_maximoAsignacionesAlcanzado_debeLanzarExcepcion() {
        // Arrange
        when(recursoService.obtenerPorId(1L)).thenReturn(recursoDisponible);
        when(asignacionRepository.countByRecursoIdAndEstado(1L, EstadoAsignacion.ACTIVA)).thenReturn(3L);

        // Act & Assert
        assertThatThrownBy(() -> asignacionService.asignarRecurso(asignacionDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("asignaciones activas");

        verify(asignacionRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe cambiar el estado de una asignación correctamente")
    void cambiarEstado_asignacionExistente_debeCambiarEstado() {
        // Arrange
        Asignacion asignacion = Asignacion.builder()
                .id(1L)
                .recurso(recursoDisponible)
                .proyectoId(100L)
                .horasAsignadas(40)
                .fechaInicio(LocalDate.of(2026, 5, 1))
                .estado(EstadoAsignacion.ACTIVA)
                .build();

        when(asignacionRepository.findById(1L)).thenReturn(java.util.Optional.of(asignacion));
        when(asignacionRepository.save(any(Asignacion.class))).thenReturn(asignacion);

        // Act
        Asignacion resultado = asignacionService.cambiarEstado(1L, "FINALIZADA");

        // Assert
        assertThat(resultado.getEstado()).isEqualTo(EstadoAsignacion.FINALIZADA);
        verify(asignacionRepository, times(1)).save(asignacion);
    }
}
