package com.prevost.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AsistenciaReportesDTO {

	private String idprofesor;
	private String nombreprofesor;
	private String id;
	private String nombrecompleto;
	private String estado;
	private String nombre;
	private String curso;
	private String fechaclase;
	
	public AsistenciaReportesDTO() {
		
	}

}
