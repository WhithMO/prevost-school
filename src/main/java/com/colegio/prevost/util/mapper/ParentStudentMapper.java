package com.colegio.prevost.util.mapper;

import org.mapstruct.Mapper;

import com.colegio.prevost.dto.ParentStudentDTO;
import com.colegio.prevost.model.ParentStudent;

@Mapper(componentModel = "spring")
public interface ParentStudentMapper {

    ParentStudentDTO toDto(ParentStudent parentStudent);
    ParentStudent toEntity(ParentStudentDTO parentStudentDTO);
}
