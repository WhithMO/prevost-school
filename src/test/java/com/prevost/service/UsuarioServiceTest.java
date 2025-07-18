package com.prevost.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

import com.prevost.model.Profesor;
import com.prevost.model.Rol;
import com.prevost.model.Usuario;
import com.prevost.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario buildUsuario(Long id, String username) {
        Usuario u = new Usuario();
        u.setIdusuario(id);
        u.setUsername(username);
        u.setPassword("pass");
        u.setActivo(true);
        u.setPrimerIngreso(false);
        Rol r = new Rol(); r.setIdrol(1L); r.setNombre("ROLE");
        u.setRol(r);
        Profesor p = new Profesor(); p.setIdprofesor(2L);
        u.setProfesor(p);
        return u;
    }

    @Test
    void listarUsuarios_returnsAll() {
        Usuario u1 = buildUsuario(1L, "user1");
        Usuario u2 = buildUsuario(2L, "user2");
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(u1, u2));

        List<Usuario> result = usuarioService.listarUsuarios();

        assertEquals(2, result.size());
        assertSame(u1, result.get(0));
        assertSame(u2, result.get(1));
        verify(usuarioRepository).findAll();
    }

    @Test
    void buscarUsuario_whenFound_returnsUsuario() {
        Usuario u = buildUsuario(3L, "found");
        when(usuarioRepository.findById(3L)).thenReturn(Optional.of(u));

        Usuario result = usuarioService.buscarUsuario(3L);

        assertEquals(u, result);
        verify(usuarioRepository).findById(3L);
    }

    @Test
    void buscarUsuario_whenNotFound_returnsNull() {
        when(usuarioRepository.findById(4L)).thenReturn(Optional.empty());

        Usuario result = usuarioService.buscarUsuario(4L);

        assertNull(result);
        verify(usuarioRepository).findById(4L);
    }

    @Test
    void guardarUsuario_savesAndReturns() {
        Usuario input = buildUsuario(null, "new");
        Usuario saved = buildUsuario(5L, "new");
        when(usuarioRepository.save(input)).thenReturn(saved);

        Usuario result = usuarioService.guardarUsuario(input);

        assertEquals(saved, result);
        verify(usuarioRepository).save(input);
    }

    @Test
    void editarUsuario_whenExists_updatesFieldsAndSaves() {
        Usuario existing = buildUsuario(6L, "old");
        Usuario update = new Usuario();
        update.setUsername("updated");
        update.setPassword("newpass");
        update.setActivo(false);
        update.setPrimerIngreso(true);
        Rol newRol = new Rol(); newRol.setIdrol(3L); newRol.setNombre("NEW");
        update.setRol(newRol);
        Profesor newProf = new Profesor(); newProf.setIdprofesor(4L);
        update.setProfesor(newProf);

        when(usuarioRepository.findById(6L)).thenReturn(Optional.of(existing));
        when(usuarioRepository.save(existing)).thenReturn(existing);

        Usuario result = usuarioService.editarUsuario(6L, update);

        assertEquals(6L, result.getIdusuario());
        assertEquals("updated", result.getUsername());
        assertEquals("newpass", result.getPassword());
        assertFalse(result.getActivo());
        assertTrue(result.getPrimerIngreso());
        assertEquals(newRol, result.getRol());
        assertEquals(newProf, result.getProfesor());
        verify(usuarioRepository).findById(6L);
        verify(usuarioRepository).save(existing);
    }

    @Test
    void editarUsuario_whenNotExists_throwsRuntimeException() {
        when(usuarioRepository.findById(7L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                usuarioService.editarUsuario(7L, new Usuario())
        );
        assertTrue(ex.getMessage().contains("Usuario no encontrado con ID: 7"));
        verify(usuarioRepository).findById(7L);
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void eliminarUsuario_invokesDeleteById() {
        usuarioService.eliminarUsuario(8L);

        verify(usuarioRepository).deleteById(8L);
    }
}
