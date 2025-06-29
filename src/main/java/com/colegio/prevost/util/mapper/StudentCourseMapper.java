package com.colegio.prevost.util.mapper;

import org.mapstruct.Mapper;

import com.colegio.prevost.dto.StudentCourseDTO;
import com.colegio.prevost.model.StudentCourse;

@Mapper(componentModel = "spring")
public interface StudentCourseMapper {

     StudentCourseDTO toDto(StudentCourse studentCourse);
     StudentCourse toEntity(StudentCourseDTO studentCourseDTO);

}
