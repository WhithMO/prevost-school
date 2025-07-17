package com.prevost.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

@Component
public class PermisosConsultasConfig {

    public void configurePermissions(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {

        // Permisos para AlumnoController
    	auth.requestMatchers(HttpMethod.GET, "/rest/alumnos/listar").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_PROFESOR");
    	auth.requestMatchers(HttpMethod.GET, "/rest/alumnos/{id}/reporteasistencias").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_PROFESOR");
        auth.requestMatchers(HttpMethod.POST, "/rest/alumnos/agregar").hasAuthority("ROLE_ADMINISTRADOR");
        auth.requestMatchers(HttpMethod.POST, "/rest/alumnos/editar/{id}").hasAuthority("ROLE_ADMINISTRADOR");
        auth.requestMatchers(HttpMethod.GET, "/rest/alumnos/buscar/{id}").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_PROFESOR");
        auth.requestMatchers(HttpMethod.DELETE, "/rest/alumnos/eliminar/{id}").hasAuthority("ROLE_ADMINISTRADOR");
        
        // Permisos para UsuarioController
    	auth.requestMatchers(HttpMethod.GET, "/rest/usuarios/listar").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_PROFESOR");
        auth.requestMatchers(HttpMethod.POST, "/rest/usuarios/agregar").hasAuthority("ROLE_ADMINISTRADOR");
        auth.requestMatchers(HttpMethod.PUT, "/rest/usuarios/editar/{id}").hasAuthority("ROLE_ADMINISTRADOR");
        auth.requestMatchers(HttpMethod.GET, "/rest/usuarios/buscar/{id}").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_PROFESOR");
        auth.requestMatchers(HttpMethod.DELETE, "/rest/usuarios/eliminar/{id}").hasAuthority("ROLE_ADMINISTRADOR");
        
        // Permisos para AsistenciaController
    	auth.requestMatchers(HttpMethod.GET, "/rest/asistencias/listar").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_PROFESOR");
    	auth.requestMatchers(HttpMethod.GET, "/rest/asistencias/listaranioscantidades/{estado}/{inicio}/{fin}").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_PROFESOR");
    	auth.requestMatchers(HttpMethod.GET, "/rest/asistencias/listarporcentajes/{inicio}/{fin}").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_PROFESOR");
    	auth.requestMatchers(HttpMethod.POST, "/rest/asistencias/agregar").hasAuthority("ROLE_ADMINISTRADOR");
        auth.requestMatchers(HttpMethod.POST, "/rest/asistencias/editar/{id}").hasAuthority("ROLE_ADMINISTRADOR");
        auth.requestMatchers(HttpMethod.GET, "/rest/asistencias/buscar/{id}").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_PROFESOR");
        auth.requestMatchers(HttpMethod.DELETE, "/rest/asistencias/eliminar/{id}").hasAuthority("ROLE_ADMINISTRADOR");
        
        // Permisos para AulaController
    	auth.requestMatchers(HttpMethod.GET, "/rest/aulas/listar").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_PROFESOR");
        auth.requestMatchers(HttpMethod.POST, "/rest/aulas/agregar").hasAuthority("ROLE_ADMINISTRADOR");
        auth.requestMatchers(HttpMethod.POST, "/rest/aulas/editar/{id}").hasAuthority("ROLE_ADMINISTRADOR");
        auth.requestMatchers(HttpMethod.GET, "/rest/aulas/buscar/{id}").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_PROFESOR");
        auth.requestMatchers(HttpMethod.DELETE, "/rest/aulas/eliminar/{id}").hasAuthority("ROLE_ADMINISTRADOR");
        
        // Permisos para AulaCursoController
    	auth.requestMatchers(HttpMethod.GET, "/rest/aulacurso/listar").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_PROFESOR");
        auth.requestMatchers(HttpMethod.POST, "/rest/aulacurso/agregar").hasAuthority("ROLE_ADMINISTRADOR");
        auth.requestMatchers(HttpMethod.POST, "/rest/aulacurso/editar/{id}").hasAuthority("ROLE_ADMINISTRADOR");
        auth.requestMatchers(HttpMethod.GET, "/rest/aulaulacursoas/buscar/{id}").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_PROFESOR");
        auth.requestMatchers(HttpMethod.DELETE, "/rest/aulacurso/eliminar/{id}").hasAuthority("ROLE_ADMINISTRADOR");
        
        // Permisos para ClaseController
    	auth.requestMatchers(HttpMethod.GET, "/rest/clases/listar").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_PROFESOR");
        auth.requestMatchers(HttpMethod.POST, "/rest/clases/agregar").hasAuthority("ROLE_ADMINISTRADOR");
        auth.requestMatchers(HttpMethod.POST, "/rest/clases/editar/{id}").hasAuthority("ROLE_ADMINISTRADOR");
        auth.requestMatchers(HttpMethod.GET, "/rest/clases/buscar/{id}").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_PROFESOR");
        auth.requestMatchers(HttpMethod.DELETE, "/rest/clases/eliminar/{id}").hasAuthority("ROLE_ADMINISTRADOR");
        
        // Permisos para CursosController
    	auth.requestMatchers(HttpMethod.GET, "/rest/cursos/listar").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_PROFESOR");
        auth.requestMatchers(HttpMethod.POST, "/rest/cursos/agregar").hasAuthority("ROLE_ADMINISTRADOR");
        auth.requestMatchers(HttpMethod.POST, "/rest/cursos/editar/{id}").hasAuthority("ROLE_ADMINISTRADOR");
        auth.requestMatchers(HttpMethod.GET, "/rest/cursos/buscar/{id}").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_PROFESOR");
        auth.requestMatchers(HttpMethod.DELETE, "/rest/cursos/eliminar/{id}").hasAuthority("ROLE_ADMINISTRADOR");
        
        // Permisos para JustificacionessController
    	auth.requestMatchers(HttpMethod.GET, "/rest/justificacion/listar").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_PROFESOR");
    	auth.requestMatchers(HttpMethod.GET, "/rest/justificacion/buscarprofesor/{idprofesor}").hasAnyAuthority("ROLE_PROFESOR");
        auth.requestMatchers(HttpMethod.POST, "/rest/justificacion/agregar").hasAuthority("ROLE_ADMINISTRADOR");
        auth.requestMatchers(HttpMethod.POST, "/rest/justificacion/editar/{id}").hasAuthority("ROLE_ADMINISTRADOR");
        auth.requestMatchers(HttpMethod.GET, "/rest/justificacion/buscar/{id}").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_PROFESOR");
        auth.requestMatchers(HttpMethod.DELETE, "/rest/justificacion/eliminar/{id}").hasAuthority("ROLE_ADMINISTRADOR");
        
        // Permisos para ProfesorAulaController
    	auth.requestMatchers(HttpMethod.GET, "/rest/profesoraula/listar").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_PROFESOR");
        auth.requestMatchers(HttpMethod.POST, "/rest/profesoraula/agregar").hasAuthority("ROLE_ADMINISTRADOR");
        auth.requestMatchers(HttpMethod.POST, "/rest/profesoraula/editar/{id}").hasAuthority("ROLE_ADMINISTRADOR");
        auth.requestMatchers(HttpMethod.GET, "/rest/profesoraula/buscar/{id}").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_PROFESOR");
        auth.requestMatchers(HttpMethod.DELETE, "/rest/profesoraula/eliminar/{id}").hasAuthority("ROLE_ADMINISTRADOR");
        
        // Permisos para ProfesoresController
    	auth.requestMatchers(HttpMethod.GET, "/rest/profesores/listar").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_PROFESOR");
        auth.requestMatchers(HttpMethod.POST, "/rest/profesores/agregar").hasAuthority("ROLE_ADMINISTRADOR");
        auth.requestMatchers(HttpMethod.POST, "/rest/profesores/editar/{id}").hasAuthority("ROLE_ADMINISTRADOR");
        auth.requestMatchers(HttpMethod.GET, "/rest/profesores/buscar1/{id}").hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_PROFESOR");
        auth.requestMatchers(HttpMethod.DELETE, "/rest/profesores/eliminar/{id}").hasAuthority("ROLE_ADMINISTRADOR");
    }
}
