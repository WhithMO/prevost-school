package com.colegio.prevost.util.mapper;

import org.mapstruct.Mapper;

import com.colegio.prevost.dto.ParentDTO;
import com.colegio.prevost.model.Parent;

@Mapper(componentModel = "spring")
public interface ParentMapper {

     ParentDTO toParentDTO(Parent parent);
     Parent toParent(ParentDTO parentDTO);
}
