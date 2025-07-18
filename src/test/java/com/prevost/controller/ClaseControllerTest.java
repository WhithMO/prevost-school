package com.prevost.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.prevost.model.Clase;
import com.prevost.service.ClaseService;

@ExtendWith(MockitoExtension.class)
class ClaseControllerTest {

    @Mock
    private ClaseService claseService;

    @InjectMocks
    private ClaseController claseController;

    private Clase buildSample(Long id) {
        Clase c = new Clase();
        c.setIdClase(id);
        c.setDiaSemana("Lunes");
        c.setHoraInicio("08:00");
        c.setHoraFin("10:00");
        c.setFechadeclase(LocalDateTime.of(2025, 7, 18, 8, 0));
        return c;
    }

    @Test
    void listarClases_returnsList() {
        Clase c1 = buildSample(1L);
        Clase c2 = buildSample(2L);
        List<Clase> expected = Arrays.asList(c1, c2);
        when(claseService.listarClases()).thenReturn(expected);

        ResponseEntity<List<Clase>> resp = claseController.listarClases();

        assertEquals(200, resp.getStatusCodeValue());
        assertEquals(expected, resp.getBody());
        verify(claseService).listarClases();
    }

    @Test
    void buscarClase_whenExists_returnsOk() {
        Clase sample = buildSample(5L);
        when(claseService.buscarClase(5L)).thenReturn(sample);

        ResponseEntity<Clase> resp = claseController.buscarClase(5L);

        assertEquals(200, resp.getStatusCodeValue());
        assertSame(sample, resp.getBody());
        verify(claseService).buscarClase(5L);
    }

    @Test
    void buscarClase_whenNotFound_returns404() {
        when(claseService.buscarClase(99L)).thenReturn(null);

        ResponseEntity<Clase> resp = claseController.buscarClase(99L);

        assertEquals(404, resp.getStatusCodeValue());
        assertNull(resp.getBody());
        verify(claseService).buscarClase(99L);
    }

    @Test
    void agregarClase_returnsCreated() {
        Clase input = buildSample(null);
        Clase saved = buildSample(10L);
        when(claseService.guardarClase(input)).thenReturn(saved);

        ResponseEntity<Clase> resp = claseController.agregarClase(input);

        assertEquals(201, resp.getStatusCodeValue());
        assertSame(saved, resp.getBody());
        verify(claseService).guardarClase(input);
    }

    @Test
    void editarClase_whenExists_returnsOk() {
        Clase input = buildSample(null);
        Clase edited = buildSample(3L);
        when(claseService.editarClase(3L, input)).thenReturn(edited);

        ResponseEntity<Clase> resp = claseController.editarClase(3L, input);

        assertEquals(200, resp.getStatusCodeValue());
        assertSame(edited, resp.getBody());
        verify(claseService).editarClase(3L, input);
    }

    @Test
    void editarClase_whenNotExists_returns404() {
        Clase anyClase = buildSample(null);
        when(claseService.editarClase(eq(7L), any())).thenThrow(new RuntimeException("no existe"));

        ResponseEntity<Clase> resp = claseController.editarClase(7L, anyClase);

        assertEquals(404, resp.getStatusCodeValue());
        assertNull(resp.getBody());
        verify(claseService).editarClase(7L, anyClase);
    }

    @Test
    void eliminarClase_returnsNoContent() {
        doNothing().when(claseService).eliminarClase(4L);

        ResponseEntity<Void> resp = claseController.eliminarClase(4L);

        assertEquals(204, resp.getStatusCodeValue());
        assertNull(resp.getBody());
        verify(claseService).eliminarClase(4L);
    }
}
