package com.prevost.dto;

import java.util.Date;

public class AsistenciaListaDTO {
    private Integer idasistencia;
    private String nombre;
    private String apellido;
    private Date fecha;
    private String estado;
    private String nombrecurso;
    private Integer idjustificacion; // Puede ser null

    // 📌 Constructor con los mismos parámetros que la consulta
    public AsistenciaListaDTO(Integer idasistencia, String nombre, String apellido, Date fecha, String estado, String nombrecurso, Integer idjustificacion) {
        this.idasistencia = idasistencia;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha = fecha;
        this.estado = estado;
        this.nombrecurso = nombrecurso;
        this.idjustificacion = idjustificacion;
    }

    // 📌 Getters y setters
    public Integer getIdasistencia() { return idasistencia; }
    public void setIdasistencia(Integer idasistencia) { this.idasistencia = idasistencia; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public String getNombrecurso() { return nombrecurso; }
    public void setNombrecurso(String nombrecurso) { this.nombrecurso = nombrecurso; }
    
    public Integer getIdjustificacion() { return idjustificacion; }
    public void setIdjustificacion(Integer idjustificacion) { this.idjustificacion = idjustificacion; }
}
