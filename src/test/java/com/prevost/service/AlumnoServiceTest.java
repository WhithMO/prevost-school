package com.prevost.service;

import com.prevost.dto.AlumnoReporteDTO;
import com.prevost.model.Alumno;
import com.prevost.repository.AlumnoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlumnoServiceTest {

    @Mock
    private AlumnoRepository alumnoRepository;

    @InjectMocks
    private AlumnoService alumnoService;

    private Alumno buildAlumno(Long id) {
        Alumno a = new Alumno();
        a.setIdalumno(id);
        a.setNombre("Nombre" + id);
        a.setApellido("Apellido" + id);
        a.setDni("DNI" + id);
        a.setFechaNacimiento(Date.valueOf("2000-01-01"));
        return a;
    }

    @Test
    void listarAlumnos_returnsAllFromRepository() {
        Alumno a1 = buildAlumno(1L);
        Alumno a2 = buildAlumno(2L);
        when(alumnoRepository.findAll()).thenReturn(Arrays.asList(a1, a2));

        List<Alumno> result = alumnoService.listarAlumnos();

        assertEquals(2, result.size());
        assertEquals(a1, result.get(0));
        verify(alumnoRepository).findAll();
    }

    @Test
    void listarAlumnosSinAula_returnsFromRepository() {
        Alumno a = buildAlumno(3L);
        when(alumnoRepository.findAlumnosSinAula()).thenReturn(Collections.singletonList(a));

        List<Alumno> result = alumnoService.listaralumnosinaula();

        assertEquals(1, result.size());
        assertEquals(a, result.get(0));
        verify(alumnoRepository).findAlumnosSinAula();
    }

    @Test
    void buscarAlumno_whenFound_returnsAlumno() {
        Alumno a = buildAlumno(4L);
        when(alumnoRepository.findById(4L)).thenReturn(Optional.of(a));

        Alumno result = alumnoService.buscarAlumno(4L);

        assertEquals(a, result);
        verify(alumnoRepository).findById(4L);
    }

    @Test
    void buscarAlumno_whenNotFound_returnsNull() {
        when(alumnoRepository.findById(5L)).thenReturn(Optional.empty());

        Alumno result = alumnoService.buscarAlumno(5L);

        assertNull(result);
        verify(alumnoRepository).findById(5L);
    }

    @Test
    void guardarAlumno_savesAndReturns() {
        Alumno toSave = buildAlumno(null);
        Alumno saved = buildAlumno(6L);
        when(alumnoRepository.save(toSave)).thenReturn(saved);

        Alumno result = alumnoService.guardarAlumno(toSave);

        assertEquals(saved, result);
        verify(alumnoRepository).save(toSave);
    }

    @Test
    void editarAlumno_whenExists_updatesFieldsAndSaves() {
        Alumno existing = buildAlumno(7L);
        Alumno update = new Alumno();
        update.setNombre("Nuevo");
        update.setApellido("ApellidoNuevo");
        update.setDni("DNINuevo");
        update.setFechaNacimiento(Date.valueOf("1999-12-31"));

        when(alumnoRepository.findById(7L)).thenReturn(Optional.of(existing));
        when(alumnoRepository.save(any(Alumno.class))).thenAnswer(inv -> inv.getArgument(0));

        Alumno result = alumnoService.editarAlumno(7L, update);

        assertEquals(7L, result.getIdalumno());
        assertEquals("Nuevo", result.getNombre());
        assertEquals("ApellidoNuevo", result.getApellido());
        assertEquals("DNINuevo", result.getDni());
        assertEquals(Date.valueOf("1999-12-31"), result.getFechaNacimiento());
        verify(alumnoRepository).findById(7L);
        verify(alumnoRepository).save(result);
    }

    @Test
    void editarAlumno_whenNotExists_throwsRuntimeException() {
        when(alumnoRepository.findById(8L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                alumnoService.editarAlumno(8L, new Alumno())
        );
        assertTrue(ex.getMessage().contains("Alumno no encontrado con ID: 8"));
        verify(alumnoRepository).findById(8L);
        verify(alumnoRepository, never()).save(any());
    }

    @Test
    void eliminarAlumno_invokesDeleteById() {
        alumnoService.eliminarAlumno(9L);

        verify(alumnoRepository).deleteById(9L);
    }

    @Test
    void obtenerAsistenciasPorAlumno_returnsDtoList() {
        AlumnoReporteDTO dto = new AlumnoReporteDTO(
                1L, "Nombre", "Apellido",
                Date.valueOf("2025-07-18"),
                "PRESENTE", "CursoX", "AulaY"
        );
        when(alumnoRepository.obtenerAsistenciasPorAlumno(1L)).thenReturn(List.of(dto));

        List<AlumnoReporteDTO> result = alumnoService.obtenerAsistenciasPorAlumno(1L);

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
        verify(alumnoRepository).obtenerAsistenciasPorAlumno(1L);
    }
}
