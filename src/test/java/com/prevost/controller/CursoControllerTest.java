package com.prevost.controller;

import com.prevost.model.Curso;
import com.prevost.service.CursoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CursoControllerTest {

    @Mock
    private CursoService cursoService;

    @InjectMocks
    private CursoController cursoController;

    private Curso buildSample(Long id) {
        Curso c = new Curso();
        c.setIdcurso(id);
        c.setNombre("Curso " + id);
        c.setDescripcion("Descripción " + id);
        return c;
    }

    @Test
    void listarCursos_returnsListOfCursos() {
        Curso c1 = buildSample(1L);
        Curso c2 = buildSample(2L);
        List<Curso> expected = Arrays.asList(c1, c2);
        when(cursoService.listarCursos()).thenReturn(expected);

        ResponseEntity<List<Curso>> response = cursoController.listarCursos();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expected, response.getBody());
        verify(cursoService).listarCursos();
    }

    @Test
    void buscarCurso_whenExists_returnsOk() {
        Curso sample = buildSample(5L);
        when(cursoService.buscarCurso(5L)).thenReturn(sample);

        ResponseEntity<Curso> response = cursoController.buscarCurso(5L);

        assertEquals(200, response.getStatusCodeValue());
        assertSame(sample, response.getBody());
        verify(cursoService).buscarCurso(5L);
    }

    @Test
    void buscarCurso_whenNotFound_returns404() {
        when(cursoService.buscarCurso(99L)).thenReturn(null);

        ResponseEntity<Curso> response = cursoController.buscarCurso(99L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(cursoService).buscarCurso(99L);
    }

    @Test
    void agregarCurso_returnsCreated() {
        Curso input = buildSample(null);
        Curso saved = buildSample(10L);
        when(cursoService.guardarCurso(input)).thenReturn(saved);

        ResponseEntity<Curso> response = cursoController.agregarCurso(input);

        assertEquals(201, response.getStatusCodeValue());
        assertSame(saved, response.getBody());
        verify(cursoService).guardarCurso(input);
    }

    @Test
    void editarCurso_whenExists_returnsOk() {
        Curso input = buildSample(null);
        Curso edited = buildSample(3L);
        when(cursoService.editarCurso(3L, input)).thenReturn(edited);

        ResponseEntity<Curso> response = cursoController.editarCurso(3L, input);

        assertEquals(200, response.getStatusCodeValue());
        assertSame(edited, response.getBody());
        verify(cursoService).editarCurso(3L, input);
    }

    @Test
    void editarCurso_whenNotExists_returns404() {
        Curso anyCurso = buildSample(null);
        when(cursoService.editarCurso(eq(7L), any())).thenThrow(new RuntimeException("Not found"));

        ResponseEntity<Curso> response = cursoController.editarCurso(7L, anyCurso);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(cursoService).editarCurso(7L, anyCurso);
    }

    @Test
    void eliminarCurso_returnsNoContent() {
        doNothing().when(cursoService).eliminarCurso(4L);

        ResponseEntity<Void> response = cursoController.eliminarCurso(4L);

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(cursoService).eliminarCurso(4L);
    }
}
