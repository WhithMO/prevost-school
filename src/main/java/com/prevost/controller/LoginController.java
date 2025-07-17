package com.prevost.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProfesorRepository profesorRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(request.getUsuario());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "error", "Usuario no encontrado."
            ));
        }

        Usuario usuario = usuarioOpt.get();

        if (usuario.getActivo() == null || !usuario.getActivo()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                "error", "Usuario desactivado. Contacta al administrador."
            ));
        }

        if (!passwordEncoder.matches(request.getContraseña(), usuario.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                "error", "Credenciales incorrectas."
            ));
        }

        String token = jwtService.generateToken(usuario);

        return ResponseEntity.ok(new LoginResponseDTO(
        	    token,
        	    usuario.getRol().getNombre(),
        	    usuario.getUsername(),
        	    usuario.getProfesor() != null ? String.valueOf(usuario.getProfesor().getIdprofesor()) : null,
        	    usuario.getPrimerIngreso()
        	));

    }

    @PostMapping("/agregar")
    public ResponseEntity<?> agregarUsuario(@RequestBody Usuario usuario) {
        if (usuario.getProfesor() == null || usuario.getProfesor().getIdprofesor() == null) {
            return ResponseEntity.badRequest().body("El profesor es obligatorio.");
        }

        Profesor profesor = profesorRepository.findById(usuario.getProfesor().getIdprofesor())
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado con ID: " + usuario.getProfesor().getIdprofesor()));

        Rol rol = rolRepository.findById(2L)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: 2"));

        usuario.setProfesor(profesor);
        usuario.setRol(rol);

        if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("La clave del usuario no puede estar vacía.");
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        Usuario usuarioNuevo = usuarioService.guardarUsuario(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioNuevo);
    }
    
    @PutMapping("/actualizarclave")
    public ResponseEntity<?> cambiarClave(@RequestBody CambioClaveDTO dto) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(dto.getUsuario());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Usuario no encontrado."));
        }

        Usuario usuario = usuarioOpt.get();

        if (!passwordEncoder.matches(dto.getClaveActual(), usuario.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Contraseña actual incorrecta."));
        }

        usuario.setPassword(passwordEncoder.encode(dto.getNuevaClave()));
        usuario.setPrimerIngreso(true);
        usuarioRepository.save(usuario);

        return ResponseEntity.ok(Map.of("mensaje", "Contraseña actualizada correctamente."));
    }

}