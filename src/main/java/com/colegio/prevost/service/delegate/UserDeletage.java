package com.colegio.prevost.service.delegate;

import java.util.List;

import com.colegio.prevost.dto.UserDTO;

public interface UserDeletage {
    UserDTO getUserById(String username);
    List<UserDTO> getAllUsers();
    UserDTO createUser(UserDTO user);
    UserDTO updateUser(String username, UserDTO user);
    void deleteUser(String username);
}
