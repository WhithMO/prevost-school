package com.prevost.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prevost.dto.AlumnoReporteDTO;
import com.prevost.model.Alumno;
import com.prevost.repository.AlumnoRepository;

@Service
public class AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    public List<Alumno> listarAlumnos() {
        return alumnoRepository.findAll();
    }
    
    public List<Alumno> listaralumnosinaula() {
        return alumnoRepository.findAlumnosSinAula();
    }

    public Alumno buscarAlumno(Long id) {
        return alumnoRepository.findById(id).orElse(null);
    }

    public Alumno guardarAlumno(Alumno alumno) {
        return alumnoRepository.save(alumno);
    }

    public Alumno editarAlumno(Long id, Alumno alumnoActualizado) {
        Optional<Alumno> alumnoOptional = alumnoRepository.findById(id);

        if (alumnoOptional.isPresent()) {
            Alumno alumno = alumnoOptional.get();
            
            alumno.setNombre(alumnoActualizado.getNombre());
            alumno.setApellido(alumnoActualizado.getApellido());
            alumno.setDni(alumnoActualizado.getDni());
            alumno.setFechaNacimiento(alumnoActualizado.getFechaNacimiento());

            return alumnoRepository.save(alumno);
        } else {
            throw new RuntimeException("Alumno no encontrado con ID: " + id);
        }
    }

    public void eliminarAlumno(Long id) {
        alumnoRepository.deleteById(id);
    }
    
    public List<AlumnoReporteDTO> obtenerAsistenciasPorAlumno(Long idAlumno) {
        return alumnoRepository.obtenerAsistenciasPorAlumno(idAlumno);
    }
}