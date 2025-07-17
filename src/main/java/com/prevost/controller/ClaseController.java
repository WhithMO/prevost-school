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

import com.prevost.model.Clase;
//import com.prevost.model.Grupo;
import com.prevost.service.ClaseService;

@RestController
@RequestMapping("/rest/clases")
public class ClaseController {

    @Autowired
    private ClaseService claseService;

    @GetMapping("/listar")
    public ResponseEntity<List<Clase>> listarClases() {
        return ResponseEntity.ok(claseService.listarClases());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Clase> buscarClase(@PathVariable Long id) {
        Clase clase = claseService.buscarClase(id);
        return clase != null ? ResponseEntity.ok(clase) : ResponseEntity.notFound().build();
    }

    @PostMapping("/agregar")
    public ResponseEntity<Clase> agregarClase(@RequestBody Clase clase) {
        return ResponseEntity.status(HttpStatus.CREATED).body(claseService.guardarClase(clase));
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Clase> editarClase(@PathVariable Long id, @RequestBody Clase clase) {
        try {
            return ResponseEntity.ok(claseService.editarClase(id, clase));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarClase(@PathVariable Long id) {
        claseService.eliminarClase(id);
        return ResponseEntity.noContent().build();
    }
   
}