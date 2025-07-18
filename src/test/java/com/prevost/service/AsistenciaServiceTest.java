package com.prevost.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prevost.dto.AsistenciaReportesDTO;
import com.prevost.model.Asistencia;
import com.prevost.repository.AsistenciaRepository;

@ExtendWith(MockitoExtension.class)
class AsistenciaServiceTest {

    @Mock
    private AsistenciaRepository asistenciaRepository;

    @InjectMocks
    private AsistenciaService asistenciaService;

    private Asistencia buildAsistencia(Long id, String estado) {
        Asistencia a = new Asistencia();
        a.setIdasistencia(id);
        a.setFecha(Date.valueOf("2025-07-18"));
        a.setEstado(estado);
        return a;
    }

    @Test
    void listarAsistencias_returnsAll() {
        Asistencia a1 = buildAsistencia(1L, "PRESENTE");
        Asistencia a2 = buildAsistencia(2L, "AUSENTE");
        when(asistenciaRepository.findAll()).thenReturn(Arrays.asList(a1, a2));

        List<Asistencia> result = asistenciaService.listarAsistencias();

        assertEquals(2, result.size());
        assertEquals(a1, result.get(0));
        verify(asistenciaRepository).findAll();
    }

    @Test
    void buscarAsistencia_foundAndNotFound() {
        Asistencia a = buildAsistencia(3L, "PRESENTE");
        when(asistenciaRepository.findById(3L)).thenReturn(Optional.of(a));
        assertEquals(a, asistenciaService.buscarAsistencia(3L));
        verify(asistenciaRepository).findById(3L);

        when(asistenciaRepository.findById(4L)).thenReturn(Optional.empty());
        assertNull(asistenciaService.buscarAsistencia(4L));
        verify(asistenciaRepository).findById(4L);
    }

    @Test
    void guardarVariasAsistencias_savesAll() {
        Asistencia a1 = buildAsistencia(null, "PRESENTE");
        Asistencia a2 = buildAsistencia(null, "AUSENTE");
        List<Asistencia> input = Arrays.asList(a1, a2);
        List<Asistencia> saved = Arrays.asList(
                buildAsistencia(5L, "PRESENTE"),
                buildAsistencia(6L, "AUSENTE"));
        when(asistenciaRepository.saveAll(input)).thenReturn(saved);

        List<Asistencia> result = asistenciaService.guardarVariasAsistencias(input);

        assertEquals(2, result.size());
        assertEquals(5L, result.get(0).getIdasistencia());
        verify(asistenciaRepository).saveAll(input);
    }

    @Test
    void editarAsistencia_whenExists_updatesAndSaves() {
        Asistencia existing = buildAsistencia(7L, "PRESENTE");
        Asistencia update = buildAsistencia(null, "AUSENTE");
        when(asistenciaRepository.findById(7L)).thenReturn(Optional.of(existing));
        when(asistenciaRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Asistencia result = asistenciaService.editarAsistencia(7L, update);

        assertEquals(7L, result.getIdasistencia());
        assertEquals("AUSENTE", result.getEstado());
        verify(asistenciaRepository).findById(7L);
        verify(asistenciaRepository).save(existing);
    }

    @Test
    void editarAsistencia_whenNotFound_throws() {
        when(asistenciaRepository.findById(8L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> asistenciaService.editarAsistencia(8L, buildAsistencia(null, "PRESENTE")));
        assertTrue(ex.getMessage().contains("Asistencia no encontrada con ID: 8"));
        verify(asistenciaRepository).findById(8L);
        verify(asistenciaRepository, never()).save(any());
    }

    @Test
    void eliminarAsistencia_invokesDelete() {
        asistenciaService.eliminarAsistencia(9L);

        verify(asistenciaRepository).deleteById(9L);
    }

    @Test
    void editarVariasAsistencias_success() {
        Asistencia detail = buildAsistencia(10L, "PRESENTE");
        Asistencia updated = buildAsistencia(10L, "AUSENTE");
        when(asistenciaRepository.findById(10L)).thenReturn(Optional.of(detail));
        when(asistenciaRepository.save(any())).thenReturn(updated);

        List<Asistencia> result = asistenciaService.editarVariasAsistencias(
                Collections.singletonList(new Asistencia() {{ setIdasistencia(10L); setEstado("AUSENTE"); }})
        );

        assertEquals(1, result.size());
        assertEquals("AUSENTE", result.get(0).getEstado());
        verify(asistenciaRepository).findById(10L);
        verify(asistenciaRepository).save(detail);
    }

    @Test
    void editarVariasAsistencias_whenIdNull_throws() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> asistenciaService.editarVariasAsistencias(
                        Collections.singletonList(new Asistencia() {{ setIdasistencia(null); }})
                )
        );
        assertTrue(ex.getMessage().contains("El id de la asistencia no puede ser nulo"));
    }

    @Test
    void obtenerDatos_formatsResponse() {
        List<Object[]> repoData = Arrays.asList(
                new Object[]{2023, 5L},
                new Object[]{2024, 10L}
        );
        when(asistenciaRepository.contarAsistenciasporAño("PRESENTE", 2023, 2024))
                .thenReturn(repoData);

        Map<String,Object> resp = asistenciaService.obtenerDatos("PRESENTE", 2023, 2024);

        assertEquals(Arrays.asList(2023, 2024), resp.get("anios"));
        assertEquals(Arrays.asList(5L, 10L), resp.get("cantidades"));
    }

    @Test
    void obtenerPorcentajes_formatsResponse() {
        List<Object[]> repoData = Collections.singletonList(
                new Object[]{"Ausente", 123, 40.5}
        );
        LocalDate start = LocalDate.of(2025, 7, 1);
        LocalDate end = LocalDate.of(2025, 7, 31);
        when(asistenciaRepository.countAsistenciasByEstado(start, end))
                .thenReturn(repoData);

        List<Map<String,Object>> resp = asistenciaService.obtenerPorcentajes(start, end);

        assertEquals(1, resp.size());
        assertEquals("Ausente", resp.get(0).get("name"));
        assertEquals(40.5, resp.get(0).get("value"));
    }

    @Test
    void obtenerAsistenciasPorClase_returnsList() {
        Asistencia a = buildAsistencia(11L, "PRESENTE");
        when(asistenciaRepository.obtenerAsistenciasPorClase(2L))
                .thenReturn(Collections.singletonList(a));

        List<Asistencia> result = asistenciaService.obtenerAsistenciasPorClase(2L);

        assertEquals(1, result.size());
        assertEquals(a, result.get(0));
    }

    @Test
    void reportes_mapsToDto() {
        Object[] row = new Object[]{
                "42", "Juan Perez", "100", "Alumno X",
                "PRESENTE", "Clase A", "Curso B", "2025-07-18"
        };
        when(asistenciaRepository.buscarAsistenciasPorProfesor(
                "PRESENTE", 1L, "2025-07-01", "2025-07-02", 2L
        )).thenReturn(Collections.singletonList(row));

        List<AsistenciaReportesDTO> result = asistenciaService.reportes(
                "PRESENTE", 1L, "2025-07-01", "2025-07-02", 2L
        );

        assertEquals(1, result.size());
        AsistenciaReportesDTO dto = result.get(0);
        assertEquals("42", dto.getIdprofesor());
        assertEquals("Juan Perez", dto.getNombreprofesor());
        assertEquals("100", dto.getId());
        assertEquals("Alumno X", dto.getNombrecompleto());
        assertEquals("PRESENTE", dto.getEstado());
        assertEquals("Clase A", dto.getNombre());
        assertEquals("Curso B", dto.getCurso());
        assertEquals("2025-07-18", dto.getFechaclase());
    }
}
