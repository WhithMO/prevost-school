package com.prevost.model;

import java.time.LocalDateTime;

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
@Table(name = "clase")
public class Clase {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClase;

    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private LocalDateTime fechadeclase;

    @ManyToOne
    @JoinColumn(name = "idcurso", nullable = false)
    private Curso curso;
    
    @ManyToOne
    @JoinColumn(name = "idaula", nullable = false)
    private Aula aula;

    @ManyToOne
    @JoinColumn(name = "idprofesor", nullable = false)
    private Profesor profesor;
}