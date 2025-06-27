package com.colegio.prevost.dto;

import com.colegio.prevost.util.enums.RoleEnum;

import lombok.Data;

@Data
public class UserDTO {

    private String code;
    private String names;
    private String surNames;
    private String email;
    private String password;
    private RoleEnum role;
}
