package com.prevost.controller;

import java.util.List;
import java.util.Optional;

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

import com.prevost.dto.AulaRequestDTO;
import com.prevost.model.AlumnoAula;
import com.prevost.model.Aula;
import com.prevost.service.AulaService;

@RestController
@RequestMapping("/rest/aulas")
public class AulaController {

    @Autowired
    private AulaService aulaService;

    @GetMapping("/listar")
    public ResponseEntity<List<Aula>> listarAulas() {
        return ResponseEntity.ok(aulaService.listarAulas());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Aula> buscarAula(@PathVariable Long id) {
        Aula aula = aulaService.buscarAula(id);
        return aula != null ? ResponseEntity.ok(aula) : ResponseEntity.notFound().build();
    }

    @PostMapping("/agregar")
    public ResponseEntity<Aula> agregarAula(@RequestBody
    		Aula aula) {
        return ResponseEntity.status(HttpStatus.CREATED).body(aulaService.guardarAula(aula));
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Aula> editarAula(@PathVariable Long id, @RequestBody Aula aula) {
        try {
            return ResponseEntity.ok(aulaService.editarAula(id, aula));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/agregaraulaalumno")
    public ResponseEntity<?> crearAula(@RequestBody AulaRequestDTO aulaRequest) {
        try {
            Aula aulaGuardada = aulaService.guardarAulaConAlumnos(aulaRequest);
            return ResponseEntity.ok(aulaGuardada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ocurrió un error inesperado.");
        }
    }
    
    @PutMapping("/editaraulaalumno/{idaula}")
    public ResponseEntity<?> actualizarAula(
            @PathVariable Long idaula,
            @RequestBody AulaRequestDTO aulaRequest) {

        Optional<Aula> aulaOptional = aulaService.obtenerAulaPorId(idaula);
        if (aulaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Aula aulaActualizada = aulaService.actualizarAula(idaula, aulaRequest);
        return ResponseEntity.ok(aulaActualizada);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarAula(@PathVariable Long id) {
        aulaService.eliminarAula(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("listaralumnos/{idaula}")
    public ResponseEntity<List<AlumnoAula>> obtenerAlumnosPorAula(@PathVariable Long idaula) {
        List<AlumnoAula> alumnos = aulaService.obtenerAlumnosPorAula(idaula);

        if (alumnos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(alumnos);
    }
    
    
}