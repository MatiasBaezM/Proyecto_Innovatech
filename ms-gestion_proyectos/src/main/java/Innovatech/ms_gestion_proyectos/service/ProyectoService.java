package Innovatech.ms_gestion_proyectos.service;

import Innovatech.ms_gestion_proyectos.dto.ProyectoDTO;
import Innovatech.ms_gestion_proyectos.entity.Proyecto;
import Innovatech.ms_gestion_proyectos.entity.Proyecto.EstadoProyecto;
import Innovatech.ms_gestion_proyectos.repository.ProyectoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProyectoService {

    private final ProyectoRepository proyectoRepository;

    public List<Proyecto> listarTodos() {
        return proyectoRepository.findAll();
    }

    public Proyecto obtenerPorId(Long id) {
        return proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con ID: " + id));
    }

    public List<Proyecto> buscarPorNombre(String nombre) {
        return proyectoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Proyecto> listarPorEstado(String estado) {
        EstadoProyecto estadoEnum = EstadoProyecto.valueOf(estado.toUpperCase());
        return proyectoRepository.findByEstado(estadoEnum);
    }

    public List<Proyecto> listarActivos() {
        return proyectoRepository.findProyectosActivos();
    }

    public Proyecto crear(ProyectoDTO dto) {
        Proyecto proyecto = Proyecto.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .fechaInicio(dto.getFechaInicio())
                .fechaFin(dto.getFechaFin())
                .presupuesto(dto.getPresupuesto())
                .estado(dto.getEstado() != null
                        ? EstadoProyecto.valueOf(dto.getEstado().toUpperCase())
                        : EstadoProyecto.PLANIFICACION)
                .build();
        return proyectoRepository.save(proyecto);
    }

    public Proyecto actualizar(Long id, ProyectoDTO dto) {
        Proyecto proyecto = obtenerPorId(id);

        proyecto.setNombre(dto.getNombre());
        proyecto.setDescripcion(dto.getDescripcion());
        proyecto.setFechaInicio(dto.getFechaInicio());
        proyecto.setFechaFin(dto.getFechaFin());
        proyecto.setPresupuesto(dto.getPresupuesto());

        if (dto.getEstado() != null) {
            proyecto.setEstado(EstadoProyecto.valueOf(dto.getEstado().toUpperCase()));
        }

        return proyectoRepository.save(proyecto);
    }

    public void eliminar(Long id) {
        Proyecto proyecto = obtenerPorId(id);
        proyectoRepository.delete(proyecto);
    }
}
