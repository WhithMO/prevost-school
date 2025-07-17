package com.prevost.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AsistenciaDTO {

    private Integer idasistencia;
    private String nombre;
    private String apellido;
    private Date fecha;
    private String estado;
    private String nombrecurso;

    public AsistenciaDTO() {}
}
