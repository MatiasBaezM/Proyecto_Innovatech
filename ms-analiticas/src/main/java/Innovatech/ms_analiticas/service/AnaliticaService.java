package Innovatech.ms_analiticas.service;

import Innovatech.ms_analiticas.entity.KpiProyecto;
import Innovatech.ms_analiticas.entity.ReporteAnalitico;
import Innovatech.ms_analiticas.repository.KpiProyectoRepository;
import Innovatech.ms_analiticas.repository.ReporteAnaliticoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnaliticaService {

    private final KpiProyectoRepository kpiRepository;
    private final ReporteAnaliticoRepository reporteRepository;

    // ==================== KPIs ====================

    public List<KpiProyecto> listarKpis() {
        return kpiRepository.findAll();
    }

    public List<KpiProyecto> obtenerKpisPorProyecto(Long proyectoId) {
        return kpiRepository.findByProyectoId(proyectoId);
    }

    public List<KpiProyecto> obtenerKpisPorPeriodo(String periodo) {
        return kpiRepository.findByPeriodo(periodo);
    }

    public KpiProyecto guardarKpi(KpiProyecto kpi) {
        return kpiRepository.save(kpi);
    }

    public void eliminarKpi(String id) {
        kpiRepository.deleteById(id);
    }

    // ==================== REPORTES ====================

    public List<ReporteAnalitico> listarReportes() {
        return reporteRepository.findAll();
    }

    public ReporteAnalitico obtenerReportePorId(String id) {
        return reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado con ID: " + id));
    }

    public List<ReporteAnalitico> obtenerReportesPorTipo(String tipo) {
        return reporteRepository.findByTipoReporte(tipo);
    }

    public ReporteAnalitico guardarReporte(ReporteAnalitico reporte) {
        return reporteRepository.save(reporte);
    }

    public void eliminarReporte(String id) {
        reporteRepository.deleteById(id);
    }
}
