package com.colegio.prevost.util.mapper;

import org.mapstruct.Mapper;

import com.colegio.prevost.dto.StudentDTO;
import com.colegio.prevost.model.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {

     StudentDTO toStudentDTO(Student student);
     Student toStudent(StudentDTO studentDTO);
}
