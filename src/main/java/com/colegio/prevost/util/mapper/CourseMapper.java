package com.colegio.prevost.util.mapper;

import org.mapstruct.Mapper;

import com.colegio.prevost.dto.CourseDTO;
import com.colegio.prevost.model.Course;

@Mapper(componentModel = "spring")
public interface CourseMapper {

     CourseDTO toCourseDTO(Course course);
     Course toEntity(CourseDTO courseDTO);
}
