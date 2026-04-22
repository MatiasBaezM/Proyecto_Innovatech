package Innovatech.ms_recursos_colaboraciones.service;

import Innovatech.ms_recursos_colaboraciones.dto.RecursoDTO;
import Innovatech.ms_recursos_colaboraciones.entity.Recurso;
import Innovatech.ms_recursos_colaboraciones.entity.Recurso.TipoRecurso;
import Innovatech.ms_recursos_colaboraciones.repository.RecursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RecursoService {

    private final RecursoRepository recursoRepository;

    public List<Recurso> listarTodos() {
        return recursoRepository.findAll();
    }

    public Recurso obtenerPorId(Long id) {
        return recursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado con ID: " + id));
    }

    public List<Recurso> listarDisponibles() {
        return recursoRepository.findByDisponibleTrue();
    }

    public List<Recurso> listarPorTipo(String tipo) {
        TipoRecurso tipoEnum = TipoRecurso.valueOf(tipo.toUpperCase());
        return recursoRepository.findByTipo(tipoEnum);
    }

    public Recurso crear(RecursoDTO dto) {
        Recurso recurso = Recurso.builder()
                .nombre(dto.getNombre())
                .tipo(TipoRecurso.valueOf(dto.getTipo().toUpperCase()))
                .disponible(dto.getDisponible() != null ? dto.getDisponible() : true)
                .costoHora(dto.getCostoHora())
                .especialidad(dto.getEspecialidad())
                .email(dto.getEmail())
                .build();
        return recursoRepository.save(recurso);
    }

    public Recurso actualizar(Long id, RecursoDTO dto) {
        Recurso recurso = obtenerPorId(id);
        recurso.setNombre(dto.getNombre());
        recurso.setTipo(TipoRecurso.valueOf(dto.getTipo().toUpperCase()));
        recurso.setDisponible(dto.getDisponible());
        recurso.setCostoHora(dto.getCostoHora());
        recurso.setEspecialidad(dto.getEspecialidad());
        recurso.setEmail(dto.getEmail());
        return recursoRepository.save(recurso);
    }

    public void eliminar(Long id) {
        Recurso recurso = obtenerPorId(id);
        recursoRepository.delete(recurso);
    }
}
