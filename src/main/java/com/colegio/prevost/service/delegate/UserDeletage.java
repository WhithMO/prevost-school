package com.colegio.prevost.service.delegate;

import java.util.List;

import com.colegio.prevost.dto.UserDTO;
import com.colegio.prevost.model.User;

public interface UserDeletage {
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    UserDTO createUser(UserDTO user);
    UserDTO updateUser(Long id, UserDTO user);
    void deleteUser(Long id);
}
