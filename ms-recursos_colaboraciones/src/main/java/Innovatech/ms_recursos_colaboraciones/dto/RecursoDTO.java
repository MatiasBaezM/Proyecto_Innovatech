package Innovatech.ms_recursos_colaboraciones.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecursoDTO {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 150)
    private String nombre;

    @NotNull(message = "El tipo de recurso es obligatorio")
    private String tipo;

    private Boolean disponible;

    @DecimalMin(value = "0.0")
    private BigDecimal costoHora;

    @Size(max = 100)
    private String especialidad;

    @Email
    private String email;
}
