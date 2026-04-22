package Innovatech.ms_recursos_colaboraciones.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsignacionDTO {

    private Long id;

    @NotNull(message = "El ID del recurso es obligatorio")
    private Long recursoId;

    @NotNull(message = "El ID del proyecto es obligatorio")
    private Long proyectoId;

    @NotNull(message = "Las horas asignadas son obligatorias")
    @Min(value = 1, message = "Debe asignar al menos 1 hora")
    private Integer horasAsignadas;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private String rol;
}
