package com.prevost.controller;

import com.prevost.dto.AlumnoClaseDTO;
import com.prevost.dto.ListaClaseProfesorDTO;
import com.prevost.model.Asistencia;
import com.prevost.model.Profesor;
import com.prevost.service.AsistenciaService;
import com.prevost.service.ProfesorService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfesorControllerTest {

    @Mock
    private ProfesorService profesorService;

    @Mock
    private AsistenciaService asistenciaService;

    @InjectMocks
    private ProfesorController controller;

    private Profesor buildProfesor(Long id) {
        Profesor p = new Profesor();
        p.setIdprofesor(id);
        p.setNombre("Nombre" + id);
        p.setApellido("Apellido" + id);
        return p;
    }

    private Asistencia buildAsistencia(Long id) {
        Asistencia a = new Asistencia();
        a.setIdasistencia(id);
        a.setEstado("PRESENTE");
        a.setFecha(Date.valueOf("2025-07-18"));
        return a;
    }

    @Test
    void listarProfesores_returnsList() {
        Profesor p1 = buildProfesor(1L);
        Profesor p2 = buildProfesor(2L);
        when(profesorService.listarProfesores()).thenReturn(Arrays.asList(p1, p2));

        ResponseEntity<List<Profesor>> resp = controller.listarProfesores();

        assertEquals(200, resp.getStatusCodeValue());
        assertEquals(2, resp.getBody().size());
        assertEquals(p1, resp.getBody().get(0));
        verify(profesorService).listarProfesores();
    }

    @Test
    void buscarProfesor_whenExists_returnsOk() {
        Profesor p = buildProfesor(5L);
        when(profesorService.buscarProfesor(5L)).thenReturn(p);

        ResponseEntity<Profesor> resp = controller.buscarProfesor(5L);

        assertEquals(200, resp.getStatusCodeValue());
        assertSame(p, resp.getBody());
        verify(profesorService).buscarProfesor(5L);
    }

    @Test
    void buscarProfesor_whenNotFound_returns404() {
        when(profesorService.buscarProfesor(99L)).thenReturn(null);

        ResponseEntity<Profesor> resp = controller.buscarProfesor(99L);

        assertEquals(404, resp.getStatusCodeValue());
        assertNull(resp.getBody());
        verify(profesorService).buscarProfesor(99L);
    }

    @Test
    void agregarProfesor_returnsCreated() {
        Profesor input = buildProfesor(null);
        Profesor saved = buildProfesor(10L);
        when(profesorService.guardarProfesor(input)).thenReturn(saved);

        ResponseEntity<Profesor> resp = controller.agregarProfesor(input);

        assertEquals(201, resp.getStatusCodeValue());
        assertSame(saved, resp.getBody());
        verify(profesorService).guardarProfesor(input);
    }

    @Test
    void editarProfesor_whenExists_returnsOk() {
        Profesor input = buildProfesor(null);
        Profesor edited = buildProfesor(3L);
        when(profesorService.editarProfesor(3L, input)).thenReturn(edited);

        ResponseEntity<Profesor> resp = controller.editarProfesor(3L, input);

        assertEquals(200, resp.getStatusCodeValue());
        assertSame(edited, resp.getBody());
        verify(profesorService).editarProfesor(3L, input);
    }

    @Test
    void editarProfesor_whenNotExists_returns404() {
        Profesor anyProf = buildProfesor(null);
        when(profesorService.editarProfesor(eq(7L), any())).thenThrow(new RuntimeException("no existe"));

        ResponseEntity<Profesor> resp = controller.editarProfesor(7L, anyProf);

        assertEquals(404, resp.getStatusCodeValue());
        assertNull(resp.getBody());
        verify(profesorService).editarProfesor(7L, anyProf);
    }

    @Test
    void eliminarProfesor_returnsNoContent() {
        doNothing().when(profesorService).eliminarProfesor(4L);

        ResponseEntity<Void> resp = controller.eliminarProfesor(4L);

        assertEquals(204, resp.getStatusCodeValue());
        assertNull(resp.getBody());
        verify(profesorService).eliminarProfesor(4L);
    }

    @Test
    void obtenerClasesPorProfesor_whenEmpty_returnsNoContent() {
        when(profesorService.obtenerClasesPorProfesor(2L)).thenReturn(Collections.emptyList());

        ResponseEntity<List<ListaClaseProfesorDTO>> resp = controller.obtenerClasesPorProfesor(2L);

        assertEquals(204, resp.getStatusCodeValue());
        assertNull(resp.getBody());
        verify(profesorService).obtenerClasesPorProfesor(2L);
    }

    @Test
    void obtenerClasesPorProfesor_withData_returnsOk() {
        ListaClaseProfesorDTO dto = new ListaClaseProfesorDTO(
                "1", "Lunes", "08:00", "10:00", "Curso1", "Aula1", "2025-07-18"
        );
        when(profesorService.obtenerClasesPorProfesor(3L)).thenReturn(List.of(dto));

        ResponseEntity<List<ListaClaseProfesorDTO>> resp = controller.obtenerClasesPorProfesor(3L);

        assertEquals(200, resp.getStatusCodeValue());
        assertEquals(1, resp.getBody().size());
        assertEquals(dto, resp.getBody().get(0));
        verify(profesorService).obtenerClasesPorProfesor(3L);
    }

    @Test
    void obtenerAlumnosPorClase_returnsList() {
        AlumnoClaseDTO dto = new AlumnoClaseDTO(
                1L, "Martes", "09:00", "11:00",
                "Juan", "Perez", "AUSENTE",
                100L, 10L, "AulaX", Date.valueOf("2025-07-18")
        );
        when(profesorService.obtenerAlumnosPorClase(5L)).thenReturn(List.of(dto));

        ResponseEntity<List<AlumnoClaseDTO>> resp = controller.obtenerAlumnosPorClase(5L);

        assertEquals(200, resp.getStatusCodeValue());
        assertEquals(List.of(dto), resp.getBody());
        verify(profesorService).obtenerAlumnosPorClase(5L);
    }

    @Test
    void agregarVariasAsistencias_returnsCreatedList() {
        Asistencia a1 = buildAsistencia(1L);
        Asistencia a2 = buildAsistencia(2L);
        when(asistenciaService.guardarVariasAsistencias(List.of(a1, a2)))
                .thenReturn(List.of(a1, a2));

        ResponseEntity<List<Asistencia>> resp = controller.agregarVariasAsistencias(List.of(a1, a2));

        assertEquals(201, resp.getStatusCodeValue());
        assertEquals(List.of(a1, a2), resp.getBody());
        verify(asistenciaService).guardarVariasAsistencias(List.of(a1, a2));
    }

    @Test
    void editarVariasAsistencias_returnsList() {
        Asistencia a = buildAsistencia(3L);
        when(asistenciaService.editarVariasAsistencias(List.of(a))).thenReturn(List.of(a));

        List<Asistencia> result = controller.editarVariasAsistencias(List.of(a));

        assertEquals(1, result.size());
        assertEquals(a, result.get(0));
        verify(asistenciaService).editarVariasAsistencias(List.of(a));
    }

    @Test
    void listarAsistencia_returnsOk() {
        Asistencia a = buildAsistencia(4L);
        when(asistenciaService.listarAsistencias()).thenReturn(List.of(a));

        ResponseEntity<List<Asistencia>> resp = controller.listarAsistencia();

        assertEquals(200, resp.getStatusCodeValue());
        assertEquals(List.of(a), resp.getBody());
        verify(asistenciaService).listarAsistencias();
    }

    @Test
    void getAsistenciasPorClase_returnsOk() {
        Asistencia a = buildAsistencia(5L);
        when(asistenciaService.obtenerAsistenciasPorClase(7L)).thenReturn(List.of(a));

        ResponseEntity<List<Asistencia>> resp = controller.getAsistenciasPorClase(7L);

        assertEquals(200, resp.getStatusCodeValue());
        assertEquals(List.of(a), resp.getBody());
        verify(asistenciaService).obtenerAsistenciasPorClase(7L);
    }
}
