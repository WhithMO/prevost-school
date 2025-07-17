package com.prevost.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "asistencia")
public class Asistencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idasistencia;
    
    @ManyToOne
    @JoinColumn(name = "idalumno")
    private Alumno alumno;
    
    @ManyToOne
    @JoinColumn(name = "id_clase")
    private Clase clase;
    
    private Date fecha;
    private String estado;
}