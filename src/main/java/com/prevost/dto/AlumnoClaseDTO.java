package com.prevost.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlumnoClaseDTO {

    private Long id;
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private String nombreAlumno;
    private String apellidoAlumno;
    private String estado;
    private Long idasistencia;
    private Long idaula;
    private String nombreAula;
    private Date fechaAsistencia;
    
	public AlumnoClaseDTO() {
	}
    
}
