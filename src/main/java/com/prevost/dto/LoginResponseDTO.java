package com.prevost.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {

    private String token;
    private String rol;
    private String usuario;
    private String  idProfesor;
    private Boolean primerIngreso;
    
    public LoginResponseDTO(String token, String rol, String usuario, String  idProfesor, Boolean primerIngreso) {
        this.token = token;
        this.rol = rol;
        this.usuario = usuario;
        this.idProfesor = idProfesor;
        this.primerIngreso = primerIngreso;
        
    }
}
