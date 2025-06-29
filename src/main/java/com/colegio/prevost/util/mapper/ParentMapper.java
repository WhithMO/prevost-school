package com.colegio.prevost.util.mapper;

import org.mapstruct.Mapper;

import com.colegio.prevost.dto.ParentDTO;
import com.colegio.prevost.model.Parent;

@Mapper(componentModel = "spring")
public interface ParentMapper {

     ParentDTO toDto(Parent parent);
     Parent toEntity(ParentDTO parentDTO);
}
