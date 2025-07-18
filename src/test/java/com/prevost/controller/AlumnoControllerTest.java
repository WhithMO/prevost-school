package com.prevost.controller;

import com.prevost.dto.AlumnoReporteDTO;
import com.prevost.model.Alumno;
import com.prevost.service.AlumnoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlumnoControllerTest {

    @Mock
    private AlumnoService alumnoService;

    @InjectMocks
    private AlumnoController alumnoController;

    @Test
    void listarAlumnos_returnsListOfAlumnos() {
        Alumno a1 = new Alumno();
        a1.setIdalumno(1L);
        a1.setNombre("Juan");
        a1.setApellido("Perez");
        Alumno a2 = new Alumno();
        a2.setIdalumno(2L);
        a2.setNombre("Maria");
        a2.setApellido("Lopez");
        List<Alumno> expected = Arrays.asList(a1, a2);
        when(alumnoService.listarAlumnos()).thenReturn(expected);

        ResponseEntity<List<Alumno>> response = alumnoController.listarAlumnos();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expected, response.getBody());
        verify(alumnoService).listarAlumnos();
    }

    @Test
    void listarAlumnosSinAula_returnsEmptyList() {
        when(alumnoService.listaralumnosinaula()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Alumno>> response = alumnoController.listaralumnosinaula();

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isEmpty());
        verify(alumnoService).listaralumnosinaula();
    }

    @Test
    void buscarAlumno_whenExists_returnsAlumno() {
        Alumno alumno = new Alumno();
        alumno.setIdalumno(1L);
        when(alumnoService.buscarAlumno(1L)).thenReturn(alumno);

        ResponseEntity<Alumno> response = alumnoController.buscarAlumno(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(alumno, response.getBody());
        verify(alumnoService).buscarAlumno(1L);
    }

    @Test
    void buscarAlumno_whenNotExists_returnsNotFound() {
        when(alumnoService.buscarAlumno(1L)).thenReturn(null);

        ResponseEntity<Alumno> response = alumnoController.buscarAlumno(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(alumnoService).buscarAlumno(1L);
    }

    @Test
    void agregarAlumno_returnsCreatedAlumno() {
        Alumno input = new Alumno();
        input.setNombre("Ana");
        input.setApellido("Gomez");
        Alumno saved = new Alumno();
        saved.setIdalumno(3L);
        saved.setNombre("Ana");
        saved.setApellido("Gomez");
        when(alumnoService.guardarAlumno(input)).thenReturn(saved);

        ResponseEntity<Alumno> response = alumnoController.agregarAlumno(input);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(saved, response.getBody());
        verify(alumnoService).guardarAlumno(input);
    }

    @Test
    void editarAlumno_whenExists_returnsEditedAlumno() {
        Alumno input = new Alumno();
        input.setNombre("Luis");
        input.setApellido("Diaz");
        Alumno edited = new Alumno();
        edited.setIdalumno(1L);
        edited.setNombre("Luis");
        edited.setApellido("Diaz");
        when(alumnoService.editarAlumno(1L, input)).thenReturn(edited);

        ResponseEntity<Alumno> response = alumnoController.editarAlumno(1L, input);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(edited, response.getBody());
        verify(alumnoService).editarAlumno(1L, input);
    }

    @Test
    void editarAlumno_whenNotExists_returnsNotFound() {
        when(alumnoService.editarAlumno(eq(1L), any(Alumno.class)))
                .thenThrow(new RuntimeException("Not found"));

        ResponseEntity<Alumno> response = alumnoController.editarAlumno(1L, new Alumno());

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(alumnoService).editarAlumno(eq(1L), any(Alumno.class));
    }

    @Test
    void eliminarAlumno_invokesServiceAndReturnsNoContent() {
        doNothing().when(alumnoService).eliminarAlumno(2L);

        ResponseEntity<Void> response = alumnoController.eliminarAlumno(2L);

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(alumnoService).eliminarAlumno(2L);
    }

    @Test
    void obtenerReporteAsistencias_returnsDtoList() {
        AlumnoReporteDTO dto = new AlumnoReporteDTO(
                1L, "Juan", "Perez",
                Date.valueOf("2025-07-18"),
                "PRESENTE", "Matemáticas", "A1"
        );
        when(alumnoService.obtenerAsistenciasPorAlumno(1L)).thenReturn(Arrays.asList(dto));

        List<AlumnoReporteDTO> result = alumnoController.obtenerReporteAsistencias(1L);

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
        verify(alumnoService).obtenerAsistenciasPorAlumno(1L);
    }
}
