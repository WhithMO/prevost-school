package com.prevost.controller;

import com.prevost.dto.AsistenciaReportesDTO;
import com.prevost.service.AsistenciaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AsistenciasControllerTest {

    @Mock
    private AsistenciaService asistenciaService;

    @InjectMocks
    private AsistenciasController asistenciasController;

    @Test
    void listarCantidadesPorAnio_returnsMap() {
        Map<String, Object> expected = new HashMap<>();
        expected.put("2022", 5);
        expected.put("2023", 8);
        when(asistenciaService.obtenerDatos("PRESENTE", 2022, 2023)).thenReturn(expected);

        Map<String, Object> result = asistenciasController.listarCantidadesPorAnio("PRESENTE", 2022, 2023);

        assertEquals(expected, result);
        verify(asistenciaService).obtenerDatos("PRESENTE", 2022, 2023);
    }

    @Test
    void listarPorcentajes_returnsList() {
        List<Map<String, Object>> expected = Arrays.asList(
                Map.of("curso", "Math", "porcentaje", 75.0),
                Map.of("curso", "History", "porcentaje", 60.0)
        );
        LocalDate inicio = LocalDate.of(2025, 7, 1);
        LocalDate fin = LocalDate.of(2025, 7, 31);
        when(asistenciaService.obtenerPorcentajes(inicio, fin)).thenReturn(expected);

        List<Map<String, Object>> result = asistenciasController.listarPorcentajes("2025-07-01", "2025-07-31");

        assertEquals(expected, result);
        verify(asistenciaService).obtenerPorcentajes(inicio, fin);
    }

    @Test
    void obtenerClasesPorProfesor_withResults_returnsOk() {
        AsistenciaReportesDTO dto = new AsistenciaReportesDTO(
                "1", "Profesor Uno", "10", "Alumno Uno",
                "PRESENTE", "Clase A", "Curso A", "2025-07-18"
        );
        List<AsistenciaReportesDTO> list = Collections.singletonList(dto);
        when(asistenciaService.reportes("PRESENTE", 1L, "2025-07-01", "2025-07-02", 2L))
                .thenReturn(list);

        ResponseEntity<List<AsistenciaReportesDTO>> response =
                asistenciasController.obtenerClasesPorProfesor("PRESENTE", 1L, "2025-07-01", "2025-07-02", 2L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(list, response.getBody());
        verify(asistenciaService).reportes("PRESENTE", 1L, "2025-07-01", "2025-07-02", 2L);
    }

    @Test
    void obtenerClasesPorProfesor_noResults_returnsNoContent() {
        when(asistenciaService.reportes(null, null, null, null, null))
                .thenReturn(Collections.emptyList());

        ResponseEntity<List<AsistenciaReportesDTO>> response =
                asistenciasController.obtenerClasesPorProfesor(null, null, null, null, null);

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(asistenciaService).reportes(null, null, null, null, null);
    }
}
