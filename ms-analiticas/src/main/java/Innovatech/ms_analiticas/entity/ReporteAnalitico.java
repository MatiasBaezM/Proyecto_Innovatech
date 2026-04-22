package Innovatech.ms_analiticas.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Documento MongoDB para reportes analíticos desnormalizados.
 */
@Document(collection = "reportes_analiticos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteAnalitico {

    @Id
    private String id;

    private String tipoReporte;  // "RESUMEN_EJECUTIVO", "RENDIMIENTO_EQUIPO", "FINANCIERO"

    private String titulo;

    private String descripcion;

    /**
     * Datos desnormalizados del reporte (estructura flexible).
     */
    private Map<String, Object> datos;

    /**
     * Lista de secciones con contenido heterogéneo.
     */
    private List<Map<String, Object>> secciones;

    private Long generadoPor;  // ID del usuario que generó el reporte

    private String periodo;

    @CreatedDate
    private LocalDateTime fechaGeneracion;
}
