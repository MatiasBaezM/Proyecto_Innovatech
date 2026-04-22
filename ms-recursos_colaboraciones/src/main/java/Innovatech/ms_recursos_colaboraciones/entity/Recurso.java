package Innovatech.ms_recursos_colaboraciones.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "recursos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TipoRecurso tipo;

    @Column(nullable = false)
    private Boolean disponible;

    @Column(name = "costo_hora", precision = 10, scale = 2)
    private BigDecimal costoHora;

    @Column(length = 100)
    private String especialidad;

    @Column(length = 200)
    private String email;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.disponible == null) {
            this.disponible = true;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public enum TipoRecurso {
        DESARROLLADOR,
        DISENADOR,
        PROJECT_MANAGER,
        QA_TESTER,
        DEVOPS,
        ANALISTA
    }
}
