package Innovatech.ms_recursos_colaboraciones.repository;

import Innovatech.ms_recursos_colaboraciones.entity.Recurso;
import Innovatech.ms_recursos_colaboraciones.entity.Recurso.TipoRecurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecursoRepository extends JpaRepository<Recurso, Long> {

    List<Recurso> findByDisponibleTrue();

    List<Recurso> findByTipo(TipoRecurso tipo);

    List<Recurso> findByEspecialidadContainingIgnoreCase(String especialidad);
}
