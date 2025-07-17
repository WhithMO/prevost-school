package com.prevost.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.prevost.model.Profesor;
import com.prevost.model.Rol;
import com.prevost.model.Usuario;
import com.prevost.repository.ProfesorRepository;
import com.prevost.repository.RolRepository;
import com.prevost.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final ProfesorRepository profesorRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepository, ProfesorRepository profesorRepository,
                           RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.profesorRepository = profesorRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
    	Rol adminRol = rolRepository.findByNombre("ADMINISTRADOR")
    		    .orElseGet(() -> rolRepository.save(new Rol("ADMINISTRADOR")));

    		Rol profesorRol = rolRepository.findByNombre("PROFESOR")
    		    .orElseGet(() -> rolRepository.save(new Rol("PROFESOR")));

        Profesor profesor = profesorRepository.findByDni("99999999")
                .orElseGet(() -> {
                    Profesor newProfesor = new Profesor();
                    newProfesor.setDni("99999999");
                    newProfesor.setNombre("Profesor Default");
                    newProfesor.setApellido("Admin");
                    newProfesor.setEspecialidad("General");
                    return profesorRepository.save(newProfesor);
                });

        if (usuarioRepository.findByUsername("admin").isEmpty()) {
            Usuario adminUser = new Usuario();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("admin"));
            adminUser.setActivo(true);
            adminUser.setPrimerIngreso(true);
            adminUser.setRol(adminRol);
            adminUser.setProfesor(profesor);

            usuarioRepository.save(adminUser);
            System.out.println("✅ Usuario administrador creado con éxito.");
        } else {
            System.out.println("ℹ️ El usuario administrador ya existe.");
        }
    }
}