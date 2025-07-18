package com.prevost.controller;

import com.prevost.dto.AsistenciaListaDTO;
import com.prevost.model.Asistencia;
import com.prevost.model.Justificacion;
import com.prevost.model.Profesor;
import com.prevost.service.AsistenciaService;
import com.prevost.service.JustificacionService;
import com.prevost.service.ProfesorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JustificacionesControllerTest {

    @Mock
    private JustificacionService justificacionService;

    @Mock
    private ProfesorService profesorService;

    @Mock
    private AsistenciaService asistenciaService;

    @InjectMocks
    private JustificacionesController controller;

    @Test
    void listar_returnsListOfJustificaciones() {
        Justificacion j1 = new Justificacion(); j1.setIdjustificacion(1L);
        Justificacion j2 = new Justificacion(); j2.setIdjustificacion(2L);
        List<Justificacion> expected = Arrays.asList(j1, j2);
        when(justificacionService.listarJustificaciones()).thenReturn(expected);

        ResponseEntity<List<Justificacion>> resp = controller.listar();

        assertEquals(200, resp.getStatusCodeValue());
        assertEquals(expected, resp.getBody());
        verify(justificacionService).listarJustificaciones();
    }

    @Test
    void buscarClase_whenExists_returnsOk() {
        Justificacion j = new Justificacion(); j.setIdjustificacion(5L);
        when(justificacionService.buscarJustificacion(5L)).thenReturn(j);

        ResponseEntity<Justificacion> resp = controller.buscarClase(5L);

        assertEquals(200, resp.getStatusCodeValue());
        assertSame(j, resp.getBody());
        verify(justificacionService).buscarJustificacion(5L);
    }

    @Test
    void buscarClase_whenNotFound_returns404() {
        when(justificacionService.buscarJustificacion(99L)).thenReturn(null);

        ResponseEntity<Justificacion> resp = controller.buscarClase(99L);

        assertEquals(404, resp.getStatusCodeValue());
        assertNull(resp.getBody());
        verify(justificacionService).buscarJustificacion(99L);
    }

    @Test
    void agregarClase_createsJustificacion_andReturnsCreated() throws Exception {
        String fileName = "justi-test.txt";
        byte[] content = "contenido".getBytes();
        MultipartFile archivo = new MockMultipartFile(
                "archivo", fileName, MediaType.TEXT_PLAIN_VALUE, content
        );
        Profesor prof = new Profesor();
        Asistencia asis = new Asistencia();
        Justificacion saved = new Justificacion(); saved.setIdjustificacion(10L);

        when(profesorService.buscarProfesor(1L)).thenReturn(prof);
        when(asistenciaService.buscarAsistencia(2L)).thenReturn(asis);
        when(justificacionService.guardarJustificacion(any(Justificacion.class)))
                .thenReturn(saved);

        ResponseEntity<Justificacion> resp = controller.agregarClase(
                "motivo1", "2025-07-18", 1L, 2L, archivo
        );

        assertEquals(201, resp.getStatusCodeValue());
        assertSame(saved, resp.getBody());
        ArgumentCaptor<Justificacion> captor = ArgumentCaptor.forClass(Justificacion.class);
        verify(justificacionService).guardarJustificacion(captor.capture());
        Justificacion toSave = captor.getValue();
        assertEquals("motivo1", toSave.getMotivo());
        assertEquals(Date.valueOf("2025-07-18"), toSave.getFechaPresentacion());
        assertTrue(toSave.getArchivo().endsWith(fileName));
        assertSame(prof, toSave.getProfesor());
        assertSame(asis, toSave.getAsistencia());
        Files.deleteIfExists(Paths.get(fileName));
    }

    @Test
    void descargarArchivo_whenExists_returnsResource() throws Exception {
        String fileName = "test-download.txt";
        Path path = Paths.get(fileName);
        Files.write(path, "hola".getBytes());

        ResponseEntity<Resource> resp = controller.descargarArchivo(fileName);

        assertEquals(200, resp.getStatusCodeValue());
        assertEquals(MediaType.APPLICATION_OCTET_STREAM, resp.getHeaders().getContentType());
        assertEquals(
                "attachment; filename=\"" + fileName + "\"",
                resp.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION)
        );
        Resource body = resp.getBody();
        assertNotNull(body);
        assertTrue(body.exists());
        Files.deleteIfExists(path);
    }

    @Test
    void actualizarEstado_whenValid_returnsOk() {
        Map<String,String> body = new HashMap<>();
        body.put("estado", "APROBADO");

        ResponseEntity<Map<String,String>> resp = controller.actualizarEstado(1L, body);

        assertEquals(200, resp.getStatusCodeValue());
        assertEquals("Estado actualizado correctamente.", resp.getBody().get("mensaje"));
        verify(justificacionService).actualizarEstado(1L, "APROBADO");
    }

    @Test
    void actualizarEstado_whenEmptyState_returnsBadRequest() {
        Map<String,String> body = Collections.singletonMap("estado", "");

        ResponseEntity<Map<String,String>> resp = controller.actualizarEstado(1L, body);

        assertEquals(400, resp.getStatusCodeValue());
        assertEquals("El estado no puede estar vacío.", resp.getBody().get("mensaje"));
        verifyNoInteractions(justificacionService);
    }

    @Test
    void obtenerPorProfesor_returnsList() {
        Justificacion j = new Justificacion(); j.setIdjustificacion(3L);
        List<Justificacion> list = Collections.singletonList(j);
        when(justificacionService.obtenerJustificacionesPorProfesor(3L)).thenReturn(list);

        ResponseEntity<List<Justificacion>> resp = controller.obtenerPorProfesor(3L);

        assertEquals(200, resp.getStatusCodeValue());
        assertEquals(list, resp.getBody());
        verify(justificacionService).obtenerJustificacionesPorProfesor(3L);
    }

    @Test
    void obtenerAsistencias_whenNotEmpty_returnsOk() {
        AsistenciaListaDTO dto = new AsistenciaListaDTO(
                1, "Nombre", "Apellido", new Date(System.currentTimeMillis()),
                "PRESENTE", "CursoX", null
        );
        List<AsistenciaListaDTO> dtos = Collections.singletonList(dto);
        when(justificacionService.obtenerAsistenciasPorAlumnoYProfesor(4)).thenReturn(dtos);

        ResponseEntity<List<AsistenciaListaDTO>> resp = controller.obtenerAsistencias(4);

        assertEquals(200, resp.getStatusCodeValue());
        assertEquals(dtos, resp.getBody());
        verify(justificacionService).obtenerAsistenciasPorAlumnoYProfesor(4);
    }

    @Test
    void obtenerAsistencias_whenEmpty_returnsNoContent() {
        when(justificacionService.obtenerAsistenciasPorAlumnoYProfesor(5)).thenReturn(Collections.emptyList());

        ResponseEntity<List<AsistenciaListaDTO>> resp = controller.obtenerAsistencias(5);

        assertEquals(204, resp.getStatusCodeValue());
        assertNull(resp.getBody());
        verify(justificacionService).obtenerAsistenciasPorAlumnoYProfesor(5);
    }
}
