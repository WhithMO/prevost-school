package com.prevost.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.prevost.model.Usuario;
import com.prevost.service.UsuarioService;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioController controller;

    private Usuario buildUser(Long id, String rawPwd) {
        Usuario u = new Usuario();
        u.setIdusuario(id);
        u.setUsername("user" + id);
        u.setPassword(rawPwd);
        u.setActivo(true);
        u.setPrimerIngreso(false);
        return u;
    }

    @Test
    void listarUsuarios_returnsList() {
        Usuario u1 = buildUser(1L, "pwd1");
        Usuario u2 = buildUser(2L, "pwd2");
        when(usuarioService.listarUsuarios()).thenReturn(Arrays.asList(u1, u2));

        ResponseEntity<List<Usuario>> resp = controller.listarUsuarios();

        assertEquals(200, resp.getStatusCodeValue());
        assertEquals(Arrays.asList(u1, u2), resp.getBody());
        verify(usuarioService).listarUsuarios();
    }

    @Test
    void buscarUsuario_whenExists_returnsOk() {
        Usuario u = buildUser(5L, "pwd");
        when(usuarioService.buscarUsuario(5L)).thenReturn(u);

        ResponseEntity<Usuario> resp = controller.buscarUsuario(5L);

        assertEquals(200, resp.getStatusCodeValue());
        assertSame(u, resp.getBody());
        verify(usuarioService).buscarUsuario(5L);
    }

    @Test
    void buscarUsuario_whenNotFound_returns404() {
        when(usuarioService.buscarUsuario(99L)).thenReturn(null);

        ResponseEntity<Usuario> resp = controller.buscarUsuario(99L);

        assertEquals(404, resp.getStatusCodeValue());
        assertNull(resp.getBody());
        verify(usuarioService).buscarUsuario(99L);
    }

    @Test
    void agregarUsuario_encodesPassword_andReturnsCreated() {
        Usuario raw = buildUser(null, "rawPass");
        Usuario saved = buildUser(10L, "encodedPass");
        when(passwordEncoder.encode("rawPass")).thenReturn("encodedPass");
        when(usuarioService.guardarUsuario(any())).thenReturn(saved);

        ResponseEntity<Usuario> resp = controller.agregarUsuario(raw);

        assertEquals(201, resp.getStatusCodeValue());
        assertEquals(saved, resp.getBody());

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(passwordEncoder).encode("rawPass");
        verify(usuarioService).guardarUsuario(captor.capture());
        Usuario toSave = captor.getValue();
        assertEquals("encodedPass", toSave.getPassword());
    }

    @Test
    void editarUsuario_whenNotFound_returns404() {
        when(usuarioService.buscarUsuario(5L)).thenReturn(null);

        ResponseEntity<Usuario> resp = controller.editarUsuario(5L, new Usuario());

        assertEquals(404, resp.getStatusCodeValue());
        assertNull(resp.getBody());
        verify(usuarioService).buscarUsuario(5L);
        verify(usuarioService, never()).editarUsuario(anyLong(), any());
    }

    @Test
    void editarUsuario_withEmptyPassword_keepsOldPassword() {
        Usuario existing = buildUser(3L, "oldEncoded");
        Usuario update = new Usuario();
        update.setPassword("");
        when(usuarioService.buscarUsuario(3L)).thenReturn(existing);
        Usuario returned = buildUser(3L, "oldEncoded");
        when(usuarioService.editarUsuario(eq(3L), any())).thenReturn(returned);

        ResponseEntity<Usuario> resp = controller.editarUsuario(3L, update);

        assertEquals(200, resp.getStatusCodeValue());
        assertEquals(returned, resp.getBody());
        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioService).editarUsuario(eq(3L), captor.capture());
        assertEquals("oldEncoded", captor.getValue().getPassword());
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    void editarUsuario_withNewPassword_encodesAndUpdates() {
        Usuario existing = buildUser(4L, "oldEnc");
        Usuario update = new Usuario();
        update.setPassword("newRaw");
        when(usuarioService.buscarUsuario(4L)).thenReturn(existing);
        when(passwordEncoder.encode("newRaw")).thenReturn("newEnc");
        Usuario returned = buildUser(4L, "newEnc");
        when(usuarioService.editarUsuario(eq(4L), any())).thenReturn(returned);

        ResponseEntity<Usuario> resp = controller.editarUsuario(4L, update);

        assertEquals(200, resp.getStatusCodeValue());
        assertEquals(returned, resp.getBody());
        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(passwordEncoder).encode("newRaw");
        verify(usuarioService).editarUsuario(eq(4L), captor.capture());
        assertEquals("newEnc", captor.getValue().getPassword());
    }

    @Test
    void eliminarUsuario_returnsNoContent() {
        doNothing().when(usuarioService).eliminarUsuario(7L);

        ResponseEntity<Void> resp = controller.eliminarUsuario(7L);

        assertEquals(204, resp.getStatusCodeValue());
        assertNull(resp.getBody());
        verify(usuarioService).eliminarUsuario(7L);
    }
}
