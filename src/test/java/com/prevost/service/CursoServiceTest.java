package com.prevost.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prevost.model.Curso;
import com.prevost.repository.CursoRepository;

@ExtendWith(MockitoExtension.class)
class CursoServiceTest {

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private CursoService cursoService;

    private Curso buildCurso(Long id, String nombre, String descripcion) {
        Curso c = new Curso();
        c.setIdcurso(id);
        c.setNombre(nombre);
        c.setDescripcion(descripcion);
        return c;
    }

    @Test
    void listarCursos_returnsAllFromRepository() {
        Curso c1 = buildCurso(1L, "Math", "Mathematics");
        Curso c2 = buildCurso(2L, "History", "World History");
        when(cursoRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

        List<Curso> result = cursoService.listarCursos();

        assertEquals(2, result.size());
        assertEquals(c1, result.get(0));
        assertEquals(c2, result.get(1));
        verify(cursoRepository).findAll();
    }

    @Test
    void buscarCurso_whenFound_returnsCurso() {
        Curso c = buildCurso(3L, "Physics", "Physics");
        when(cursoRepository.findById(3L)).thenReturn(Optional.of(c));

        Curso result = cursoService.buscarCurso(3L);

        assertEquals(c, result);
        verify(cursoRepository).findById(3L);
    }

    @Test
    void buscarCurso_whenNotFound_returnsNull() {
        when(cursoRepository.findById(4L)).thenReturn(Optional.empty());

        Curso result = cursoService.buscarCurso(4L);

        assertNull(result);
        verify(cursoRepository).findById(4L);
    }

    @Test
    void guardarCurso_savesAndReturns() {
        Curso input = buildCurso(null, "Chemistry", "Chemistry");
        Curso saved = buildCurso(5L, "Chemistry", "Chemistry");
        when(cursoRepository.save(input)).thenReturn(saved);

        Curso result = cursoService.guardarCurso(input);

        assertEquals(saved, result);
        verify(cursoRepository).save(input);
    }

    @Test
    void editarCurso_whenExists_updatesAndSaves() {
        Curso existing = buildCurso(6L, "Bio", "Biology");
        Curso update = buildCurso(null, "Biology", "Life Sciences");
        when(cursoRepository.findById(6L)).thenReturn(Optional.of(existing));
        when(cursoRepository.save(existing)).thenReturn(existing);

        Curso result = cursoService.editarCurso(6L, update);

        assertEquals(6L, result.getIdcurso());
        assertEquals("Biology", result.getNombre());
        assertEquals("Life Sciences", result.getDescripcion());
        verify(cursoRepository).findById(6L);
        verify(cursoRepository).save(existing);
    }

    @Test
    void editarCurso_whenNotExists_throwsRuntimeException() {
        when(cursoRepository.findById(7L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                cursoService.editarCurso(7L, buildCurso(null, "X", "X"))
        );
        assertTrue(ex.getMessage().contains("Curso no encontrado con ID: 7"));
        verify(cursoRepository).findById(7L);
        verify(cursoRepository, never()).save(any());
    }

    @Test
    void eliminarCurso_invokesRepositoryDelete() {
        cursoService.eliminarCurso(8L);

        verify(cursoRepository).deleteById(8L);
    }
}
