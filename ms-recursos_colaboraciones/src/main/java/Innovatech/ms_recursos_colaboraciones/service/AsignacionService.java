package Innovatech.ms_recursos_colaboraciones.service;

import Innovatech.ms_recursos_colaboraciones.dto.AsignacionDTO;
import Innovatech.ms_recursos_colaboraciones.entity.Asignacion;
import Innovatech.ms_recursos_colaboraciones.entity.Asignacion.EstadoAsignacion;
import Innovatech.ms_recursos_colaboraciones.entity.Recurso;
import Innovatech.ms_recursos_colaboraciones.repository.AsignacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AsignacionService {

    private final AsignacionRepository asignacionRepository;
    private final RecursoService recursoService;

    public List<Asignacion> listarPorProyecto(Long proyectoId) {
        return asignacionRepository.findByProyectoId(proyectoId);
    }

    public List<Asignacion> listarPorRecurso(Long recursoId) {
        return asignacionRepository.findByRecursoId(recursoId);
    }

    public Asignacion obtenerPorId(Long id) {
        return asignacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada con ID: " + id));
    }

    /**
     * Asigna un recurso a un proyecto, validando la disponibilidad del recurso.
     */
    public Asignacion asignarRecurso(AsignacionDTO dto) {
        Recurso recurso = recursoService.obtenerPorId(dto.getRecursoId());

        // Validar que el recurso esté disponible
        if (!recurso.getDisponible()) {
            throw new RuntimeException(
                    "El recurso '" + recurso.getNombre() + "' no está disponible para asignación");
        }

        // Validar que no tenga demasiadas asignaciones activas
        long asignacionesActivas = asignacionRepository
                .countByRecursoIdAndEstado(recurso.getId(), EstadoAsignacion.ACTIVA);
        if (asignacionesActivas >= 3) {
            throw new RuntimeException(
                    "El recurso '" + recurso.getNombre() + "' ya tiene " + asignacionesActivas +
                    " asignaciones activas (máximo 3)");
        }

        Asignacion asignacion = Asignacion.builder()
                .recurso(recurso)
                .proyectoId(dto.getProyectoId())
                .horasAsignadas(dto.getHorasAsignadas())
                .fechaInicio(dto.getFechaInicio())
                .fechaFin(dto.getFechaFin())
                .rol(dto.getRol())
                .estado(EstadoAsignacion.ACTIVA)
                .build();

        return asignacionRepository.save(asignacion);
    }

    public Asignacion cambiarEstado(Long id, String nuevoEstado) {
        Asignacion asignacion = obtenerPorId(id);
        asignacion.setEstado(EstadoAsignacion.valueOf(nuevoEstado.toUpperCase()));
        return asignacionRepository.save(asignacion);
    }

    public void eliminar(Long id) {
        Asignacion asignacion = obtenerPorId(id);
        asignacionRepository.delete(asignacion);
    }
}
