package com.prevost.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prevost.dto.AsistenciaReportesDTO;
import com.prevost.service.AsistenciaService;

@RestController
@RequestMapping("/rest/asistencia")
public class AsistenciasController {

	@Autowired
	private AsistenciaService asistenciaService;
	
	@GetMapping("/listaranioscantidades/{estado}/{inicio}/{fin}")
	 Map<String, Object> listarCantidadesPorAnio(@PathVariable String estado , @PathVariable int inicio, @PathVariable int fin) {
		 return asistenciaService.obtenerDatos(estado, inicio, fin);
	 }
	
	@GetMapping("/listarporcentajes/{inicio}/{fin}") 
	public List<Map<String, Object>> listarPorcentajes(@PathVariable String inicio, @PathVariable String fin) {
	    LocalDate fechaInicio = LocalDate.parse(inicio);
	    LocalDate fechaFin = LocalDate.parse(fin);
	    return asistenciaService.obtenerPorcentajes(fechaInicio, fechaFin);
	}
	
	@GetMapping("/buscarportes")
	public ResponseEntity<List<AsistenciaReportesDTO>> obtenerClasesPorProfesor(
	    @RequestParam(required = false) String estado, 
	    @RequestParam(required = false) Long idaula, 
	    @RequestParam(required = false) String fechaInicio, 
	    @RequestParam(required = false) String fechaFin,
	    @RequestParam(required = false) Long idprofesor) {
	    
	    List<AsistenciaReportesDTO> reportes = asistenciaService.reportes(estado, idaula, fechaInicio, fechaFin, idprofesor);
	    return reportes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(reportes);
	}
}
