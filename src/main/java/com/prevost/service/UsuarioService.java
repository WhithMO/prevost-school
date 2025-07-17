package com.prevost.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prevost.model.Usuario;
import com.prevost.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuario(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario editarUsuario(Long id, Usuario usuarioActualizado) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            usuario.setUsername(usuarioActualizado.getUsername());
            usuario.setPassword(usuarioActualizado.getPassword());
            usuario.setActivo(usuarioActualizado.getActivo());
            usuario.setPrimerIngreso(usuarioActualizado.getPrimerIngreso());
            usuario.setRol(usuarioActualizado.getRol());
            usuario.setProfesor(usuarioActualizado.getProfesor());

            return usuarioRepository.save(usuario);
        } else {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}