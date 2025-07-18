package com.prevost.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prevost.dto.AulaRequestDTO;
import com.prevost.model.Alumno;
import com.prevost.model.AlumnoAula;
import com.prevost.model.Aula;
import com.prevost.repository.AlumnoAulaRepository;
import com.prevost.repository.AlumnoRepository;
import com.prevost.repository.AulaRepository;

@ExtendWith(MockitoExtension.class)
class AulaServiceTest {

    @Mock
    private AulaRepository aulaRepository;
    @Mock
    private AlumnoRepository alumnoRepository;
    @Mock
    private AlumnoAulaRepository alumnoAulaRepository;

    @InjectMocks
    private AulaService aulaService;

    private Aula buildAula(Long id, String nombre, int capacidad) {
        Aula a = new Aula();
        a.setIdaula(id);
        a.setNombre(nombre);
        a.setCapacidad(capacidad);
        return a;
    }

    private Alumno buildAlumno(Long id) {
        Alumno a = new Alumno();
        a.setIdalumno(id);
        return a;
    }

    private AlumnoAula buildAlumnoAula(Long id, Alumno alumno, Aula aula) {
        AlumnoAula aa = new AlumnoAula();
        aa.setIdalumnoaula(id);
        aa.setAlumno(alumno);
        aa.setAula(aula);
        return aa;
    }

    @Test
    void listarAulas_returnsAll() {
        List<Aula> expected = Arrays.asList(
                buildAula(1L, "A1", 10),
                buildAula(2L, "A2", 20)
        );
        when(aulaRepository.findAll()).thenReturn(expected);

        List<Aula> result = aulaService.listarAulas();

        assertEquals(expected, result);
        verify(aulaRepository).findAll();
    }

    @Test
    void buscarAula_foundAndNotFound() {
        Aula a = buildAula(3L, "A3", 15);
        when(aulaRepository.findById(3L)).thenReturn(Optional.of(a));
        assertEquals(a, aulaService.buscarAula(3L));
        verify(aulaRepository).findById(3L);

        when(aulaRepository.findById(4L)).thenReturn(Optional.empty());
        assertNull(aulaService.buscarAula(4L));
        verify(aulaRepository).findById(4L);
    }

    @Test
    void guardarAula_savesAndReturns() {
        Aula toSave = buildAula(null, "Nueva", 5);
        Aula saved = buildAula(5L, "Nueva", 5);
        when(aulaRepository.save(toSave)).thenReturn(saved);

        Aula result = aulaService.guardarAula(toSave);

        assertEquals(saved, result);
        verify(aulaRepository).save(toSave);
    }

    @Test
    void editarAula_whenExists_updatesAndSaves() {
        Aula existing = buildAula(6L, "Old", 10);
        Aula update = buildAula(null, "New", 12);
        when(aulaRepository.findById(6L)).thenReturn(Optional.of(existing));
        when(aulaRepository.save(existing)).thenReturn(existing);

        Aula result = aulaService.editarAula(6L, update);

        assertEquals(6L, result.getIdaula());
        assertEquals("New", result.getNombre());
        assertEquals(12, result.getCapacidad());
        verify(aulaRepository).findById(6L);
        verify(aulaRepository).save(existing);
    }

    @Test
    void editarAula_whenNotExists_throws() {
        when(aulaRepository.findById(7L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                aulaService.editarAula(7L, buildAula(null, "X", 1))
        );
        assertTrue(ex.getMessage().contains("Aula no encontrada con ID: 7"));
        verify(aulaRepository).findById(7L);
        verify(aulaRepository, never()).save(any());
    }

    @Test
    void obtenerAlumnosPorAula_returnsList() {
        Aula aula = buildAula(8L, "A8", 30);
        AlumnoAula aa = buildAlumnoAula(1L, buildAlumno(100L), aula);
        when(alumnoAulaRepository.findByAula_Idaula(8L)).thenReturn(Collections.singletonList(aa));

        List<AlumnoAula> result = aulaService.obtenerAlumnosPorAula(8L);

        assertEquals(1, result.size());
        assertEquals(aa, result.get(0));
        verify(alumnoAulaRepository).findByAula_Idaula(8L);
    }

    @Test
    void guardarAulaConAlumnos_success() {
        // Arrange
        AulaRequestDTO dto = new AulaRequestDTO();
        dto.setNombre("A9");
        dto.setCapacidad(2);
        dto.setAlumnosIds(Arrays.asList(10L, 20L));

        Aula savedAula = buildAula(9L, "A9", 2);
        when(aulaRepository.save(any(Aula.class))).thenReturn(savedAula);
        when(alumnoAulaRepository.findByAula_Idaula(9L)).thenReturn(Collections.emptyList());
        List<Alumno> alumnos = Arrays.asList(buildAlumno(10L), buildAlumno(20L));
        when(alumnoRepository.findAllById(dto.getAlumnosIds())).thenReturn(alumnos);
        when(alumnoAulaRepository.save(any(AlumnoAula.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        Aula result = aulaService.guardarAulaConAlumnos(dto);

        // Assert
        assertEquals(savedAula, result);
        ArgumentCaptor<AlumnoAula> captor = ArgumentCaptor.forClass(AlumnoAula.class);
        verify(alumnoAulaRepository, times(2)).save(captor.capture());
        List<AlumnoAula> savedAssociations = captor.getAllValues();
        assertEquals(10L, savedAssociations.get(0).getAlumno().getIdalumno());
        assertEquals(20L, savedAssociations.get(1).getAlumno().getIdalumno());
    }

    @Test
    void guardarAulaConAlumnos_capacityExceeded_throws() {
        AulaRequestDTO dto = new AulaRequestDTO();
        dto.setNombre("X");
        dto.setCapacidad(1);
        dto.setAlumnosIds(Arrays.asList(1L, 2L));

        Aula a = buildAula(11L, "X", 1);
        when(aulaRepository.save(any(Aula.class))).thenReturn(a);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                aulaService.guardarAulaConAlumnos(dto)
        );
        assertTrue(ex.getMessage().contains("La cantidad de alumnos excede la capacidad del aula."));
    }

    @Test
    void obtenerAulaPorId_foundAndEmpty() {
        Aula a = buildAula(13L, "Z", 5);
        when(aulaRepository.findById(13L)).thenReturn(Optional.of(a));
        assertTrue(aulaService.obtenerAulaPorId(13L).isPresent());
        verify(aulaRepository).findById(13L);

        when(aulaRepository.findById(14L)).thenReturn(Optional.empty());
        assertTrue(aulaService.obtenerAulaPorId(14L).isEmpty());
        verify(aulaRepository).findById(14L);
    }

    @Test
    void actualizarAula_success() {
        // Arrange
        Aula original = buildAula(15L, "OldName", 3);
        when(aulaRepository.findById(15L)).thenReturn(Optional.of(original));
        when(alumnoRepository.findAllById(Arrays.asList(30L, 40L)))
                .thenReturn(Arrays.asList(buildAlumno(30L), buildAlumno(40L)));
        when(aulaRepository.save(original)).thenReturn(original);

        AulaRequestDTO dto = new AulaRequestDTO();
        dto.setNombre("NewName");
        dto.setCapacidad(2);
        dto.setAlumnosIds(Arrays.asList(30L, 40L));

        // Act
        Aula result = aulaService.actualizarAula(15L, dto);

        // Assert
        assertEquals("NewName", result.getNombre());
        assertEquals(2, result.getCapacidad());
        verify(alumnoAulaRepository).deleteByAula_Idaula(15L);
        verify(alumnoAulaRepository, times(2)).save(any(AlumnoAula.class));
        verify(aulaRepository).save(original);
    }

    @Test
    void actualizarAula_notFound_throws() {
        when(aulaRepository.findById(16L)).thenReturn(Optional.empty());
        AulaRequestDTO dto = new AulaRequestDTO();
        dto.setNombre("X");
        dto.setCapacidad(1);
        dto.setAlumnosIds(Collections.emptyList());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                aulaService.actualizarAula(16L, dto)
        );
        assertTrue(ex.getMessage().contains("Aula no encontrada"));
    }

    @Test
    void eliminarAula_invokesDelete() {
        // Act
        aulaService.eliminarAula(17L);

        // Assert
        verify(aulaRepository).deleteById(17L);
    }
}
