package com.prevost.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.prevost.model.Asistencia;
import jakarta.transaction.Transactional;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {

    @Query("SELECT YEAR(a.fecha) AS anio, COUNT(a) AS totalAsistencias " +
            "FROM Asistencia a " +
            "WHERE a.estado = :estado AND YEAR(a.fecha) BETWEEN :inicio AND :fin " +
            "GROUP BY YEAR(a.fecha)")
     List<Object[]> contarAsistenciasporAño(@Param("estado") String estado, @Param("inicio") int inicio, @Param("fin") int fin);
     
     
     @Query("SELECT a.estado, COUNT(a) AS cantidad, " +
             "ROUND((COUNT(a) * 100.0) / (SELECT COUNT(b) FROM Asistencia b WHERE b.fecha BETWEEN :inicio AND :fin), 2) AS porcentaje " +
             "FROM Asistencia a " +
             "WHERE a.fecha BETWEEN :inicio AND :fin " +
             "GROUP BY a.estado")
      List<Object[]> countAsistenciasByEstado(@Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);
      
     @Query("SELECT a FROM Asistencia a WHERE a.clase.id = :id_clase")
      List<Asistencia> obtenerAsistenciasPorClase(@Param("id_clase") Long idClase);

     @Modifying
     @Transactional
     @Query(value = """
         INSERT INTO asistencia (id_clase, idalumno, estado, fecha)
         SELECT :idClase, a.idalumno, 'Sin registrar', CURDATE()
         FROM alumno_aula aa
         JOIN alumno a ON aa.id_alumno = a.idalumno
         WHERE aa.idaula = (SELECT idaula FROM clase WHERE id_clase = :idClase);
         """, nativeQuery = true)
     void crearAsistenciasParaClase(@Param("idClase") Long idClase);
     
     @Query(value = "SELECT " +
    	        "p.idprofesor, CONCAT(p.nombre, ' ', p.apellido) AS profesor, " +
    	        "a.idalumno AS idalumno, " +
    	        "CONCAT(a.apellido, ', ', a.nombre) AS alumno, " +
    	        "asis.estado AS estado_asistencia, " +
    	        "au.nombre AS aula, " +
    	        "c.nombre AS curso, " +
    	        "DATE_FORMAT(cl.fechadeclase, '%Y-%m-%d') AS fecha_clase " +
    	        "FROM asistencia asis " +
    	        "JOIN alumno a ON asis.idalumno = a.idalumno " +
    	        "JOIN clase cl ON asis.id_clase = cl.id_clase " +
    	        "JOIN aula au ON cl.idaula = au.idaula " +
    	        "JOIN curso c ON cl.idcurso = c.idcurso " +
    	        "JOIN profesor p ON cl.idprofesor = p.idprofesor " +
    	        "WHERE asis.estado <> 'Sin registrar' " +
    	        "AND (:estado IS NULL OR asis.estado = :estado) " +
    	        "AND (:idaula IS NULL OR au.idaula = :idaula) " +
    	        "AND (:fechaInicio IS NULL OR :fechaFin IS NULL OR DATE(cl.fechadeclase) BETWEEN :fechaInicio AND :fechaFin) " +
    	        "AND (:idprofesor IS NULL OR p.idprofesor = :idprofesor)", 
    	       nativeQuery = true)
    	List<Object[]> buscarAsistenciasPorProfesor(
    	        @Param("estado") String estado,
    	        @Param("idaula") Long idaula,
    	        @Param("fechaInicio") String fechaInicio,
    	        @Param("fechaFin") String fechaFin,
    	        @Param("idprofesor") Long idprofesor
    	);

}
