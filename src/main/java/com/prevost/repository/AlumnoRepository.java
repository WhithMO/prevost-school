package com.prevost.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prevost.dto.AlumnoReporteDTO;
import com.prevost.model.Alumno;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
	
	Optional<Alumno> findByDni(String dni);
	
	@Query("""
		    SELECT new com.prevost.dto.AlumnoReporteDTO(
		        a.idalumno,
		        a.nombre,
		        a.apellido,
		        asi.fecha,
		        CASE 
		            WHEN j.id IS NOT NULL THEN 'Justificado'
		            ELSE asi.estado
		        END,
		        c.nombre,
		        au.nombre
		    )
		    FROM Asistencia asi
		    JOIN asi.alumno a
		    JOIN asi.clase cl
		    JOIN cl.curso c
		    JOIN cl.aula au
		    LEFT JOIN Justificacion j ON j.asistencia = asi
		    WHERE a.idalumno = :idAlumno
		""")
		List<AlumnoReporteDTO> obtenerAsistenciasPorAlumno(@Param("idAlumno") Long idAlumno);

	@Query(value = "SELECT * FROM alumno a LEFT JOIN alumno_aula aa ON a.idalumno = aa.id_alumno WHERE aa.idalumnoaula IS NULL", nativeQuery = true)
	List<Alumno> findAlumnosSinAula();

}
