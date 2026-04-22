package Innovatech.ms_gestion_proyectos.repository;

import Innovatech.ms_gestion_proyectos.entity.Proyecto;
import Innovatech.ms_gestion_proyectos.entity.Proyecto.EstadoProyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {

    List<Proyecto> findByEstado(EstadoProyecto estado);

    List<Proyecto> findByNombreContainingIgnoreCase(String nombre);

    @Query("SELECT p FROM Proyecto p WHERE p.estado != 'CANCELADO' ORDER BY p.createdAt DESC")
    List<Proyecto> findProyectosActivos();

    @Query("SELECT COUNT(p) FROM Proyecto p WHERE p.estado = :estado")
    long countByEstado(@Param("estado") EstadoProyecto estado);
}
