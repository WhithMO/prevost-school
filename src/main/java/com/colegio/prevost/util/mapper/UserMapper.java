package com.colegio.prevost.util.mapper;

import org.mapstruct.Mapper;

import com.colegio.prevost.dto.UserDTO;
import com.colegio.prevost.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

     UserDTO toDto(User user);
     User toEntity(UserDTO userDTO);
}
