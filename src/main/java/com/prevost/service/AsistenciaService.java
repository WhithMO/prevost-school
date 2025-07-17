package com.prevost.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prevost.dto.AsistenciaReportesDTO;
import com.prevost.model.Asistencia;
import com.prevost.repository.AsistenciaRepository;
import jakarta.transaction.Transactional;

@Service
public class AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    public List<Asistencia> listarAsistencias() {
        return asistenciaRepository.findAll();
    }

    public Asistencia buscarAsistencia(Long id) {
        return asistenciaRepository.findById(id).orElse(null);
    }

    @Transactional
    public List<Asistencia> guardarVariasAsistencias(List<Asistencia> asistencias) {
        return asistenciaRepository.saveAll(asistencias);
    }

    public Asistencia editarAsistencia(Long id, Asistencia asistenciaActualizada) {
        Optional<Asistencia> asistenciaOptional = asistenciaRepository.findById(id);

        if (asistenciaOptional.isPresent()) {
            Asistencia asistencia = asistenciaOptional.get();

            asistencia.setAlumno(asistenciaActualizada.getAlumno());
            asistencia.setClase(asistenciaActualizada.getClase());
            asistencia.setFecha(asistenciaActualizada.getFecha());
            asistencia.setEstado(asistenciaActualizada.getEstado());

            return asistenciaRepository.save(asistencia);
        } else {
            throw new RuntimeException("Asistencia no encontrada con ID: " + id);
        }
    }

    public void eliminarAsistencia(Long id) {
        asistenciaRepository.deleteById(id);
    }
    
    public List<Asistencia> editarVariasAsistencias(List<Asistencia> asistenciasDetalles) {
        return asistenciasDetalles.stream().map(asistenciaDetalles -> {
            if (asistenciaDetalles.getIdasistencia() == null) {
                throw new IllegalArgumentException("❌ ERROR: El id de la asistencia no puede ser nulo");
            }

            return asistenciaRepository.findById(asistenciaDetalles.getIdasistencia()).map(asistencia -> {
                asistencia.setEstado(asistenciaDetalles.getEstado()); // 🔹 Solo actualiza el estado
                return asistenciaRepository.save(asistencia);
            }).orElseThrow(() -> new RuntimeException("❌ ERROR: Asistencia no encontrada con id: " + asistenciaDetalles.getIdasistencia()));
        }).collect(Collectors.toList());
    }

    
    public Map<String, Object> obtenerDatos(String estado, int inicio, int fin) {
    	List<Object[]> resultado = asistenciaRepository.contarAsistenciasporAño(estado, inicio, fin);
    	
    	//Estraer años y la cantidad de asistencias
    	List<Integer> anio = resultado.stream()
    			.map(obj -> (Integer) obj[0])
    			.collect(Collectors.toList());
    	
    	List<Long> cantidades = resultado.stream()
    			.map(obj -> (Long) obj[1])
    			.collect(Collectors.toList());
    	
    	//Formato que se adapta a Echarts
    	Map<String, Object> response = new HashMap<>();
    		response.put("anios", anio);
    		response.put("cantidades", cantidades);
    		return response;
    	
    }
    
    public List<Map<String, Object>> obtenerPorcentajes(LocalDate inicio, LocalDate fin) {
        List<Object[]> resultado = asistenciaRepository.countAsistenciasByEstado(inicio, fin);

        // Formatear la respuesta en el formato de ECharts
        List<Map<String, Object>> response = resultado.stream()
                .map(obj -> {
                    Map<String, Object> dataPoint = new HashMap<>();
                    dataPoint.put("name", obj[0].toString()); // Estado (Ej: "Ausente", "Presente")
                    dataPoint.put("value", Double.parseDouble(obj[2].toString())); // Porcentaje
                    return dataPoint;
                })
                .collect(Collectors.toList());

        return response;
    }
    
    public List<Asistencia> obtenerAsistenciasPorClase(Long idClase) {
        return asistenciaRepository.obtenerAsistenciasPorClase(idClase);
    }

	public List<AsistenciaReportesDTO> reportes(String estado, Long idaula, String fechaInicio, String fechaFin, Long idprofesor) {
    	List<Object[]> resultado = asistenciaRepository.buscarAsistenciasPorProfesor(estado, idaula, fechaInicio, fechaFin, idprofesor);
    	
    	return resultado.stream().map(obj -> new AsistenciaReportesDTO(
    			obj[0].toString(),
    			obj[1].toString(),
    			obj[2].toString(),
    			obj[3].toString(),
    			obj[4].toString(),
    			obj[5].toString(),
    			obj[6].toString(),
    			obj[7].toString()
    			)).collect(Collectors.toList());
    }

}