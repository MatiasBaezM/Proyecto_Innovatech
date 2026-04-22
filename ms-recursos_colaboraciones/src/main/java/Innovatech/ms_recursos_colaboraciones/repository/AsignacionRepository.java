package Innovatech.ms_recursos_colaboraciones.repository;

import Innovatech.ms_recursos_colaboraciones.entity.Asignacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsignacionRepository extends JpaRepository<Asignacion, Long> {

    List<Asignacion> findByProyectoId(Long proyectoId);

    List<Asignacion> findByRecursoId(Long recursoId);

    List<Asignacion> findByRecursoIdAndEstado(Long recursoId, Asignacion.EstadoAsignacion estado);

    long countByRecursoIdAndEstado(Long recursoId, Asignacion.EstadoAsignacion estado);
}
