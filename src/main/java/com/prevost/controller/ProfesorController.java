package com.prevost.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prevost.dto.AlumnoClaseDTO;
import com.prevost.dto.ListaClaseProfesorDTO;
import com.prevost.model.Asistencia;
//import com.prevost.model.Grupo;
import com.prevost.model.Profesor;
import com.prevost.service.AsistenciaService;
import com.prevost.service.ClaseService;
import com.prevost.service.ProfesorService;

@RestController
@RequestMapping("/rest/profesores")
public class ProfesorController {

    @Autowired
    private ProfesorService profesorService;
    
    @Autowired
    private AsistenciaService asistenciaService;
    
    @Autowired
    private ClaseService claseService;

    @GetMapping("/listar")
    public ResponseEntity<List<Profesor>> listarProfesores() {
        return ResponseEntity.ok(profesorService.listarProfesores());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Profesor> buscarProfesor(@PathVariable Long id) {
        Profesor profesor = profesorService.buscarProfesor(id);
        return profesor != null ? ResponseEntity.ok(profesor) : ResponseEntity.notFound().build();
    }

    @PostMapping("/agregar")
    public ResponseEntity<Profesor> agregarProfesor(@RequestBody Profesor profesor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(profesorService.guardarProfesor(profesor));
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Profesor> editarProfesor(@PathVariable Long id, @RequestBody Profesor profesor) {
        try {
            return ResponseEntity.ok(profesorService.editarProfesor(id, profesor));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarProfesor(@PathVariable Long id) {
        profesorService.eliminarProfesor(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/clases/{idProfesor}")
    public ResponseEntity<List<ListaClaseProfesorDTO>> obtenerClasesPorProfesor(@PathVariable Long idProfesor) {
        List<ListaClaseProfesorDTO> clases = profesorService.obtenerClasesPorProfesor(idProfesor);
        return clases.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(clases);
    }

    
    @GetMapping("/{idClase}/alumnos")
    public ResponseEntity<List<AlumnoClaseDTO>> obtenerAlumnosPorClase(@PathVariable Long idClase) {
        return ResponseEntity.ok(profesorService.obtenerAlumnosPorClase(idClase));
    }
    
    @PostMapping("/asistencias")
    public ResponseEntity<List<Asistencia>> agregarVariasAsistencias(@RequestBody List<Asistencia> asistencias) {
        List<Asistencia> asistenciasGuardadas = asistenciaService.guardarVariasAsistencias(asistencias);
        return ResponseEntity.status(HttpStatus.CREATED).body(asistenciasGuardadas);
    }
    
    @PutMapping("/asistenciaseditar")
    public List<Asistencia> editarVariasAsistencias(@RequestBody List<Asistencia> asistenciasDetalles) {
        return asistenciaService.editarVariasAsistencias(asistenciasDetalles);
    }
    
    @GetMapping("/listarAsistencia")
    public ResponseEntity<List<Asistencia>> listarAsistencia() {
        return ResponseEntity.ok(asistenciaService.listarAsistencias());
    }
    
    @GetMapping("/asistencias/{idClase}")
    public ResponseEntity<List<Asistencia>> getAsistenciasPorClase(@PathVariable Long idClase) {
        List<Asistencia> asistencias = asistenciaService.obtenerAsistenciasPorClase(idClase);
        return ResponseEntity.ok(asistencias);
    }
    
}