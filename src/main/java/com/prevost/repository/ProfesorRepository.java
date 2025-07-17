package com.prevost.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.prevost.dto.AlumnoClaseDTO;
import com.prevost.model.Profesor;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {

    Optional<Profesor> findByDni(String dni);
    
    @Query("SELECT c.idClase, c.diaSemana, c.horaInicio, c.horaFin, cu.nombre, a.nombre, c.fechadeclase " +
            "FROM Clase c " +
            "JOIN c.curso cu " +
            "JOIN c.aula a " +
            "WHERE c.profesor.id = :idProfesor")
    List<Object[]> obtenerClasesPorProfesor(@Param("idProfesor") Long idProfesor);
    
    @Query(value = "SELECT a.idalumno AS idalumno, " +
            "c.dia_semana AS diaSemana, " +
            "c.hora_inicio AS horaInicio, " +
            "c.hora_fin AS horaFin, " +
            "a.nombre AS nombre, " +
            "a.apellido AS apellido, " +
            "COALESCE(asis.estado, 'Sin registrar') AS estadoAsistencia, " +
            "COALESCE(asis.idasistencia, NULL) AS idAsistencia, " +
            "au.idaula AS idaula, " +
            "au.nombre AS nombreAula, " +
            "asis.fecha AS fechaAsistencia " + // ← CORRECTO
            "FROM alumno a " +
            "JOIN alumno_aula aa ON a.idalumno = aa.id_alumno " +
            "JOIN aula au ON aa.idaula = au.idaula " +
            "JOIN clase c ON au.idaula = c.idaula " +
            "LEFT JOIN asistencia asis ON a.idalumno = asis.idalumno " +
            "AND c.id_clase = asis.id_clase " +
            "WHERE c.id_clase = :idClase",
       nativeQuery = true)
List<AlumnoClaseDTO> obtenerAlumnosPorClase(@Param("idClase") Long idClase);



}
