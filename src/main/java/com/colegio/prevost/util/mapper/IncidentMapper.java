package com.colegio.prevost.util.mapper;

import org.mapstruct.Mapper;

import com.colegio.prevost.dto.IncidentDTO;
import com.colegio.prevost.model.Incident;

@Mapper(componentModel = "spring")
public interface IncidentMapper {

     IncidentDTO toDto(Incident incident);
     Incident toEntity(IncidentDTO incidentDTO);

}
