package Innovatech.ms_recursos_colaboraciones.controller;

import Innovatech.ms_recursos_colaboraciones.dto.AsignacionDTO;
import Innovatech.ms_recursos_colaboraciones.dto.RecursoDTO;
import Innovatech.ms_recursos_colaboraciones.entity.Asignacion;
import Innovatech.ms_recursos_colaboraciones.entity.Recurso;
import Innovatech.ms_recursos_colaboraciones.service.AsignacionService;
import Innovatech.ms_recursos_colaboraciones.service.RecursoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RecursoController {

    private final RecursoService recursoService;
    private final AsignacionService asignacionService;

    // ==================== RECURSOS ====================

    @GetMapping("/recursos")
    public ResponseEntity<List<Recurso>> listarRecursos() {
        return ResponseEntity.ok(recursoService.listarTodos());
    }

    @GetMapping("/recursos/{id}")
    public ResponseEntity<Recurso> obtenerRecurso(@PathVariable Long id) {
        return ResponseEntity.ok(recursoService.obtenerPorId(id));
    }

    @GetMapping("/recursos/disponibles")
    public ResponseEntity<List<Recurso>> listarDisponibles() {
        return ResponseEntity.ok(recursoService.listarDisponibles());
    }

    @GetMapping("/recursos/tipo/{tipo}")
    public ResponseEntity<List<Recurso>> listarPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(recursoService.listarPorTipo(tipo));
    }

    @PostMapping("/recursos")
    public ResponseEntity<Recurso> crearRecurso(@Valid @RequestBody RecursoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recursoService.crear(dto));
    }

    @PutMapping("/recursos/{id}")
    public ResponseEntity<Recurso> actualizarRecurso(@PathVariable Long id, @Valid @RequestBody RecursoDTO dto) {
        return ResponseEntity.ok(recursoService.actualizar(id, dto));
    }

    @DeleteMapping("/recursos/{id}")
    public ResponseEntity<Void> eliminarRecurso(@PathVariable Long id) {
        recursoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== ASIGNACIONES ====================

    @GetMapping("/asignaciones/proyecto/{proyectoId}")
    public ResponseEntity<List<Asignacion>> listarPorProyecto(@PathVariable Long proyectoId) {
        return ResponseEntity.ok(asignacionService.listarPorProyecto(proyectoId));
    }

    @GetMapping("/asignaciones/recurso/{recursoId}")
    public ResponseEntity<List<Asignacion>> listarPorRecurso(@PathVariable Long recursoId) {
        return ResponseEntity.ok(asignacionService.listarPorRecurso(recursoId));
    }

    @PostMapping("/asignaciones")
    public ResponseEntity<Asignacion> asignarRecurso(@Valid @RequestBody AsignacionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(asignacionService.asignarRecurso(dto));
    }

    @PatchMapping("/asignaciones/{id}/estado")
    public ResponseEntity<Asignacion> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        return ResponseEntity.ok(asignacionService.cambiarEstado(id, estado));
    }

    @DeleteMapping("/asignaciones/{id}")
    public ResponseEntity<Void> eliminarAsignacion(@PathVariable Long id) {
        asignacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
