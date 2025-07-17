package com.prevost.dto;

import java.util.List;

import lombok.Data;

@Data
public class AulaRequestDTO {

    private String nombre;
    private Integer capacidad;
    private List<Long> alumnosIds;
    
}
