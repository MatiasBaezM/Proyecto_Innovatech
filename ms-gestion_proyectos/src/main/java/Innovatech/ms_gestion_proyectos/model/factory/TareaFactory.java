package Innovatech.ms_gestion_proyectos.model.factory;

import org.springframework.stereotype.Component;

@Component
public class TareaFactory {

    // Simulación del Factory Method
    public Object crearTarea(String tipo) {
        if ("DESARROLLO".equalsIgnoreCase(tipo)) {
            return new Object(); // Retorna TareaDesarrollo
        } else if ("DISENO".equalsIgnoreCase(tipo)) {
            return new Object(); // Retorna TareaDiseno
        }
        throw new IllegalArgumentException("Tipo de tarea desconocido");
    }
}
