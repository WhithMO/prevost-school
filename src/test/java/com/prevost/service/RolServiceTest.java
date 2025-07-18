package com.prevost.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
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

import com.prevost.model.Rol;
import com.prevost.repository.RolRepository;

@ExtendWith(MockitoExtension.class)
class RolServiceTest {

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private RolService rolService;

    private Rol buildRol(Long id, String nombre) {
        Rol r = new Rol();
        r.setIdrol(id);
        r.setNombre(nombre);
        return r;
    }

    @Test
    void listarRoles_returnsAllFromRepository() {
        Rol r1 = buildRol(1L, "ADMIN");
        Rol r2 = buildRol(2L, "USER");
        when(rolRepository.findAll()).thenReturn(Arrays.asList(r1, r2));

        List<Rol> result = rolService.listarRoles();

        assertEquals(2, result.size());
        assertSame(r1, result.get(0));
        assertSame(r2, result.get(1));
        verify(rolRepository).findAll();
    }

    @Test
    void buscarRol_whenFound_returnsRol() {
        Rol r = buildRol(3L, "GUEST");
        when(rolRepository.findById(3L)).thenReturn(Optional.of(r));

        Rol result = rolService.buscarRol(3L);

        assertEquals(r, result);
        verify(rolRepository).findById(3L);
    }

    @Test
    void buscarRol_whenNotFound_returnsNull() {
        when(rolRepository.findById(4L)).thenReturn(Optional.empty());

        Rol result = rolService.buscarRol(4L);

        assertNull(result);
        verify(rolRepository).findById(4L);
    }

    @Test
    void guardarRol_savesAndReturns() {
        Rol input = buildRol(null, "NEW_ROLE");
        Rol saved = buildRol(5L, "NEW_ROLE");
        when(rolRepository.save(input)).thenReturn(saved);

        Rol result = rolService.guardarRol(input);

        assertEquals(saved, result);
        verify(rolRepository).save(input);
    }

    @Test
    void editarRol_whenExists_updatesNameAndSaves() {
        Rol existing = buildRol(6L, "OLD");
        Rol update = buildRol(null, "UPDATED");
        when(rolRepository.findById(6L)).thenReturn(Optional.of(existing));
        when(rolRepository.save(existing)).thenReturn(existing);

        Rol result = rolService.editarRol(6L, update);

        assertEquals(6L, result.getIdrol());
        assertEquals("UPDATED", result.getNombre());
        verify(rolRepository).findById(6L);
        verify(rolRepository).save(existing);
    }

    @Test
    void editarRol_whenNotExists_throwsRuntimeException() {
        when(rolRepository.findById(7L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                rolService.editarRol(7L, buildRol(null, "X"))
        );
        assertTrue(ex.getMessage().contains("Rol no encontrado con ID: 7"));
        verify(rolRepository).findById(7L);
        verify(rolRepository, never()).save(any());
    }

    @Test
    void eliminarRol_invokesDeleteById() {
        rolService.eliminarRol(8L);

        verify(rolRepository).deleteById(8L);
    }
}
