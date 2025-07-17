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

import com.prevost.model.Curso;
import com.prevost.service.CursoService;

@RestController
@RequestMapping("/rest/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping("/listar")
    public ResponseEntity<List<Curso>> listarCursos() {
        return ResponseEntity.ok(cursoService.listarCursos());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Curso> buscarCurso(@PathVariable Long id) {
        Curso curso = cursoService.buscarCurso(id);
        return curso != null ? ResponseEntity.ok(curso) : ResponseEntity.notFound().build();
    }

    @PostMapping("/agregar")
    public ResponseEntity<Curso> agregarCurso(@RequestBody Curso curso) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.guardarCurso(curso));
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Curso> editarCurso(@PathVariable Long id, @RequestBody Curso curso) {
        try {
            return ResponseEntity.ok(cursoService.editarCurso(id, curso));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarCurso(@PathVariable Long id) {
        cursoService.eliminarCurso(id);
        return ResponseEntity.noContent().build();
    }
}