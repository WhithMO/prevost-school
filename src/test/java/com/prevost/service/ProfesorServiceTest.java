package com.prevost.service;

import com.prevost.dto.AlumnoClaseDTO;
import com.prevost.dto.ListaClaseProfesorDTO;
import com.prevost.model.Profesor;
import com.prevost.repository.ProfesorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfesorServiceTest {

    @Mock
    private ProfesorRepository profesorRepository;

    @InjectMocks
    private ProfesorService profesorService;

    private Profesor buildProfesor(Long id, String nombre) {
        Profesor p = new Profesor();
        p.setIdprofesor(id);
        p.setNombre(nombre);
        p.setApellido("Apellido");
        p.setDni("DNI");
        p.setEspecialidad("Esp");
        p.setTelefono(123);
        return p;
    }

    @Test
    void listarProfesores_returnsAll() {
        Profesor p1 = buildProfesor(1L, "Juan");
        Profesor p2 = buildProfesor(2L, "Ana");
        when(profesorRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Profesor> result = profesorService.listarProfesores();

        assertEquals(2, result.size());
        assertSame(p1, result.get(0));
        assertSame(p2, result.get(1));
        verify(profesorRepository).findAll();
    }

    @Test
    void buscarProfesor_foundAndNotFound() {
        Profesor p = buildProfesor(5L, "Luis");
        when(profesorRepository.findById(5L)).thenReturn(Optional.of(p));
        assertEquals(p, profesorService.buscarProfesor(5L));
        verify(profesorRepository).findById(5L);

        when(profesorRepository.findById(6L)).thenReturn(Optional.empty());
        assertNull(profesorService.buscarProfesor(6L));
        verify(profesorRepository).findById(6L);
    }

    @Test
    void guardarProfesor_savesAndReturns() {
        Profesor in = buildProfesor(null, "Miguel");
        Profesor saved = buildProfesor(10L, "Miguel");
        when(profesorRepository.save(in)).thenReturn(saved);

        Profesor result = profesorService.guardarProfesor(in);

        assertEquals(saved, result);
        verify(profesorRepository).save(in);
    }

    @Test
    void editarProfesor_whenExists_updatesAndSaves() {
        Profesor existing = buildProfesor(7L, "OldName");
        Profesor update = buildProfesor(null, "NewName");
        when(profesorRepository.findById(7L)).thenReturn(Optional.of(existing));
        when(profesorRepository.save(existing)).thenReturn(existing);

        Profesor result = profesorService.editarProfesor(7L, update);

        assertEquals(7L, result.getIdprofesor());
        assertEquals("NewName", result.getNombre());
        verify(profesorRepository).findById(7L);
        verify(profesorRepository).save(existing);
    }

    @Test
    void editarProfesor_whenNotExists_throws() {
        when(profesorRepository.findById(8L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> profesorService.editarProfesor(8L, buildProfesor(null, "x"))
        );
        assertTrue(ex.getMessage().contains("Profesor no encontrado con ID: 8"));
        verify(profesorRepository).findById(8L);
        verify(profesorRepository, never()).save(any());
    }

    @Test
    void eliminarProfesor_invokesDelete() {
        profesorService.eliminarProfesor(9L);
        verify(profesorRepository).deleteById(9L);
    }

    @Test
    void obtenerClasesPorProfesor_mapsTimestampAndString() {
        Timestamp ts = Timestamp.valueOf(LocalDateTime.of(2025,7,18,14,30,0));
        Object[] row1 = new Object[]{"100","Lunes","08:00","10:00","CursoX","AulaY", ts};
        Object[] row2 = new Object[]{"101","Martes","09:00","11:00","CursoZ","AulaW", "2025-07-19T09:00:00"};
        when(profesorRepository.obtenerClasesPorProfesor(3L))
                .thenReturn(Arrays.asList(row1, row2));

        List<ListaClaseProfesorDTO> dtos = profesorService.obtenerClasesPorProfesor(3L);

        assertEquals(2, dtos.size());

        ListaClaseProfesorDTO d1 = dtos.get(0);
        assertEquals("100", d1.getIdClase());
        assertEquals("Lunes", d1.getDiaSemana());
        assertEquals("08:00", d1.getHoraInicio());
        assertEquals("10:00", d1.getHoraFin());
        assertEquals("CursoX", d1.getCurso());
        assertEquals("AulaY", d1.getAula());
        assertEquals("2025-07-18 14:30:00", d1.getFechadeclase());

        ListaClaseProfesorDTO d2 = dtos.get(1);
        assertEquals("101", d2.getIdClase());
        assertEquals("Martes", d2.getDiaSemana());
        assertEquals("09:00", d2.getHoraInicio());
        assertEquals("11:00", d2.getHoraFin());
        assertEquals("CursoZ", d2.getCurso());
        assertEquals("AulaW", d2.getAula());
        assertEquals("2025-07-19T09:00:00", d2.getFechadeclase());

        verify(profesorRepository).obtenerClasesPorProfesor(3L);
    }

    @Test
    void obtenerAlumnosPorClase_forwardsToRepository() {
        AlumnoClaseDTO dto = new AlumnoClaseDTO(
                1L, "Miércoles", "10:00", "12:00",
                "Alumno", "Apellido", "PRESENTE",
                50L, 5L, "AulaA", new java.sql.Date(System.currentTimeMillis())
        );
        when(profesorRepository.obtenerAlumnosPorClase(4L))
                .thenReturn(Collections.singletonList(dto));

        List<AlumnoClaseDTO> result = profesorService.obtenerAlumnosPorClase(4L);

        assertEquals(1, result.size());
        assertSame(dto, result.get(0));
        verify(profesorRepository).obtenerAlumnosPorClase(4L);
    }
}
