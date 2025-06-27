package com.colegio.prevost.service;

import java.util.List;

import com.colegio.prevost.model.User;

public interface UserService {
    User getUserById(Long id);
    List<User> getAllUsers();
    User createUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
}
