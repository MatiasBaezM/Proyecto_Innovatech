package Innovatech.ms_recursos_colaboraciones.model;

import lombok.Data;

@Data
public class Colaborador {
    private String rut;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String direccion;
    private String comuna;
    private String region;
    private String pais;


    public Colaborador() {
    }


}