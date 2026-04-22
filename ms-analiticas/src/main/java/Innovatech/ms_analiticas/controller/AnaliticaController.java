package Innovatech.ms_analiticas.controller;

import Innovatech.ms_analiticas.entity.KpiProyecto;
import Innovatech.ms_analiticas.entity.ReporteAnalitico;
import Innovatech.ms_analiticas.service.AnaliticaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AnaliticaController {

    private final AnaliticaService analiticaService;

    // ==================== KPIs ====================

    @GetMapping("/kpis")
    public ResponseEntity<List<KpiProyecto>> listarKpis() {
        return ResponseEntity.ok(analiticaService.listarKpis());
    }

    @GetMapping("/kpis/proyecto/{proyectoId}")
    public ResponseEntity<List<KpiProyecto>> kpisPorProyecto(@PathVariable Long proyectoId) {
        return ResponseEntity.ok(analiticaService.obtenerKpisPorProyecto(proyectoId));
    }

    @GetMapping("/kpis/periodo/{periodo}")
    public ResponseEntity<List<KpiProyecto>> kpisPorPeriodo(@PathVariable String periodo) {
        return ResponseEntity.ok(analiticaService.obtenerKpisPorPeriodo(periodo));
    }

    @PostMapping("/kpis")
    public ResponseEntity<KpiProyecto> crearKpi(@RequestBody KpiProyecto kpi) {
        return ResponseEntity.status(HttpStatus.CREATED).body(analiticaService.guardarKpi(kpi));
    }

    @DeleteMapping("/kpis/{id}")
    public ResponseEntity<Void> eliminarKpi(@PathVariable String id) {
        analiticaService.eliminarKpi(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== REPORTES ====================

    @GetMapping("/analiticas/reportes")
    public ResponseEntity<List<ReporteAnalitico>> listarReportes() {
        return ResponseEntity.ok(analiticaService.listarReportes());
    }

    @GetMapping("/analiticas/reportes/{id}")
    public ResponseEntity<ReporteAnalitico> obtenerReporte(@PathVariable String id) {
        return ResponseEntity.ok(analiticaService.obtenerReportePorId(id));
    }

    @GetMapping("/analiticas/reportes/tipo/{tipo}")
    public ResponseEntity<List<ReporteAnalitico>> reportesPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(analiticaService.obtenerReportesPorTipo(tipo));
    }

    @PostMapping("/analiticas/reportes")
    public ResponseEntity<ReporteAnalitico> crearReporte(@RequestBody ReporteAnalitico reporte) {
        return ResponseEntity.status(HttpStatus.CREATED).body(analiticaService.guardarReporte(reporte));
    }

    @DeleteMapping("/analiticas/reportes/{id}")
    public ResponseEntity<Void> eliminarReporte(@PathVariable String id) {
        analiticaService.eliminarReporte(id);
        return ResponseEntity.noContent().build();
    }
}
