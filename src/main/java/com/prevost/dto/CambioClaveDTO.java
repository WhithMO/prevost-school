package com.prevost.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CambioClaveDTO {

    private String usuario;
    private String claveActual;
    private String nuevaClave;
    
}
