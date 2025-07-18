package com.prevost.dto;

import java.sql.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AlumnoReporteDTO {

    private Long idAlumno;
    private String nombreAlumno;
    private String apellidoAlumno;
    private Date fechaAsistencia;
    private String estadoAsistencia;
    private String curso;
    private String aula;

    // ✅ Este constructor es usado por el SELECT NEW en JPQL
    public AlumnoReporteDTO(Long idAlumno, String nombreAlumno, String apellidoAlumno,
                            Date fechaAsistencia, String estadoAsistencia,
                            String curso, String aula) {
        this.idAlumno = idAlumno;
        this.nombreAlumno = nombreAlumno;
        this.apellidoAlumno = apellidoAlumno;
        this.fechaAsistencia = fechaAsistencia;
        this.estadoAsistencia = estadoAsistencia;
        this.curso = curso;
        this.aula = aula;
    }
}