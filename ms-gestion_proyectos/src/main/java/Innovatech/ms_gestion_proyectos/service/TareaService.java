package Innovatech.ms_gestion_proyectos.service;

import Innovatech.ms_gestion_proyectos.dto.TareaDTO;
import Innovatech.ms_gestion_proyectos.entity.Proyecto;
import Innovatech.ms_gestion_proyectos.entity.Tarea;
import Innovatech.ms_gestion_proyectos.factory.TareaFactory;
import Innovatech.ms_gestion_proyectos.repository.TareaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TareaService {

    private final TareaRepository tareaRepository;
    private final TareaFactory tareaFactory;
    private final ProyectoService proyectoService;

    public List<Tarea> listarPorProyecto(Long proyectoId) {
        return tareaRepository.findByProyectoId(proyectoId);
    }

    public Tarea obtenerPorId(Long id) {
        return tareaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con ID: " + id));
    }

    /**
     * Crea una tarea usando el Factory Method.
     * El tipo de tarea (TECNICA o GESTION) se determina por el campo "tipo" del DTO.
     */
    public Tarea crear(TareaDTO dto) {
        Proyecto proyecto = proyectoService.obtenerPorId(dto.getProyectoId());
        Tarea tarea = tareaFactory.crearTarea(dto, proyecto);
        return tareaRepository.save(tarea);
    }

    public Tarea cambiarEstado(Long id, String nuevoEstado) {
        Tarea tarea = obtenerPorId(id);
        tarea.setEstado(Tarea.EstadoTarea.valueOf(nuevoEstado.toUpperCase()));
        return tareaRepository.save(tarea);
    }

    public void eliminar(Long id) {
        Tarea tarea = obtenerPorId(id);
        tareaRepository.delete(tarea);
    }
}
