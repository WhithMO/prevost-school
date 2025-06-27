package com.colegio.prevost.util.mapper;

import org.mapstruct.Mapper;

import com.colegio.prevost.dto.AttendanceDTO;
import com.colegio.prevost.model.Attendance;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

     AttendanceDTO toDto(Attendance attendance);
     Attendance toEntity(AttendanceDTO attendanceDTO);
}
