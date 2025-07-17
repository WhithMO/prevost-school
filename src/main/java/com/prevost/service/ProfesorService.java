package com.prevost.service;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prevost.dto.AlumnoClaseDTO;
import com.prevost.dto.ListaClaseProfesorDTO;
import com.prevost.model.Profesor;
import com.prevost.repository.ProfesorRepository;

@Service
public class ProfesorService {

    @Autowired
    private ProfesorRepository profesorRepository;

    public List<Profesor> listarProfesores() {
        return profesorRepository.findAll();
    }

    public Profesor buscarProfesor(Long id) {
        return profesorRepository.findById(id).orElse(null);
    }

    public Profesor guardarProfesor(Profesor profesor) {
        return profesorRepository.save(profesor);
    }

    public Profesor editarProfesor(Long id, Profesor profesorActualizado) {
        Optional
        <Profesor> profesorOptional = profesorRepository.findById(id);

        if (profesorOptional.isPresent()) {
            Profesor profesor = profesorOptional.get();

            profesor.setNombre(profesorActualizado.getNombre());
            profesor.setApellido(profesorActualizado.getApellido());
            profesor.setDni(profesorActualizado.getDni());
            profesor.setEspecialidad(profesorActualizado.getEspecialidad());
            profesor.setTelefono(profesorActualizado.getTelefono());

            return profesorRepository.save(profesor);
        } else {
            throw new RuntimeException("Profesor no encontrado con ID: " + id);
        }
    }

    public void eliminarProfesor(Long id) {
        profesorRepository.deleteById(id);
    }
    
    public List<ListaClaseProfesorDTO> obtenerClasesPorProfesor(Long idProfesor) {
        List<Object[]> resultado = profesorRepository.obtenerClasesPorProfesor(idProfesor);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return resultado.stream().map(obj -> new ListaClaseProfesorDTO(
            obj[0].toString(),
            obj[1].toString(),
            obj[2].toString(),
            obj[3].toString(),
            obj[4].toString(),
            obj[5].toString(),
            obj[6] instanceof Timestamp ?
                ((Timestamp) obj[6]).toLocalDateTime().format(formatter)
                : obj[6].toString()
        )).collect(Collectors.toList());

    }

    
    public List<AlumnoClaseDTO> obtenerAlumnosPorClase(Long idClase) {
        return profesorRepository.obtenerAlumnosPorClase(idClase);
    }
}