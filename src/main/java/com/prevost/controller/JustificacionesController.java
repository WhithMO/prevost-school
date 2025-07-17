package com.prevost.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.prevost.dto.AsistenciaListaDTO;
import com.prevost.model.Justificacion;
import com.prevost.service.AsistenciaService;
import com.prevost.service.JustificacionService;
import com.prevost.service.ProfesorService;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/rest/justificaciones")
public class JustificacionesController {

    @Autowired
    private JustificacionService justificacionService;
    
    @Autowired
    private ProfesorService profesorService;
    
    @Autowired
    private AsistenciaService asistenciaService;

    @GetMapping("/listar")
    public ResponseEntity<List<Justificacion>> listar() {
        return ResponseEntity.ok(justificacionService.listarJustificaciones());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Justificacion> buscarClase(@PathVariable Long id) {
        Justificacion justificacion = justificacionService.buscarJustificacion(id);
        return justificacion != null ? ResponseEntity.ok(justificacion) : ResponseEntity.notFound().build();
    }

    @PostMapping("/agregarjusti")
    public ResponseEntity<Justificacion> agregarClase(
        @RequestParam("motivo") String motivo,
        @RequestParam("fechaPresentacion") String fechaString,
        @RequestParam("idProfesor") Long idProfesor,
        @RequestParam("idAsistencia") Long idAsistencia,
        @RequestParam("archivo") MultipartFile archivo
    ) {
        try {
            java.sql.Date fechaPresentacion = java.sql.Date.valueOf(fechaString.trim());

            String uploadDir = "";
            Files.createDirectories(Paths.get(uploadDir));

            String nombreArchivo = archivo.getOriginalFilename();
            Path rutaCompleta = Paths.get(uploadDir, nombreArchivo);
            Files.copy(archivo.getInputStream(), rutaCompleta, StandardCopyOption.REPLACE_EXISTING);

            Justificacion justificacion = new Justificacion();
            justificacion.setMotivo(motivo);
            justificacion.setFechaPresentacion(fechaPresentacion);
            justificacion.setArchivo("uploads/" + nombreArchivo);
            justificacion.setProfesor(profesorService.buscarProfesor(idProfesor));
            justificacion.setAsistencia(asistenciaService.buscarAsistencia(idAsistencia));

            Justificacion justificacionGuardada = justificacionService.guardarJustificacion(justificacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(justificacionGuardada);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/archivo/{nombre}")
    public ResponseEntity<Resource> descargarArchivo(@PathVariable String nombre) {
        try {
            String uploadDir = "";
            Path archivoPath = Paths.get(uploadDir).resolve(nombre).normalize();

            Resource resource = new UrlResource(archivoPath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/{idjustificacion}/estado")
    public ResponseEntity<Map<String, String>> actualizarEstado(@PathVariable Long idjustificacion, @RequestBody Map<String, String> body) {
        String nuevoEstado = body.get("estado");

        if (nuevoEstado == null || nuevoEstado.isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "El estado no puede estar vacío."));
        }

        justificacionService.actualizarEstado(idjustificacion, nuevoEstado);
        return ResponseEntity.ok(Collections.singletonMap("mensaje", "Estado actualizado correctamente."));
    }

    @GetMapping("/buscarprofesor/{idprofesor}")
    public ResponseEntity<List<Justificacion>> obtenerPorProfesor(@PathVariable Long idprofesor) {
        List<Justificacion> justificaciones = justificacionService.obtenerJustificacionesPorProfesor(idprofesor);
        return ResponseEntity.ok(justificaciones);
    }
    
    @GetMapping("/profesor/{idProfesor}")
    public ResponseEntity<List<AsistenciaListaDTO>> obtenerAsistencias(@PathVariable Integer idProfesor) {
        List<AsistenciaListaDTO> asistencias = justificacionService.obtenerAsistenciasPorAlumnoYProfesor(idProfesor);

        if (asistencias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(asistencias);
    }


    
}
