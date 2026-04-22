package Innovatech.ms_gestion_proyectos.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("TECNICA")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TareaTecnica extends Tarea {

    @Column(name = "lenguaje_programacion", length = 50)
    private String lenguajeProgramacion;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Complejidad complejidad;

    @Column(name = "requiere_revision_codigo")
    private Boolean requiereRevisionCodigo;

    public enum Complejidad {
        BAJA,
        MEDIA,
        ALTA
    }
}
