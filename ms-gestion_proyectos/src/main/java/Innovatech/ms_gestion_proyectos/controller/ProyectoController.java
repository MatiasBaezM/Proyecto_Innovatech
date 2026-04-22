package Innovatech.ms_gestion_proyectos.controller;

import Innovatech.ms_gestion_proyectos.dto.ProyectoDTO;
import Innovatech.ms_gestion_proyectos.dto.TareaDTO;
import Innovatech.ms_gestion_proyectos.entity.Proyecto;
import Innovatech.ms_gestion_proyectos.entity.Tarea;
import Innovatech.ms_gestion_proyectos.service.ProyectoService;
import Innovatech.ms_gestion_proyectos.service.TareaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
@RequiredArgsConstructor
public class ProyectoController {

    private final ProyectoService proyectoService;
    private final TareaService tareaService;

    // ==================== PROYECTOS ====================

    @GetMapping
    public ResponseEntity<List<Proyecto>> listarTodos() {
        return ResponseEntity.ok(proyectoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(proyectoService.obtenerPorId(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Proyecto>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(proyectoService.buscarPorNombre(nombre));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Proyecto>> listarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(proyectoService.listarPorEstado(estado));
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Proyecto>> listarActivos() {
        return ResponseEntity.ok(proyectoService.listarActivos());
    }

    @PostMapping
    public ResponseEntity<Proyecto> crear(@Valid @RequestBody ProyectoDTO dto) {
        Proyecto proyecto = proyectoService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(proyecto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> actualizar(@PathVariable Long id, @Valid @RequestBody ProyectoDTO dto) {
        return ResponseEntity.ok(proyectoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        proyectoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== TAREAS (sub-recurso) ====================

    @GetMapping("/{proyectoId}/tareas")
    public ResponseEntity<List<Tarea>> listarTareas(@PathVariable Long proyectoId) {
        return ResponseEntity.ok(tareaService.listarPorProyecto(proyectoId));
    }

    @PostMapping("/{proyectoId}/tareas")
    public ResponseEntity<Tarea> crearTarea(@PathVariable Long proyectoId, @Valid @RequestBody TareaDTO dto) {
        dto.setProyectoId(proyectoId);
        Tarea tarea = tareaService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(tarea);
    }

    @PatchMapping("/tareas/{tareaId}/estado")
    public ResponseEntity<Tarea> cambiarEstadoTarea(
            @PathVariable Long tareaId,
            @RequestParam String estado) {
        return ResponseEntity.ok(tareaService.cambiarEstado(tareaId, estado));
    }

    @DeleteMapping("/tareas/{tareaId}")
    public ResponseEntity<Void> eliminarTarea(@PathVariable Long tareaId) {
        tareaService.eliminar(tareaId);
        return ResponseEntity.noContent().build();
    }
}
