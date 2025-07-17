package com.prevost.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idusuario;
    
    private String username;
    private String password;
    private Boolean activo;
    private Boolean primerIngreso;
    
    @ManyToOne
    @JoinColumn(name = "idrol")
    private Rol rol;
    
    @OneToOne
    @JoinColumn(name = "idprofesor", unique = true)
    private Profesor profesor;
}