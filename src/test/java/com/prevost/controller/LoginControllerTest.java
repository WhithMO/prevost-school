package com.prevost.controller;

import com.prevost.dto.CambioClaveDTO;
import com.prevost.dto.LoginRequestDTO;
import com.prevost.dto.LoginResponseDTO;
import com.prevost.model.Profesor;
import com.prevost.model.Rol;
import com.prevost.model.Usuario;
import com.prevost.repository.ProfesorRepository;
import com.prevost.repository.RolRepository;
import com.prevost.repository.UsuarioRepository;
import com.prevost.service.JwtService;
import com.prevost.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ProfesorRepository profesorRepository;
    @Mock
    private RolRepository rolRepository;
    @Mock
    private UsuarioService usuarioService;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    private LoginController controller;

    @Test
    void login_userNotFound_returns404() {
        LoginRequestDTO req = new LoginRequestDTO();
        req.setUsuario("noExiste");
        req.setContraseña("pwd");
        when(usuarioRepository.findByUsername("noExiste")).thenReturn(Optional.empty());

        ResponseEntity<?> resp = controller.login(req);

        assertEquals(404, resp.getStatusCodeValue());
        @SuppressWarnings("unchecked")
        Map<String, String> body = (Map<String, String>) resp.getBody();
        assertEquals("Usuario no encontrado.", body.get("error"));
    }

    @Test
    void login_userInactive_returns403() {
        Usuario u = new Usuario();
        u.setUsername("juan");
        u.setActivo(false);
        when(usuarioRepository.findByUsername("juan")).thenReturn(Optional.of(u));

        LoginRequestDTO req = new LoginRequestDTO();
        req.setUsuario("juan");
        req.setContraseña("pwd");

        ResponseEntity<?> resp = controller.login(req);

        assertEquals(403, resp.getStatusCodeValue());
        @SuppressWarnings("unchecked")
        Map<String, String> body = (Map<String, String>) resp.getBody();
        assertEquals("Usuario desactivado. Contacta al administrador.", body.get("error"));
    }

    @Test
    void login_wrongPassword_returns401() {
        Usuario u = new Usuario();
        u.setUsername("maria");
        u.setActivo(true);
        u.setPassword("encodedPwd");
        when(usuarioRepository.findByUsername("maria")).thenReturn(Optional.of(u));
        when(passwordEncoder.matches("wrong", "encodedPwd")).thenReturn(false);

        LoginRequestDTO req = new LoginRequestDTO();
        req.setUsuario("maria");
        req.setContraseña("wrong");

        ResponseEntity<?> resp = controller.login(req);

        assertEquals(401, resp.getStatusCodeValue());
        @SuppressWarnings("unchecked")
        Map<String, String> body = (Map<String, String>) resp.getBody();
        assertEquals("Credenciales incorrectas.", body.get("error"));
    }

    @Test
    void login_success_returnsTokenAndUserInfo() {
        Usuario u = new Usuario();
        u.setUsername("luis");
        u.setActivo(true);
        u.setPassword("encodedPwd");
        Rol rol = new Rol(1L, "ROLE_USER");
        u.setRol(rol);
        Profesor prof = new Profesor();
        prof.setIdprofesor(42L);
        u.setProfesor(prof);
        u.setPrimerIngreso(false);

        when(usuarioRepository.findByUsername("luis")).thenReturn(Optional.of(u));
        when(passwordEncoder.matches("rawPwd", "encodedPwd")).thenReturn(true);
        when(jwtService.generateToken(u)).thenReturn("jwt-token");

        LoginRequestDTO req = new LoginRequestDTO();
        req.setUsuario("luis");
        req.setContraseña("rawPwd");

        ResponseEntity<?> resp = controller.login(req);

        assertEquals(200, resp.getStatusCodeValue());
        assertTrue(resp.getBody() instanceof LoginResponseDTO);
        LoginResponseDTO dto = (LoginResponseDTO) resp.getBody();
        assertEquals("jwt-token", dto.getToken());
        assertEquals("ROLE_USER", dto.getRol());
        assertEquals("luis", dto.getUsuario());
        assertEquals("42", dto.getIdProfesor());
        assertFalse(dto.getPrimerIngreso());
    }

    @Test
    void agregarUsuario_noProfesor_returns400() {
        Usuario u = new Usuario();

        ResponseEntity<?> resp = controller.agregarUsuario(u);

        assertEquals(400, resp.getStatusCodeValue());
        assertEquals("El profesor es obligatorio.", resp.getBody());
    }

    @Test
    void agregarUsuario_profesorNotFound_throws() {
        Usuario u = new Usuario();
        Profesor p = new Profesor();
        p.setIdprofesor(5L);
        u.setProfesor(p);
        when(profesorRepository.findById(5L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> controller.agregarUsuario(u));
        assertTrue(ex.getMessage().contains("Profesor no encontrado"));
    }

    @Test
    void agregarUsuario_emptyPassword_returns400() {
        Usuario u = new Usuario();
        Profesor p = new Profesor(); p.setIdprofesor(3L);
        u.setProfesor(p);
        when(profesorRepository.findById(3L)).thenReturn(Optional.of(p));
        Rol r = new Rol(2L, "ROLE_PROF");
        when(rolRepository.findById(2L)).thenReturn(Optional.of(r));
        u.setPassword(null);

        ResponseEntity<?> resp = controller.agregarUsuario(u);

        assertEquals(400, resp.getStatusCodeValue());
        assertEquals("La clave del usuario no puede estar vacía.", resp.getBody());
    }

    @Test
    void agregarUsuario_success_returnsCreatedUsuario() {
        Usuario u = new Usuario();
        Profesor p = new Profesor(); p.setIdprofesor(7L);
        u.setProfesor(p);
        u.setPassword("rawPass");
        when(profesorRepository.findById(7L)).thenReturn(Optional.of(p));
        Rol r = new Rol(2L, "ROLE_PROF");
        when(rolRepository.findById(2L)).thenReturn(Optional.of(r));
        when(passwordEncoder.encode("rawPass")).thenReturn("encPass");
        Usuario saved = new Usuario();
        saved.setIdusuario(100L);
        when(usuarioService.guardarUsuario(any())).thenReturn(saved);

        ResponseEntity<?> resp = controller.agregarUsuario(u);

        assertEquals(201, resp.getStatusCodeValue());
        assertSame(saved, resp.getBody());
        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioService).guardarUsuario(captor.capture());
        Usuario toSave = captor.getValue();
        assertEquals("encPass", toSave.getPassword());
        assertEquals(p, toSave.getProfesor());
        assertEquals(r, toSave.getRol());
    }

    @Test
    void cambiarClave_userNotFound_returns404() {
        CambioClaveDTO dto = new CambioClaveDTO("noExiste", "old", "new");
        when(usuarioRepository.findByUsername("noExiste")).thenReturn(Optional.empty());

        ResponseEntity<?> resp = controller.cambiarClave(dto);

        assertEquals(404, resp.getStatusCodeValue());
        @SuppressWarnings("unchecked")
        Map<String,String> body = (Map<String,String>) resp.getBody();
        assertEquals("Usuario no encontrado.", body.get("error"));
    }

    @Test
    void cambiarClave_wrongCurrent_returns401() {
        Usuario u = new Usuario();
        u.setPassword("encOld");
        when(usuarioRepository.findByUsername("juan")).thenReturn(Optional.of(u));
        when(passwordEncoder.matches("badOld", "encOld")).thenReturn(false);
        CambioClaveDTO dto = new CambioClaveDTO("juan", "badOld", "newPwd");

        ResponseEntity<?> resp = controller.cambiarClave(dto);

        assertEquals(401, resp.getStatusCodeValue());
        @SuppressWarnings("unchecked")
        Map<String,String> body = (Map<String,String>) resp.getBody();
        assertEquals("Contraseña actual incorrecta.", body.get("error"));
    }

    @Test
    void cambiarClave_success_updatesAndReturnsOk() {
        Usuario u = new Usuario();
        u.setUsername("ana");
        u.setPassword("encOld");
        u.setPrimerIngreso(false);
        when(usuarioRepository.findByUsername("ana")).thenReturn(Optional.of(u));
        when(passwordEncoder.matches("oldPwd", "encOld")).thenReturn(true);
        when(passwordEncoder.encode("newPwd")).thenReturn("encNew");

        CambioClaveDTO dto = new CambioClaveDTO("ana", "oldPwd", "newPwd");

        ResponseEntity<?> resp = controller.cambiarClave(dto);

        assertEquals(200, resp.getStatusCodeValue());
        @SuppressWarnings("unchecked")
        Map<String,String> body = (Map<String,String>) resp.getBody();
        assertEquals("Contraseña actualizada correctamente.", body.get("mensaje"));

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(captor.capture());
        Usuario saved = captor.getValue();
        assertEquals("encNew", saved.getPassword());
        assertTrue(saved.getPrimerIngreso());
    }
}
