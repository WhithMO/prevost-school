package com.prevost.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.prevost.model.Justificacion;

@Repository
public interface JustificacionRepository extends JpaRepository<Justificacion, Long> {

	@Modifying
	@Query("UPDATE Asistencia a SET a.estado = :nuevoEstado WHERE a.idasistencia = (SELECT j.asistencia.idasistencia FROM Justificacion j WHERE j.idjustificacion = :idjustificacion)")
	void actualizarEstadoJustificacion(@Param("idjustificacion") Long idjustificacion, @Param("nuevoEstado") String nuevoEstado);
	
    @Query("SELECT j FROM Justificacion j WHERE j.profesor.idprofesor = :idprofesor")
    List<Justificacion> encontrarPorProfesor(@Param("idprofesor") Long idprofesor);
    
    @Query("SELECT a.idasistencia, al.nombre, al.apellido, a.fecha, a.estado, cu.nombre, j.idjustificacion " +
    	       "FROM Asistencia a " +
    	       "JOIN a.alumno al " +
    	       "JOIN a.clase c " +
    	       "JOIN c.profesor p " +
    	       "JOIN c.curso cu " +
    	       "LEFT JOIN Justificacion j ON j.asistencia.idasistencia = a.idasistencia " +
    	       "WHERE a.estado = 'Ausente' " +
    	       "AND p.idprofesor = :idprofesor " +
    	       "AND a.fecha >= :fechaLimite " +
    	       "AND j.idjustificacion IS NULL")
    	List<Object[]> findAsistenciasByAlumnoAndProfesor(
    	    @Param("idprofesor") Integer idprofesor, 
    	    @Param("fechaLimite") Date fechaLimite
    	);



}
