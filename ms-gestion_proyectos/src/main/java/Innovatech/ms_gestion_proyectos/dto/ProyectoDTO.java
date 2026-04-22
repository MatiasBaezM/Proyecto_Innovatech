package Innovatech.ms_gestion_proyectos.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProyectoDTO {

    private Long id;

    @NotBlank(message = "El nombre del proyecto es obligatorio")
    @Size(max = 150, message = "El nombre no puede exceder 150 caracteres")
    private String nombre;

    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    @DecimalMin(value = "0.0", message = "El presupuesto debe ser positivo")
    private BigDecimal presupuesto;

    private String estado;
}
