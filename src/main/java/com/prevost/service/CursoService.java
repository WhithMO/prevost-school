package com.prevost.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prevost.model.Curso;
import com.prevost.repository.CursoRepository;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    public List<Curso> listarCursos() {
        return cursoRepository.findAll();
    }

    public Curso buscarCurso(Long id) {
        return cursoRepository.findById(id).orElse(null);
    }

    public Curso guardarCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    public Curso editarCurso(Long id, Curso cursoActualizado) {
        Optional<Curso> cursoOptional = cursoRepository.findById(id);

        if (cursoOptional.isPresent()) {
            Curso curso = cursoOptional.get();

            curso.setNombre(cursoActualizado.getNombre());
            curso.setDescripcion(cursoActualizado.getDescripcion());

            return cursoRepository.save(curso);
        } else {
            throw new RuntimeException("Curso no encontrado con ID: " + id);
        }
    }

    public void eliminarCurso(Long id) {
        cursoRepository.deleteById(id);
    }
}