package Innovatech.ms_gestion_proyectos.repository;

import Innovatech.ms_gestion_proyectos.entity.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {

    List<Tarea> findByProyectoId(Long proyectoId);

    List<Tarea> findByProyectoIdAndEstado(Long proyectoId, Tarea.EstadoTarea estado);

    long countByProyectoId(Long proyectoId);
}
