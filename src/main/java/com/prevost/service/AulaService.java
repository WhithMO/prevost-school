package com.prevost.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prevost.dto.AulaRequestDTO;
import com.prevost.model.Alumno;
import com.prevost.model.AlumnoAula;
import com.prevost.model.Aula;
import com.prevost.repository.AlumnoAulaRepository;
import com.prevost.repository.AlumnoRepository;
import com.prevost.repository.AulaRepository;
import jakarta.transaction.Transactional;

@Service
public class AulaService {

    @Autowired
    private AulaRepository aulaRepository;
    
    @Autowired
    private AlumnoRepository alumnoRepository;
    
    @Autowired
    private AlumnoAulaRepository alumnoAulaRepository;

    public List<Aula> listarAulas() {
        return aulaRepository.findAll();
    }

    public Aula buscarAula(Long id) {
        return aulaRepository.findById(id).orElse(null);
    }

    public Aula guardarAula(Aula aula) {
        return aulaRepository.save(aula);
    }

    public Aula editarAula(Long id, Aula aulaActualizada) {
        Optional<Aula> aulaOptional = aulaRepository.findById(id);

        if (aulaOptional.isPresent()) {
            Aula aula = aulaOptional.get();

            aula.setNombre(aulaActualizada.getNombre());
            aula.setCapacidad(aulaActualizada.getCapacidad());

            return aulaRepository.save(aula);
        } else {
            throw new RuntimeException("Aula no encontrada con ID: " + id);
        }
    }
    
    public List<AlumnoAula> obtenerAlumnosPorAula(Long idaula) {
        return alumnoAulaRepository.findByAula_Idaula(idaula);
    }
    
    @Transactional
    public Aula guardarAulaConAlumnos(AulaRequestDTO aulaRequest) {
        Aula nuevaAula = new Aula();
        nuevaAula.setNombre(aulaRequest.getNombre());
        nuevaAula.setCapacidad(aulaRequest.getCapacidad());
        nuevaAula = aulaRepository.save(nuevaAula);

        if (aulaRequest.getAlumnosIds().size() > nuevaAula.getCapacidad()) {
            throw new IllegalArgumentException("La cantidad de alumnos excede la capacidad del aula.");
        }

        List<Long> alumnosExistentes = alumnoAulaRepository.findByAula_Idaula(nuevaAula.getIdaula())
                .stream().map(a -> a.getAlumno().getIdalumno()).collect(Collectors.toList());

        List<Alumno> alumnos = alumnoRepository.findAllById(aulaRequest.getAlumnosIds());

        for (Alumno alumno : alumnos) {
            if (alumnosExistentes.contains(alumno.getIdalumno())) {
                throw new IllegalArgumentException("El alumno con ID " + alumno.getIdalumno() + " ya está registrado en el aula.");
            }

            AlumnoAula aulaAlumno = new AlumnoAula();
            aulaAlumno.setAula(nuevaAula);
            aulaAlumno.setAlumno(alumno);
            alumnoAulaRepository.save(aulaAlumno);
        }

        return nuevaAula;
    }
    
    public Optional<Aula> obtenerAulaPorId(Long idaula) {
        return aulaRepository.findById(idaula);
    }
    
    @Transactional
    public Aula actualizarAula(Long idaula, AulaRequestDTO aulaRequest) {
        Aula aula = aulaRepository.findById(idaula)
                .orElseThrow(() -> new RuntimeException("Aula no encontrada"));

        aula.setNombre(aulaRequest.getNombre());
        aula.setCapacidad(aulaRequest.getCapacidad());

        alumnoAulaRepository.deleteByAula_Idaula(idaula);

        List<Alumno> alumnos = alumnoRepository.findAllById(aulaRequest.getAlumnosIds());
        for (Alumno alumno : alumnos) {
            AlumnoAula aulaAlumno = new AlumnoAula();
            aulaAlumno.setAula(aula);
            aulaAlumno.setAlumno(alumno);
            alumnoAulaRepository.save(aulaAlumno);
        }

        return aulaRepository.save(aula);
    }

    public void eliminarAula(Long id) {
        aulaRepository.deleteById(id);
    }
}