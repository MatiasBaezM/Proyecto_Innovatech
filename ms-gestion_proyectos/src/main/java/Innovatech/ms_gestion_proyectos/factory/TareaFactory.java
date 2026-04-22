package Innovatech.ms_gestion_proyectos.factory;

import Innovatech.ms_gestion_proyectos.dto.TareaDTO;
import Innovatech.ms_gestion_proyectos.entity.*;
import Innovatech.ms_gestion_proyectos.entity.Tarea.EstadoTarea;
import Innovatech.ms_gestion_proyectos.entity.Tarea.Prioridad;
import org.springframework.stereotype.Component;

/**
 * Factory Method Pattern para la creación de diferentes tipos de tareas.
 * Dependiendo del campo "tipo" en el DTO, instancia TareaTecnica o TareaGestion.
 */
@Component
public class TareaFactory {

    /**
     * Crea una instancia concreta de Tarea según el tipo especificado.
     *
     * @param dto      datos de la tarea
     * @param proyecto proyecto al que pertenece
     * @return instancia de TareaTecnica o TareaGestion
     * @throws IllegalArgumentException si el tipo no es válido
     */
    public Tarea crearTarea(TareaDTO dto, Proyecto proyecto) {
        return switch (dto.getTipo().toUpperCase()) {
            case "TECNICA" -> crearTareaTecnica(dto, proyecto);
            case "GESTION" -> crearTareaGestion(dto, proyecto);
            default -> throw new IllegalArgumentException(
                    "Tipo de tarea no válido: " + dto.getTipo() +
                    ". Los tipos válidos son: TECNICA, GESTION");
        };
    }

    private TareaTecnica crearTareaTecnica(TareaDTO dto, Proyecto proyecto) {
        TareaTecnica tarea = new TareaTecnica();
        setCommonFields(tarea, dto, proyecto);

        tarea.setLenguajeProgramacion(dto.getLenguajeProgramacion());
        tarea.setRequiereRevisionCodigo(
                dto.getRequiereRevisionCodigo() != null ? dto.getRequiereRevisionCodigo() : false);

        if (dto.getComplejidad() != null) {
            tarea.setComplejidad(TareaTecnica.Complejidad.valueOf(dto.getComplejidad().toUpperCase()));
        }

        return tarea;
    }

    private TareaGestion crearTareaGestion(TareaDTO dto, Proyecto proyecto) {
        TareaGestion tarea = new TareaGestion();
        setCommonFields(tarea, dto, proyecto);

        tarea.setTipoReunion(dto.getTipoReunion());
        tarea.setParticipantes(dto.getParticipantes());
        tarea.setRequiereAprobacion(
                dto.getRequiereAprobacion() != null ? dto.getRequiereAprobacion() : false);

        return tarea;
    }

    private void setCommonFields(Tarea tarea, TareaDTO dto, Proyecto proyecto) {
        tarea.setTitulo(dto.getTitulo());
        tarea.setDescripcion(dto.getDescripcion());
        tarea.setProyecto(proyecto);
        tarea.setPrioridad(Prioridad.valueOf(dto.getPrioridad().toUpperCase()));
        tarea.setEstado(dto.getEstado() != null
                ? EstadoTarea.valueOf(dto.getEstado().toUpperCase())
                : EstadoTarea.PENDIENTE);
    }
}
