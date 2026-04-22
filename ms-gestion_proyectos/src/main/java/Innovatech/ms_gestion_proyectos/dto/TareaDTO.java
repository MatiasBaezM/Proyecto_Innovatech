package Innovatech.ms_gestion_proyectos.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TareaDTO {

    private Long id;

    @NotBlank(message = "El título de la tarea es obligatorio")
    @Size(max = 200, message = "El título no puede exceder 200 caracteres")
    private String titulo;

    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    private String descripcion;

    private String estado;

    @NotNull(message = "La prioridad es obligatoria")
    private String prioridad;

    @NotNull(message = "El ID del proyecto es obligatorio")
    private Long proyectoId;

    /**
     * Tipo discriminador: "TECNICA" o "GESTION"
     * Usado por el TareaFactory para instanciar la subclase correcta.
     */
    @NotBlank(message = "El tipo de tarea es obligatorio")
    private String tipo;

    // --- Campos específicos de TareaTecnica ---
    private String lenguajeProgramacion;
    private String complejidad;
    private Boolean requiereRevisionCodigo;

    // --- Campos específicos de TareaGestion ---
    private String tipoReunion;
    private Integer participantes;
    private Boolean requiereAprobacion;
}
