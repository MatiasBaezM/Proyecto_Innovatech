package Innovatech.ms_analiticas.repository;

import Innovatech.ms_analiticas.entity.KpiProyecto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KpiProyectoRepository extends MongoRepository<KpiProyecto, String> {

    List<KpiProyecto> findByProyectoId(Long proyectoId);

    List<KpiProyecto> findByProyectoIdAndPeriodo(Long proyectoId, String periodo);

    List<KpiProyecto> findByPeriodo(String periodo);
}
