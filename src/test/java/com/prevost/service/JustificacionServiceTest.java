package com.prevost.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prevost.dto.AsistenciaListaDTO;
import com.prevost.model.Justificacion;
import com.prevost.repository.JustificacionRepository;

@ExtendWith(MockitoExtension.class)
class JustificacionServiceTest {

    @Mock
    private JustificacionRepository justificacionRepository;

    @InjectMocks
    private JustificacionService justificacionService;

    private Justificacion buildJustificacion(Long id) {
        Justificacion j = new Justificacion();
        j.setIdjustificacion(id);
        return j;
    }

    @Test
    void listarJustificaciones_returnsAll() {
        Justificacion j1 = buildJustificacion(1L);
        Justificacion j2 = buildJustificacion(2L);
        when(justificacionRepository.findAll()).thenReturn(Arrays.asList(j1, j2));

        List<Justificacion> result = justificacionService.listarJustificaciones();

        assertEquals(2, result.size());
        assertEquals(j1, result.get(0));
        verify(justificacionRepository).findAll();
    }

    @Test
    void buscarJustificacion_foundAndNotFound() {
        Justificacion j = buildJustificacion(3L);
        when(justificacionRepository.findById(3L)).thenReturn(Optional.of(j));
        assertEquals(j, justificacionService.buscarJustificacion(3L));
        verify(justificacionRepository).findById(3L);

        when(justificacionRepository.findById(4L)).thenReturn(Optional.empty());
        assertNull(justificacionService.buscarJustificacion(4L));
        verify(justificacionRepository).findById(4L);
    }

    @Test
    void guardarJustificacion_savesAndReturns() {
        Justificacion input = buildJustificacion(null);
        Justificacion saved = buildJustificacion(5L);
        when(justificacionRepository.save(input)).thenReturn(saved);

        Justificacion result = justificacionService.guardarJustificacion(input);

        assertEquals(saved, result);
        verify(justificacionRepository).save(input);
    }

    @Test
    void editarJustificacion_whenExists_updatesAndSaves() {
        Justificacion existing = buildJustificacion(6L);
        existing.setMotivo("Old");
        Justificacion update = buildJustificacion(null);
        update.setMotivo("New");

        when(justificacionRepository.findById(6L)).thenReturn(Optional.of(existing));
        when(justificacionRepository.save(existing)).thenReturn(existing);

        Justificacion result = justificacionService.editarJustificacion(6L, update);

        assertEquals(6L, result.getIdjustificacion());
        assertEquals("New", result.getMotivo());
        verify(justificacionRepository).findById(6L);
        verify(justificacionRepository).save(existing);
    }

    @Test
    void editarJustificacion_whenNotExists_throws() {
        when(justificacionRepository.findById(7L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                justificacionService.editarJustificacion(7L, buildJustificacion(null))
        );
        assertTrue(ex.getMessage().contains("Justificación no encontrada con ID: 7"));
        verify(justificacionRepository).findById(7L);
        verify(justificacionRepository, never()).save(any());
    }

    @Test
    void eliminarJustificacion_invokesDelete() {
        justificacionService.eliminarJustificacion(8L);

        verify(justificacionRepository).deleteById(8L);
    }

    @Test
    void actualizarEstado_delegatesToRepository() {
        justificacionService.actualizarEstado(9L, "APROBADO");

        verify(justificacionRepository).actualizarEstadoJustificacion(9L, "APROBADO");
    }

    @Test
    void obtenerJustificacionesPorProfesor_returnsList() {
        Justificacion j = buildJustificacion(10L);
        when(justificacionRepository.encontrarPorProfesor(4L)).thenReturn(Collections.singletonList(j));

        List<Justificacion> result = justificacionService.obtenerJustificacionesPorProfesor(4L);

        assertEquals(1, result.size());
        assertEquals(j, result.get(0));
        verify(justificacionRepository).encontrarPorProfesor(4L);
    }

    @Test
    void obtenerAsistenciasPorAlumnoYProfesor_mapsCorrectly() {
        Integer idAsist = 11;
        String nombre = "Juan";
        String apellido = "Perez";
        Date fecha = new Date();
        String estado = "AUSENTE";
        String curso = "Matemáticas";
        Integer idJust = 20;
        Object[] row1 = new Object[]{ idAsist, nombre, apellido, fecha, estado, curso, idJust };
        Object[] row2 = new Object[]{ 12, "Ana", "Gomez", fecha, "PRESENTE", "Historia", null };
        when(justificacionRepository.findAsistenciasByAlumnoAndProfesor(eq(5), any(Date.class)))
                .thenReturn(Arrays.asList(row1, row2));

        List<AsistenciaListaDTO> dtos = justificacionService.obtenerAsistenciasPorAlumnoYProfesor(5);

        assertEquals(2, dtos.size());

        AsistenciaListaDTO d1 = dtos.get(0);
        assertEquals(idAsist, d1.getIdasistencia());
        assertEquals(nombre, d1.getNombre());
        assertEquals(apellido, d1.getApellido());
        assertEquals(fecha, d1.getFecha());
        assertEquals(estado, d1.getEstado());
        assertEquals(curso, d1.getNombrecurso());
        assertEquals(idJust, d1.getIdjustificacion());

        AsistenciaListaDTO d2 = dtos.get(1);
        assertEquals(12, d2.getIdasistencia());
        assertEquals("Ana", d2.getNombre());
        assertEquals("Gomez", d2.getApellido());
        assertEquals(fecha, d2.getFecha());
        assertEquals("PRESENTE", d2.getEstado());
        assertEquals("Historia", d2.getNombrecurso());
        assertNull(d2.getIdjustificacion());

        verify(justificacionRepository).findAsistenciasByAlumnoAndProfesor(eq(5), any(Date.class));
    }
}
