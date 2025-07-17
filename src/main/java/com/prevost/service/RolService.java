package com.prevost.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prevost.model.Rol;
import com.prevost.repository.RolRepository;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public List<Rol> listarRoles() {
        return rolRepository.findAll();
    }

    public Rol buscarRol(Long id) {
        return rolRepository.findById(id).orElse(null);
    }

    public Rol guardarRol(Rol rol) {
        return rolRepository.save(rol);
    }

    public Rol editarRol(Long id, Rol rolActualizado) {
        Optional<Rol> rolOptional = rolRepository.findById(id);

        if (rolOptional.isPresent()) {
            Rol rol = rolOptional.get();

            rol.setNombre(rolActualizado.getNombre());

            return rolRepository.save(rol);
        } else {
            throw new RuntimeException("Rol no encontrado con ID: " + id);
        }
    }

    public void eliminarRol(Long id) {
        rolRepository.deleteById(id);
    }
}