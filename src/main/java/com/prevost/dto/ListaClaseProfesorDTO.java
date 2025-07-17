package com.prevost.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ListaClaseProfesorDTO {
    private String idClase;
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private String curso;
    private String aula;
    private String fechadeclase;

    public ListaClaseProfesorDTO() {
    }
}
