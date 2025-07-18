package com.prevost.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prevost.model.Aula;
import com.prevost.model.Clase;
import com.prevost.model.Curso;
import com.prevost.model.Profesor;
import com.prevost.repository.AsistenciaRepository;
import com.prevost.repository.ClaseRepository;

@ExtendWith(MockitoExtension.class)
class ClaseServiceTest {

    @Mock
    private ClaseRepository claseRepository;

    @Mock
    private AsistenciaRepository asistenciaRepository;

    @InjectMocks
    private ClaseService claseService;

    private Clase buildClase(Long id) {
        Clase c = new Clase();
        c.setIdClase(id);
        c.setDiaSemana("Lunes");
        c.setHoraInicio("08:00");
        c.setHoraFin("10:00");
        c.setFechadeclase(LocalDateTime.of(2025, 7, 18, 8, 0));
        c.setCurso(new Curso() {{ setIdcurso(1L); }});
        c.setAula(new Aula() {{ setIdaula(2L); }});
        c.setProfesor(new Profesor() {{ setIdprofesor(3L); }});
        return c;
    }

    @Test
    void listarClases_returnsAll() {
        Clase c1 = buildClase(1L);
        Clase c2 = buildClase(2L);
        when(claseRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

        List<Clase> result = claseService.listarClases();

        assertEquals(2, result.size());
        assertEquals(c1, result.get(0));
        verify(claseRepository).findAll();
    }

    @Test
    void buscarClase_foundAndNotFound() {
        Clase c = buildClase(5L);
        when(claseRepository.findById(5L)).thenReturn(Optional.of(c));
        assertEquals(c, claseService.buscarClase(5L));
        verify(claseRepository).findById(5L);

        when(claseRepository.findById(6L)).thenReturn(Optional.empty());
        assertNull(claseService.buscarClase(6L));
        verify(claseRepository).findById(6L);
    }

    @Test
    void guardarClase_savesAndCreatesAsistencias() {
        Clase toSave = buildClase(null);
        Clase saved = buildClase(10L);
        when(claseRepository.save(toSave)).thenReturn(saved);

        Clase result = claseService.guardarClase(toSave);

        assertEquals(saved, result);
        verify(claseRepository).save(toSave);
        verify(asistenciaRepository).crearAsistenciasParaClase(10L);
    }

    @Test
    void editarClase_whenExists_updatesAndSaves() {
        Clase existing = buildClase(7L);
        Clase update = buildClase(null);
        update.setDiaSemana("Martes");
        update.setHoraInicio("09:00");
        update.setHoraFin("11:00");
        update.setFechadeclase(LocalDateTime.of(2025, 7, 19, 9, 0));
        update.setCurso(new Curso() {{ setIdcurso(8L); }});
        update.setAula(new Aula() {{ setIdaula(9L); }});
        update.setProfesor(new Profesor() {{ setIdprofesor(10L); }});

        when(claseRepository.findById(7L)).thenReturn(Optional.of(existing));
        when(claseRepository.save(existing)).thenReturn(existing);

        Clase result = claseService.editarClase(7L, update);

        assertEquals(7L, result.getIdClase());
        assertEquals("Martes", result.getDiaSemana());
        assertEquals("09:00", result.getHoraInicio());
        assertEquals("11:00", result.getHoraFin());
        assertEquals(LocalDateTime.of(2025, 7, 19, 9, 0), result.getFechadeclase());
        assertEquals(8L, result.getCurso().getIdcurso());
        assertEquals(9L, result.getAula().getIdaula());
        assertEquals(10L, result.getProfesor().getIdprofesor());

        verify(claseRepository).findById(7L);
        verify(claseRepository).save(existing);
    }

    @Test
    void editarClase_whenNotExists_throws() {
        when(claseRepository.findById(11L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                claseService.editarClase(11L, buildClase(null))
        );
        assertTrue(ex.getMessage().contains("Clase no encontrada con ID: 11"));
        verify(claseRepository).findById(11L);
        verify(claseRepository, never()).save(any());
    }

    @Test
    void eliminarClase_invokesDelete() {
        claseService.eliminarClase(4L);

        verify(claseRepository).deleteById(4L);
    }
}
