package com.colegio.prevost.dto;

import java.util.Set;

import com.colegio.prevost.model.Role;

import lombok.Data;

@Data
public class UserDTO {

    private Long id;
    private String username;
    private String password;
    private String names;
    private String surNames;
    private String email;
    private Set<Role> roles;

    public Long getId() {
        return id != null ? id : null;
    }

    public String getUsername() {
        return username != null ? username : null;
    }

    public String getPassword() {
        return password != null ? password : null;
    }

    public String getNames() {
        return names != null ? names : null;
    }

    public String getSurNames() {
        return surNames != null ? surNames : null;
    }

    public String getEmail() {
        return email != null ? email : null;
    }

    public Set<Role> getRoles() {
        return roles != null ? roles : null;
    }

}
