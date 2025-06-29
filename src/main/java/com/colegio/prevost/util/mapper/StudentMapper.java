package com.colegio.prevost.util.mapper;

import org.mapstruct.Mapper;

import com.colegio.prevost.dto.StudentDTO;
import com.colegio.prevost.model.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {

     StudentDTO toDto(Student student);
     Student toEntity(StudentDTO studentDTO);
}
