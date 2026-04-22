package Innovatech.ms_gestion_proyectos.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("GESTION")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TareaGestion extends Tarea {

    @Column(name = "tipo_reunion", length = 50)
    private String tipoReunion;

    @Column(name = "participantes")
    private Integer participantes;

    @Column(name = "requiere_aprobacion")
    private Boolean requiereAprobacion;
}
