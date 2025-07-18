package com.prevost.controller;

import com.prevost.dto.AulaRequestDTO;
import com.prevost.model.Aula;
import com.prevost.model.AlumnoAula;
import com.prevost.service.AulaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AulaControllerTest {

    @Mock
    private AulaService aulaService;

    @InjectMocks
    private AulaController aulaController;

    @Test
    void listarAulas_returnsListOfAulas() {
        Aula a1 = new Aula();
        a1.setIdaula(1L);
        a1.setNombre("A1");
        Aula a2 = new Aula();
        a2.setIdaula(2L);
        a2.setNombre("A2");
        List<Aula> expected = Arrays.asList(a1, a2);
        when(aulaService.listarAulas()).thenReturn(expected);

        ResponseEntity<List<Aula>> resp = aulaController.listarAulas();

        assertEquals(200, resp.getStatusCodeValue());
        assertEquals(expected, resp.getBody());
        verify(aulaService).listarAulas();
    }

    @Test
    void buscarAula_whenExists_returnsAula() {
        Aula aula = new Aula();
        aula.setIdaula(1L);
        when(aulaService.buscarAula(1L)).thenReturn(aula);

        ResponseEntity<Aula> resp = aulaController.buscarAula(1L);

        assertEquals(200, resp.getStatusCodeValue());
        assertSame(aula, resp.getBody());
        verify(aulaService).buscarAula(1L);
    }

    @Test
    void buscarAula_whenNotExists_returnsNotFound() {
        when(aulaService.buscarAula(99L)).thenReturn(null);

        ResponseEntity<Aula> resp = aulaController.buscarAula(99L);

        assertEquals(404, resp.getStatusCodeValue());
        assertNull(resp.getBody());
        verify(aulaService).buscarAula(99L);
    }

    @Test
    void agregarAula_returnsCreatedAula() {
        Aula input = new Aula();
        input.setNombre("Nueva");
        Aula saved = new Aula();
        saved.setIdaula(10L);
        saved.setNombre("Nueva");
        when(aulaService.guardarAula(input)).thenReturn(saved);

        ResponseEntity<Aula> resp = aulaController.agregarAula(input);

        assertEquals(201, resp.getStatusCodeValue());
        assertSame(saved, resp.getBody());
        verify(aulaService).guardarAula(input);
    }

    @Test
    void editarAula_whenExists_returnsEdited() {
        Aula input = new Aula();
        input.setNombre("Modificada");
        Aula edited = new Aula();
        edited.setIdaula(5L);
        edited.setNombre("Modificada");
        when(aulaService.editarAula(5L, input)).thenReturn(edited);

        ResponseEntity<Aula> resp = aulaController.editarAula(5L, input);

        assertEquals(200, resp.getStatusCodeValue());
        assertSame(edited, resp.getBody());
        verify(aulaService).editarAula(5L, input);
    }

    @Test
    void editarAula_whenNotExists_returnsNotFound() {
        Aula anyAula = new Aula();
        when(aulaService.editarAula(eq(7L), any())).thenThrow(new RuntimeException("no existe"));

        ResponseEntity<Aula> resp = aulaController.editarAula(7L, anyAula);

        assertEquals(404, resp.getStatusCodeValue());
        assertNull(resp.getBody());
        verify(aulaService).editarAula(7L, anyAula);
    }

    @Test
    void crearAulaConAlumnos_success_returnsOk() {
        AulaRequestDTO req = new AulaRequestDTO();
        req.setNombre("A3");
        req.setCapacidad(30);
        req.setAlumnosIds(List.of(1L, 2L));
        Aula result = new Aula();
        result.setIdaula(20L);
        result.setNombre("A3");
        when(aulaService.guardarAulaConAlumnos(req)).thenReturn(result);

        ResponseEntity<?> resp = aulaController.crearAula(req);

        assertEquals(200, resp.getStatusCodeValue());
        assertSame(result, resp.getBody());
        verify(aulaService).guardarAulaConAlumnos(req);
    }

    @Test
    void crearAulaConAlumnos_badRequest_onInvalidArgument() {
        AulaRequestDTO req = new AulaRequestDTO();
        when(aulaService.guardarAulaConAlumnos(req))
                .thenThrow(new IllegalArgumentException("Datos inválidos"));

        ResponseEntity<?> resp = aulaController.crearAula(req);

        assertEquals(400, resp.getStatusCodeValue());
        assertEquals("Datos inválidos", resp.getBody());
        verify(aulaService).guardarAulaConAlumnos(req);
    }

    @Test
    void crearAulaConAlumnos_internalError_onException() {
        AulaRequestDTO req = new AulaRequestDTO();
        when(aulaService.guardarAulaConAlumnos(req))
                .thenThrow(new RuntimeException("Falló DB"));

        ResponseEntity<?> resp = aulaController.crearAula(req);

        assertEquals(500, resp.getStatusCodeValue());
        assertEquals("Ocurrió un error inesperado.", resp.getBody());
        verify(aulaService).guardarAulaConAlumnos(req);
    }

    @Test
    void actualizarAula_whenNotFound_returnsNotFound() {
        when(aulaService.obtenerAulaPorId(99L)).thenReturn(Optional.empty());
        AulaRequestDTO dummy = new AulaRequestDTO();

        ResponseEntity<?> resp = aulaController.actualizarAula(99L, dummy);

        assertEquals(404, resp.getStatusCodeValue());
        verify(aulaService).obtenerAulaPorId(99L);
        verify(aulaService, never()).actualizarAula(anyLong(), any());
    }

    @Test
    void actualizarAula_whenFound_returnsOk() {
        Aula existing = new Aula();
        existing.setIdaula(3L);
        when(aulaService.obtenerAulaPorId(3L)).thenReturn(Optional.of(existing));
        AulaRequestDTO req = new AulaRequestDTO();
        Aula updated = new Aula();
        updated.setIdaula(3L);
        when(aulaService.actualizarAula(3L, req)).thenReturn(updated);

        ResponseEntity<?> resp = aulaController.actualizarAula(3L, req);

        assertEquals(200, resp.getStatusCodeValue());
        assertSame(updated, resp.getBody());
        verify(aulaService).obtenerAulaPorId(3L);
        verify(aulaService).actualizarAula(3L, req);
    }

    @Test
    void eliminarAula_returnsNoContent() {
        doNothing().when(aulaService).eliminarAula(4L);

        ResponseEntity<Void> resp = aulaController.eliminarAula(4L);

        assertEquals(204, resp.getStatusCodeValue());
        assertNull(resp.getBody());
        verify(aulaService).eliminarAula(4L);
    }

    @Test
    void obtenerAlumnosPorAula_withResults_returnsOk() {
        AlumnoAula aa = new AlumnoAula();
        aa.setIdalumnoaula(100L);
        List<AlumnoAula> list = List.of(aa);
        when(aulaService.obtenerAlumnosPorAula(2L)).thenReturn(list);

        ResponseEntity<List<AlumnoAula>> resp = aulaController.obtenerAlumnosPorAula(2L);

        assertEquals(200, resp.getStatusCodeValue());
        assertEquals(list, resp.getBody());
        verify(aulaService).obtenerAlumnosPorAula(2L);
    }

    @Test
    void obtenerAlumnosPorAula_empty_returnsNoContent() {
        when(aulaService.obtenerAlumnosPorAula(5L)).thenReturn(Collections.emptyList());

        ResponseEntity<List<AlumnoAula>> resp = aulaController.obtenerAlumnosPorAula(5L);

        assertEquals(204, resp.getStatusCodeValue());
        assertNull(resp.getBody());
        verify(aulaService).obtenerAlumnosPorAula(5L);
    }
}
