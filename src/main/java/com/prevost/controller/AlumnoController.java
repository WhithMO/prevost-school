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

import com.prevost.dto.AlumnoReporteDTO;
import com.prevost.model.Alumno;
import com.prevost.service.AlumnoService;

@RestController
@RequestMapping("/rest/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @GetMapping("/listar")
    public ResponseEntity<List<Alumno>> listarAlumnos() {
        return ResponseEntity.ok(alumnoService.listarAlumnos());
    }
    
    @GetMapping("/listarsinaula")
    public ResponseEntity<List<Alumno>> listaralumnosinaula() {
        return ResponseEntity.ok(alumnoService.listaralumnosinaula());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Alumno> buscarAlumno(@PathVariable Long id) {
        Alumno alumno = alumnoService.buscarAlumno(id);
        return alumno != null ? ResponseEntity.ok(alumno) : ResponseEntity.notFound().build();
    }

    @PostMapping("/agregar")
    public ResponseEntity<Alumno> agregarAlumno(@RequestBody Alumno alumno) {
        return ResponseEntity.status(HttpStatus.CREATED).body(alumnoService.guardarAlumno(alumno));
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Alumno> editarAlumno(@PathVariable Long id, @RequestBody Alumno alumno) {
        try {
            return ResponseEntity.ok(alumnoService.editarAlumno(id, alumno));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarAlumno(@PathVariable Long id) {
        alumnoService.eliminarAlumno(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}/reporteasistencias")
    public List<AlumnoReporteDTO> obtenerReporteAsistencias(@PathVariable("id") Long idAlumno) {
        return alumnoService.obtenerAsistenciasPorAlumno(idAlumno);
    }
}