package com.prevost.model;

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
@Table(name = "alumno_aula")
public class AlumnoAula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idalumnoaula;

    @ManyToOne
    @JoinColumn(name = "id_alumno", nullable = false)
    private Alumno alumno;
    
    @ManyToOne
    @JoinColumn(name = "idaula", nullable = false)
    private Aula aula;
}
