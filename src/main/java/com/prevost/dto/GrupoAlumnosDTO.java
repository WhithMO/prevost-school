package com.prevost.dto;

import java.util.List;

public class GrupoAlumnosDTO {
    private Long idClase;
    private List<Long> idsAlumnos;

    public Long getIdClase() {
        return idClase;
    }

    public void setIdClase(Long idClase) {
        this.idClase = idClase;
    }

    public List<Long> getIdsAlumnos() {
        return idsAlumnos;
    }

    public void setIdsAlumnos(List<Long> idsAlumnos) {
        this.idsAlumnos = idsAlumnos;
    }
}