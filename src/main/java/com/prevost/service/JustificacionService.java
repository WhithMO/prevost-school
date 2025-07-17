package com.prevost.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prevost.dto.AsistenciaListaDTO;
import com.prevost.model.Justificacion;
import com.prevost.repository.JustificacionRepository;
import jakarta.transaction.Transactional;

@Service
public class JustificacionService {

    @Autowired
    private JustificacionRepository justificacionRepository;

    public List<Justificacion> listarJustificaciones() {
        return justificacionRepository.findAll();
    }

    public Justificacion buscarJustificacion(Long id) {
        return justificacionRepository.findById(id).orElse(null);
    }

    public Justificacion guardarJustificacion(Justificacion justificacion) {
        return justificacionRepository.save(justificacion);
    }

    public Justificacion editarJustificacion(Long id, Justificacion justificacionActualizada) {
        Optional<Justificacion> justificacionOptional = justificacionRepository.findById(id);

        if (justificacionOptional.isPresent()) {
            Justificacion justificacion = justificacionOptional.get();

            justificacion.setAsistencia(justificacionActualizada.getAsistencia());
            justificacion.setProfesor(justificacionActualizada.getProfesor());
            justificacion.setMotivo(justificacionActualizada.getMotivo());
            justificacion.setFechaPresentacion(justificacionActualizada.getFechaPresentacion());

            return justificacionRepository.save(justificacion);
        } else {
            throw new RuntimeException("Justificación no encontrada con ID: " + id);
        }
    }

    public void eliminarJustificacion(Long id) {
        justificacionRepository.deleteById(id);
    }
    
    @Transactional
    public void actualizarEstado(Long idjustificacion, String nuevoEstado) {
        justificacionRepository.actualizarEstadoJustificacion(idjustificacion, nuevoEstado);
    }
    
    public List<Justificacion> obtenerJustificacionesPorProfesor(Long idprofesor) {
        return justificacionRepository.encontrarPorProfesor(idprofesor);
    }
    
    public List<AsistenciaListaDTO> obtenerAsistenciasPorAlumnoYProfesor(Integer idProfesor) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -5);
        Date fechaLimite = calendar.getTime();

        List<Object[]> resultados = justificacionRepository.findAsistenciasByAlumnoAndProfesor(idProfesor, fechaLimite);

        List<AsistenciaListaDTO> asistencias = resultados.stream().map(obj -> 
            new AsistenciaListaDTO(
                ((Number) obj[0]).intValue(),  // idasistencia
                (String) obj[1],              // nombre
                (String) obj[2],              // apellido
                (Date) obj[3],                // fecha
                (String) obj[4],              // estado
                (String) obj[5],              // nombrecurso
                obj[6] != null ? ((Number) obj[6]).intValue() : null // idjustificacion (puede ser null)
            )
        ).collect(Collectors.toList());

        return asistencias; // 🚀 Retorna la lista de AsistenciaListaDTO correctamente

    }


    
}