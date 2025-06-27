package com.colegio.prevost.util.mapper;

import org.mapstruct.Mapper;

import com.colegio.prevost.dto.GradeRecordDTO;
import com.colegio.prevost.model.GradeRecord;

@Mapper(componentModel = "spring")
public interface GradeRecordMapper {

     GradeRecordDTO toDto(GradeRecord gradeRecord);
     GradeRecord toEntity(GradeRecordDTO gradeRecordDTO);

}
