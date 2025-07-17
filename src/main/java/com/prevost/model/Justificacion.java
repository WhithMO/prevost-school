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
@Table(name = "justificacion")
public class Justificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idjustificacion;
    
    @ManyToOne
    @JoinColumn(name = "idasistencia")
    private Asistencia asistencia;
    
    @ManyToOne
    @JoinColumn(name = "idprofesor")
    private Profesor profesor;
    
    private String motivo;
    
    private Date fechaPresentacion;
    
    private String archivo;
}
