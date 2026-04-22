package Innovatech.ms_analiticas.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Documento MongoDB para almacenar KPIs de un proyecto.
 * Utiliza estructura desnormalizada para alta velocidad de lectura.
 */
@Document(collection = "kpis_proyecto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KpiProyecto {

    @Id
    private String id;

    private Long proyectoId;

    private String nombreProyecto;

    /**
     * KPIs almacenados como mapa flexible (clave-valor).
     * Ejemplo: {"avance_porcentual": 75.5, "tareas_completadas": 12, "presupuesto_consumido": 45000}
     */
    private Map<String, Object> kpis;

    /**
     * Métricas de rendimiento del equipo.
     */
    private Map<String, Object> metricasEquipo;

    /**
     * Datos de progreso temporal (para gráficos).
     */
    private Map<String, Object> progreso;

    private String periodo;  // "2026-Q1", "2026-04", etc.

    @CreatedDate
    private LocalDateTime timestamp;
}
