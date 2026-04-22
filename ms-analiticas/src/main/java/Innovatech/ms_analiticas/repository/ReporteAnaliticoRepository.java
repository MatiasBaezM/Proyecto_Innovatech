package Innovatech.ms_analiticas.repository;

import Innovatech.ms_analiticas.entity.ReporteAnalitico;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteAnaliticoRepository extends MongoRepository<ReporteAnalitico, String> {

    List<ReporteAnalitico> findByTipoReporte(String tipoReporte);

    List<ReporteAnalitico> findByPeriodo(String periodo);

    List<ReporteAnalitico> findByGeneradoPor(Long userId);
}
