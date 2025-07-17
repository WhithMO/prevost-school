package com.prevost.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.prevost.model.Clase;
//import com.prevost.model.Grupo;
import com.prevost.repository.AsistenciaRepository;
import com.prevost.repository.ClaseRepository;

@Service
public class ClaseService {

    @Autowired
    private ClaseRepository claseRepository;
    
    @Autowired
    private AsistenciaRepository asistenciaRepository;
    

    public List<Clase> listarClases() {
        return claseRepository.findAll();
    }

    public Clase buscarClase(Long id) {
        return claseRepository.findById(id).orElse(null);
    }

    public Clase guardarClase(Clase clase) {

        Clase nuevaClase = claseRepository.save(clase);

        asistenciaRepository.crearAsistenciasParaClase(nuevaClase.getIdClase());

        return nuevaClase;
    }

    public Clase editarClase(Long id, Clase claseActualizada) {
        Optional<Clase> claseOptional = claseRepository.findById(id);

        if (claseOptional.isPresent()) {
            Clase clase = claseOptional.get();

            clase.setCurso(claseActualizada.getCurso());
            clase.setAula(claseActualizada.getAula());
            clase.setProfesor(claseActualizada.getProfesor());
            clase.setHoraInicio(claseActualizada.getHoraInicio());
            clase.setHoraFin(claseActualizada.getHoraFin());
            clase.setDiaSemana(claseActualizada.getDiaSemana());
            clase.setFechadeclase(claseActualizada.getFechadeclase());

            return claseRepository.save(clase);
        } else {
            throw new RuntimeException("Clase no encontrada con ID: " + id);
        }
    }

    public void eliminarClase(Long id) {
        claseRepository.deleteById(id);
    }
    
    /*public List<Grupo> obtenerGruposPorClase(Long idClase) {
        return alumnoClaseRepository.findByClase_IdClase(idClase)
            .stream()
            .map(AlumnoClase::getGrupo)
            .distinct()
            .collect(Collectors.toList());
    }*/
}